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
import request.LoginRequest;
import result.LoginResult;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class LoginServiceTest {

    private Database db;
    private UserDao ud;
    private AuthTokenDao ad;
    private LoginResult loginResult;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        ud = new UserDao(db);
        ad = new AuthTokenDao(db);
    }

    @Test
    public void login() throws Exception {
        LoginRequest lr = new LoginRequest("a", "b");
        LoginService ls = new LoginService();
        ud.clearUsers();
        User user = new User();
        user.setUserName("a");
        user.setPassword("b");
        user.setEmail("c");
        user.setFirstName("d");
        user.setLastName("e");
        user.setGender("f");
        user.setPersonID("g");
        ud.addUser(user);
        AuthToken authToken = new AuthToken();
        authToken.setUserName("a");
        authToken.setAuthID("hello");
        Calendar cal = Calendar.getInstance();
        long currTime = cal.getTimeInMillis();
        long testTime = currTime + 30000; //30 sec to test before expires
        authToken.setExp_time(Long.toString(testTime));
        ad.addAuthToken(authToken);
        db.getConn().commit();
        loginResult = ls.login(lr);
        assertEquals(loginResult.getPersonID(), "g");
        assertEquals(loginResult.getUserName(), "a");
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }
}