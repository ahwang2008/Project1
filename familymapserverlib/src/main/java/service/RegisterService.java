package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import dataAccess.AuthTokenDao;
import dataAccess.Database;
import dataAccess.UserDao;
import model.AuthToken;
import model.Person;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

/**
 * Created by ahwang13 on 5/19/17.
 */


/**
 * Creates a new user account, generates 4 generations of ancestor data for the new
 user, logs the user in, and returns an auth token.
 */
public class RegisterService extends Service {

    public RegisterService() {
    }

    /**
     * * Creates a new user account, generates 4 generations of ancestor data for the new
     user, logs the user in, and returns an auth token.
     * @param r
     * @return A register result object that contains an authToken, username, and personID
     */

    public RegisterResult register(RegisterRequest r) throws SQLException, Database.DatabaseException, IOException { //usernameAlreadyListedException
        Database db = new Database();
        try {
            db.openConnection();
        } catch (Database.DatabaseException e) {
            e.printStackTrace();
        }

        //Initalizing Databases
        UserDao userdb = new UserDao(db);
        User user = new User();
        Person person = new Person();

        //USER TABLE
        String personID = generateRandomToken();

        //Setting user and person objects for table set-up
        String userName = r.getUserName();
        user.setUserName(r.getUserName());
        person.setDescendant(r.getUserName()); //Descendant is username.

        user.setPassword(r.getPassword());
        user.setEmail(r.getEmail());
        user.setFirstName(r.getFirstName());
        user.setLastName(r.getLastName());
        user.setGender(r.getGender());
        user.setPersonID(personID);

        //PERSON TABLE
        person.setDescendant(r.getUserName());
        person.setPersonID(personID);
        person.setFirstName(r.getFirstName());
        person.setLastName(r.getLastName());
        person.setGender(r.getGender());

        int generations = 4;
        FillService fillService = new FillService();
        fillService.createFamilyTree(db, person, personID, generations, r.getUserName(), "me", 0); //Creates 4 generations of random data




        //TODO: Have the new user object have all the same variables as the incoming input DONE!
        //AuthTokens, corresponding usernames, and time should be put into the authToken dao to be accessed at a later time when the authentication is needed
        //The authToken and unique user_id should be generated here in this function and added to the RegisterResult object along with the user object params
        //Celebrate wildly when finished.

        //If the username is not found within the database...
        RegisterResult rr = new RegisterResult();
        if (userdb.getUser(r.getUserName()) == null) {
            AuthTokenDao tokendb = new AuthTokenDao(db);
            AuthToken authToken = new AuthToken();
            String authTokenID = generateRandomToken();
            authToken.setUserName(userName);
            authToken.setAuthID(authTokenID);


            //Generate timestamp here
            Calendar cal = Calendar.getInstance();
            long expTime = cal.getTimeInMillis() + expPeriod;
            authToken.setExp_time(Long.toString(expTime));

            tokendb.addAuthToken(authToken);
            boolean success = userdb.addUser(user);

            if(success == true)
            {
                rr.setAuthToken(authTokenID);
                rr.setPersonID(personID);
                rr.setUserName(r.getUserName());
            }
            else
            {
                rr.setErrorMessage("Request property missing or has invalid value");
            }
        }
        else
        {
            rr.setErrorMessage("Username already taken by another user");
        }

        try {
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            e.printStackTrace();
        }
        return rr;
    }
}
