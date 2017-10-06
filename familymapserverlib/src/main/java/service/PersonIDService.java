package service;

import java.sql.SQLException;
import java.util.Calendar;

import dataAccess.AuthTokenDao;
import dataAccess.Database;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import model.AuthToken;
import model.Person;
import model.User;
import result.PersonIDResult;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Returns the single Person object with the specified ID.
 */
public class PersonIDService extends Service {
    public PersonIDService() {
    }

    /**
     * Returns the single Person object with the specified ID.
     */
    public PersonIDResult personID(String authID, String personID) throws Database.DatabaseException, SQLException {
        Database db = new Database();
        PersonIDResult pr = new PersonIDResult();
        try
        {
            db.openConnection();
        }
        catch (Database.DatabaseException e)
        {
            e.printStackTrace();
        }
        AuthTokenDao authTokendb = new AuthTokenDao(db);
        PersonDao persondb = new PersonDao(db);
        UserDao userdb = new UserDao(db);

        //TODO: NEED TO AUTHENTICATE personID AND AUTHTOKEN
        //Matching Tables
        AuthToken authToken = authTokendb.getAuthToken(authID);
        User user = userdb.getUserByPersonID(personID);
        //AuthToken authToken = authTokendb.getAuthTokenByUsername(newUser.getUsername());
        long expTime = Long.parseLong(authToken.getExp_time());
        Calendar cal = Calendar.getInstance();
        long currTime = cal.getTimeInMillis();

        if (currTime < expTime)
        {
            if (userdb.getUserByPersonID(personID) != null) {

                if (user.getUserName().equals(authToken.getUserName())) {
                    pr.setDescendant(user.getUserName());
                    pr.setPersonID(user.getPersonID());
                    pr.setFirstName(user.getFirstName());
                    pr.setLastName(user.getLastName());
                    pr.setGender(user.getGender());
                    Person person = persondb.getPerson(personID);
                    pr.setFather(person.getFather());
                    pr.setMother(person.getMother());
                    pr.setSpouse(person.getSpouse());
                } else {
                    pr.setErrorMessage("Requested person does not belong to this user");
                }
            } else{
                pr.setErrorMessage("Invalid personID parameter");
            }
        }
        else{
            pr.setErrorMessage("Invalid auth token");
            authTokendb.deleteAuthToken(authID);
        }
        try{
            db.closeConnection(true);
        }
        catch (Database.DatabaseException e)
        {
            e.printStackTrace();
        }
        return pr;
    }
}
