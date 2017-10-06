package service;

import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.Database;
import dataAccess.EventDao;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import result.LoadResult;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Clears all data from the database (just like the /clear API), and then loads the
 posted user, person, and event data into the database.
 */
public class LoadService extends Service {

    public LoadService() {
    }

    /**
     *Clears all data from the database (just like the /clear API), and then loads the
     posted user, person, and event data into the database.
     * @param r
     * @return
     */
    public LoadResult load(LoadRequest r) throws Database.DatabaseException, SQLException {
        Database db = new Database();
        LoadResult lr = new LoadResult();
        try {
            db.openConnection();
        } catch (Database.DatabaseException e) {
            e.printStackTrace();
        }
        UserDao userdb = new UserDao(db);
        PersonDao persondb = new PersonDao(db);
        EventDao eventdb = new EventDao(db);

        db.dropTables();
        db.createTables();
        ArrayList<User> userArray = r.getUsers();
        ArrayList<Person> personArray = r.getPersons();
        ArrayList<Event> eventArray = r.getEvents();
        for (int i = 0; i < userArray.size(); i++) {
            User user = userArray.get(i);
            userdb.addUser(user);
        }
        for (int i = 0; i < personArray.size(); i++) {
            Person person = personArray.get(i);
            persondb.addPerson(person);
        }
        for (int i = 0; i < eventArray.size(); i++) {
            Event event = eventArray.get(i);
            eventdb.addEvent(event);
        }
        int userCount = userArray.size();
        int personCount = personArray.size();
        int eventCount = eventArray.size();
        lr.setMessage("Successfully added " + userCount + " users, " + personCount + " persons, and " + eventCount + " events to the database.");
        try{
            db.closeConnection(true);
        }
        catch (Database.DatabaseException e)
        {
            e.printStackTrace();
        }
        return lr;
    }
}
