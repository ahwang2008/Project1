package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class UserTest {
    private User u;

    @Before
    public void setUp() throws Exception {
        u = new User("m", "m", "m", "m", "m", "m", "m");
    }

    @Test
    public void getUserName() throws Exception {
        assertTrue(u.getUserName().equals("m"));
    }

    @Test
    public void setUserName() throws Exception {
        u.setUserName("r");
        assertTrue(u.getUserName().equals("r"));
    }

    @Test
    public void getPassword() throws Exception {
        assertTrue(u.getPassword().equals("m"));
    }

    @Test
    public void setPassword() throws Exception {
        u.setPassword("r");
        assertTrue(u.getPassword().equals("r"));
    }

    @Test
    public void getEmail() throws Exception {
        assertTrue(u.getEmail().equals("m"));
    }

    @Test
    public void setEmail() throws Exception {
        u.setEmail("r");
        assertTrue(u.getEmail().equals("r"));
    }

    @Test
    public void getFirstName() throws Exception {
        assertTrue(u.getFirstName().equals("m"));
    }

    @Test
    public void setFirstName() throws Exception {
        u.setFirstName("r");
        assertTrue(u.getFirstName().equals("r"));
    }

    @Test
    public void getLastName() throws Exception {
        assertTrue(u.getLastName().equals("m"));
    }

    @Test
    public void setLastName() throws Exception {
        u.setLastName("r");
        assertTrue(u.getLastName().equals("r"));
    }

    @Test
    public void getGender() throws Exception {
        assertTrue(u.getGender().equals("m"));
    }

    @Test
    public void setGender() throws Exception {
        u.setGender("r");
        assertTrue(u.getGender().equals("r"));
    }

    @Test
    public void getPersonID() throws Exception {
        assertTrue(u.getPersonID().equals("m"));
    }

    @Test
    public void setPersonID() throws Exception {
        u.setPersonID("r");
        assertTrue(u.getPersonID().equals("r"));
    }

}