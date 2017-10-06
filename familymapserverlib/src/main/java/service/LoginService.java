package service;

import java.sql.SQLException;
import java.util.Calendar;

import dataAccess.AuthTokenDao;
import dataAccess.Database;
import dataAccess.UserDao;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import result.LoginResult;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 *
 */
public class LoginService extends Service {

    public LoginService() {
    }

    /**
     * Logs in the user and returns a login result.
     * @param r
     * @return
     */
    public LoginResult login(LoginRequest r) throws SQLException, Database.DatabaseException
    {
        Database db = new Database();
        LoginResult lr = new LoginResult();
        try
        {
            db.openConnection();
        }
        catch (Database.DatabaseException e)
        {
            e.printStackTrace();
        }
        UserDao userdb = new UserDao(db);
        User user = new User();
        user.setUserName(r.getUserName());
        user.setPassword(r.getPassword());


        //TODO: NEED TO AUTHENTICATE USERNAME AND PASSWORD
        if (userdb.authenticateUser(user)) //Checks that username and password exists and match in the database
        {
            //TODO: GET THE ALREADY EXISTING PERSONID FROM THE USERDAO
            User newUser = userdb.getUser(r.getUserName()); //Grabs the user object from the database
            String personID = newUser.getPersonID(); //Obtains the personID from that user object to be put into the authTokenDao to generate new authToken
            String userName = r.getUserName();

            AuthTokenDao tokendb = new AuthTokenDao(db);
            AuthToken authToken = new AuthToken();
            String authTokenID = generateRandomToken();
            //String personID = generateRandomToken(personIDLength);

            //Generates a new authToken for the Same personID
            authToken.setAuthID(authTokenID);
            authToken.setUserName(userName);


            //Generate timestamp here
            Calendar cal = Calendar.getInstance();
            long expTime = cal.getTimeInMillis() + expPeriod;
            authToken.setExp_time(Long.toString(expTime));

            tokendb.addAuthToken(authToken);

            lr.setAuthToken(authTokenID);
            lr.setPersonID(personID);
            lr.setUserName(r.getUserName());
        }
        else
        {
            lr.setErrorMessage("Username or Password is incorrect");
        }

        try
        {
            db.closeConnection(true);
        }
        catch (Database.DatabaseException e)
        {
            e.printStackTrace();
        }
        return lr;
    }
}
