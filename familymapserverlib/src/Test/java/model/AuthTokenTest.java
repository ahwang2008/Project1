package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class AuthTokenTest {
    private AuthToken at;

    @Before
    public void setUp() throws Exception {
        at = new AuthToken("m", "m", "2", "m");
    }

    @Test
    public void getUserName() throws Exception {
        assertTrue(at.getUserName().equals("m"));
    }

    @Test
    public void setUserName() throws Exception {
        at.setUserName("r");
        assertTrue(at.getUserName().equals("r"));
    }

    @Test
    public void getAuthID() throws Exception {
        assertTrue(at.getAuthID().equals("m"));
    }

    @Test
    public void setAuthID() throws Exception {
        at.setAuthID("r");
        assertTrue(at.getAuthID().equals("r"));
    }

    @Test
    public void getExp_time() throws Exception {
        assertTrue(at.getExp_time().equals("2"));
    }

    @Test
    public void setExp_time() throws Exception {
        at.setExp_time("3");
        assertTrue(at.getExp_time().equals("3"));
    }

    @Test
    public void getErrorMessage() throws Exception {
        assertTrue(at.getErrorMessage().equals("m"));
    }

    @Test
    public void setErrorMessage() throws Exception {
        at.setErrorMessage("r");
        assertTrue(at.getErrorMessage().equals("r"));
    }

}