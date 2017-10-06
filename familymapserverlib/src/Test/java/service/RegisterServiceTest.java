package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import dataAccess.AuthTokenDao;
import dataAccess.Database;
import dataAccess.UserDao;
import model.AuthToken;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class RegisterServiceTest {

    private Database db;
    private UserDao ud;
    private AuthTokenDao ad;
    //private RegisterResult registerResult;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        ud = new UserDao(db);
        ad = new AuthTokenDao(db);
    }

    @Test
    public void register() throws Exception {
        RegisterRequest rr = new RegisterRequest("a", "b", "c", "d", "e", "f");
        RegisterService rs = new RegisterService();
        ud.clearUsers();
        User user = new User();
        user.setUserName("a");
        user.setPassword("b");
        user.setEmail("c");
        user.setFirstName("d");
        user.setLastName("e");
        user.setGender("f");
        user.setPersonID("h");
        ud.addUser(user);
        AuthToken authToken = new AuthToken();
        authToken.setUserName("a");
        authToken.setAuthID("hello");
        Calendar cal = Calendar.getInstance();
        long currTime = cal.getTimeInMillis();
        authToken.setExp_time(Long.toString(currTime));
        ad.addAuthToken(authToken);
        db.getConn().commit();
        RegisterResult registerResult = rs.register(rr);
        assertNotEquals(registerResult.getUserName(), "a"); //Proves it's already in the database
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }
}