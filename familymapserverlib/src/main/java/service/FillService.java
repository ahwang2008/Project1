package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;

import javax.xml.ws.spi.http.HttpExchange;

import dataAccess.Database;
import dataAccess.EventDao;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import model.Event;
import model.Person;
import model.User;
import randomData.FemaleName;
import randomData.FemaleNameData;
import randomData.LastName;
import randomData.LastNameData;
import randomData.Location;
import randomData.LocationData;
import randomData.MaleName;
import randomData.MaleNameData;
import request.FillRequest;
import result.FillResult;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Populates the server's database with generated data for the specified user name.
 The required "username" parameter must be a user already registered with the server. If there is
 any data in the database already associated with the given user name, it is deleted. The
 optional “generations” parameter lets the caller specify the number of generations of ancestors
 to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
 persons each with associated events).
 */
public class FillService extends Service {

    public FillService() {
    }

    /**
     * Populates the server's database with generated data for the specified user name.
     The required "username" parameter must be a user already registered with the server. If there is
     any data in the database already associated with the given user name, it is deleted. The
     optional “generations” parameter lets the caller specify the number of generations of ancestors
     to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
     persons each with associated events).
     * @param r
     * @return
     */
    public FillResult fill(FillRequest r) throws Database.DatabaseException, SQLException, IOException {
        Database db = new Database();
        FillResult fr = new FillResult();
        try {
            db.openConnection();
        }
        catch (Database.DatabaseException e) {
            e.printStackTrace();
        }

        UserDao userdb = new UserDao(db);
        PersonDao persondb = new PersonDao(db);
        EventDao eventdb = new EventDao(db);
        User user = userdb.getUser(r.getUserName());
        user.setUserName(user.getUserName());

        int generations = 0;
        try {
            if (r.getGenerations() == "4")
            {
                generations = 4;
            }
            else {
                generations = Integer.parseInt(r.getGenerations());
            }
        }
        catch (NumberFormatException e) {
            fr.setMessage("Invalid generations parameter");
            return fr;
        }
        if (generations < 0) {
            fr.setMessage("Invalid number of generations");
            return fr;
        }

        if (userdb.getUser(r.getUserName()) != null)
        {
            persondb.deletePersonByDescendant(r.getUserName());
            eventdb.deleteEventByDescendant(r.getUserName());

            String personID = user.getPersonID();
            //PERSON TABLE
            Person person = new Person();
            person.setDescendant(user.getUserName());
            person.setPersonID(user.getPersonID());
            person.setFirstName(user.getFirstName());
            person.setLastName(user.getLastName());
            person.setGender(user.getGender());
            if (generations == 0)
            {
                persondb.addPerson(person);
            }
            else {
                createFamilyTree(db, person, personID, generations, r.getUserName(), "me", 0); //Creates all the people and events associated (male as default);
            }
            int peopleInGeneration = (int)Math.pow(2, generations);
            int peopleCreated = peopleInGeneration + (peopleInGeneration - 1);
            int eventsCreated = peopleCreated * 4;
            fr.setMessage("Successfully added " + peopleCreated + " persons and " + eventsCreated + " events to the database.");
        }
        else {
            fr.setMessage("Invalid username parameter");
        }
        try {
            db.closeConnection(true);
        }
        catch (Database.DatabaseException e) {
            e.printStackTrace();
        }
        return fr;
    }

    public boolean createFamilyTree(Database db, Person p, String personID, int generations, String userName, String gender, int generationCount) throws IOException, SQLException, Database.DatabaseException {
        int defaultYear = 2000;
        //Database db = new Database();
        //db.openConnection();
        PersonDao personDb = new PersonDao(db);
        EventDao eventDb = new EventDao(db);
        String father = "";
        String mother = "";
        boolean success = false;

        if(generations == 0)
        {
            if (gender.equals("me"))
            {
                father = generateRandomToken();
                mother = generateRandomToken();
                p.setFather(father);
                p.setMother(mother);
                personDb.addPerson(p);
            }
            Person person = new Person();
            person.setDescendant(userName);
            person.setPersonID(personID);
            String firstName = "";
            if (gender.equals("m")) {
                MaleNameData maleNameData = new MaleNameData();
                firstName = maleNameData.getRandomMaleName();
            }
            else if (gender.equals("f")){
                FemaleNameData femaleNameData = new FemaleNameData();
                firstName = femaleNameData.getRandomFemaleName();
            }
            person.setFirstName(firstName);
            LastNameData lastNameData = new LastNameData();
            String surName = lastNameData.getRandomLastName();
            person.setLastName(surName);
            person.setGender(gender);
            personDb.addPerson(person);

            eventDb.createRandomEvents(userName, personID, defaultYear, generationCount);
            //Create new person/event object. Insert characteristics manually here and call the addPerson/addEvent function in the corresponding Daos.
            //Create everything except for the Father/Mother ID
            return true;
        }
        else
        {
            if (gender.equals("me"))
            {
                father = generateRandomToken();
                mother = generateRandomToken();
                p.setFather(father);
                p.setMother(mother);
                personDb.addPerson(p);
            }
            else {
                Person person = new Person();
                person.setDescendant(userName);
                person.setPersonID(personID);
                String firstName = "";

                if (gender.equals("m")) {
                    MaleNameData maleNameData = new MaleNameData();
                    firstName = maleNameData.getRandomMaleName();
                } else if (gender.equals("f")) {
                    FemaleNameData femaleNameData = new FemaleNameData();
                    firstName = femaleNameData.getRandomFemaleName();
                }
                person.setFirstName(firstName);
                LastNameData lastNameData = new LastNameData();
                String surName = lastNameData.getRandomLastName();
                person.setLastName(surName);
                person.setGender(gender);
                father = generateRandomToken();
                mother = generateRandomToken();
                person.setFather(father);
                person.setMother(mother);
                personDb.addPerson(person);
            }

            eventDb.createRandomEvents(userName, personID, defaultYear, generationCount);
            //Do everything including Father/Mother (Generate the father and mother)
        }
        createFamilyTree(db, null, father, generations - 1, userName, "m", generationCount + 1);
        createFamilyTree(db, null, mother, generations - 1, userName, "f", generationCount + 1);
        personDb.createMarriage(father, mother);
        //Find the father and mother id in the person database and get those Ids
        //Access the person database and store the father id in the mother person and the mother id in the father person
        success = true;
        return success;
        //makeSpouse(fatherID, motherID);
    }
}
