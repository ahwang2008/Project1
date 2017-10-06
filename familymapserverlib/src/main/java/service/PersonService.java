package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import dataAccess.AuthTokenDao;
import dataAccess.Database;
import dataAccess.PersonDao;
import model.AuthToken;
import model.Person;
import result.PersonResult;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Returns ALL family members of the current user. The current user is
 determined from the provided auth token.
 */
public class PersonService extends Service {
    public PersonService() {
    }

    /**
     * Returns ALL family members of the current user. The current user is
     determined from the provided auth token.
     */
    public PersonResult person(String authID) throws Database.DatabaseException, SQLException {
        Database db = new Database();
        PersonResult pr = new PersonResult();

        try{
            db.openConnection();
        }
        catch (Database.DatabaseException e)
        {
            e.printStackTrace();
        }
        AuthTokenDao authTokendb = new AuthTokenDao(db);
        PersonDao persondb = new PersonDao(db);

        AuthToken authToken = new AuthToken();
        try {
            authToken = authTokendb.getAuthToken(authID);
        } catch (SQLException e) {
            pr.setErrorMessage("Invalid auth token");
            e.printStackTrace();
        } catch (Database.DatabaseException e) {
            e.printStackTrace();
        }
        long expTime = Long.parseLong(authToken.getExp_time());
        Calendar cal = Calendar.getInstance();
        long currTime = cal.getTimeInMillis();

        if (currTime < expTime)
        {
            ArrayList<Person> personArrayList = persondb.getPersons(authToken.getUserName());
            pr.setData(personArrayList);
        }
        else
        {
            pr.setErrorMessage("Chosen auth token has expired");
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
