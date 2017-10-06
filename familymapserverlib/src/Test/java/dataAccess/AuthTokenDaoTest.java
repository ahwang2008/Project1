package dataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import model.AuthToken;
import server.Server;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class AuthTokenDaoTest {

    private Database db;
    private AuthTokenDao at;
    private static long authTimeout;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        at = new AuthTokenDao(db);
    }

    @Test
    public void getAuthTokenTimeout() throws Exception {
        long timeout = at.getAuthTokenTimeout();
        assertEquals(timeout, authTimeout);
    }

    @Test
    public void setAuthTokenTimeout() throws Exception {
        at.setAuthTokenTimeout(325);
        assertEquals(at.getAuthTokenTimeout(), 325 * 1000);
    }

    @Test
    public void addAuthToken() throws Exception {
        AuthToken authToken = new AuthToken();
        authToken.setUserName("r");
        authToken.setAuthID("d");
        authToken.setExp_time("f");
        at.addAuthToken(authToken);
        assertTrue(true);
    }

    @Test
    public void getAuthToken() throws Exception {
        AuthToken authToken = new AuthToken();
        authToken.setUserName("g");
        authToken.setAuthID("e");
        authToken.setExp_time("h");
        at.addAuthToken(authToken);
        AuthTokenDao at1 = new AuthTokenDao(db);
        AuthToken authToken1 = at1.getAuthToken("e");
        assertEquals(authToken1.getAuthID(), authToken.getAuthID());
    }

    @Test
    public void deleteAuthToken() throws Exception {
        AuthToken authToken = new AuthToken();
        authToken.setUserName("g");
        authToken.setAuthID("e");
        authToken.setExp_time("h");
        at.addAuthToken(authToken);
        at.deleteAuthToken("e");
        assertNull(at.getAuthToken("e"));
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }

}