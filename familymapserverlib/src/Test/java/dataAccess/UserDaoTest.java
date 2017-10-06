package dataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.User;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class UserDaoTest {

    private Database db;
    private UserDao ud;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        ud = new UserDao(db);
    }

    @Test
    public void addUser() throws Exception {
        User user = new User();
        user.setUserName("a");
        user.setPassword("b");
        user.setEmail("c");
        user.setFirstName("d");
        user.setLastName("e");
        user.setGender("f");
        user.setPersonID("g");
        ud.addUser(user);
        assertTrue(true);
    }

    @Test
    public void getUser() throws Exception {
        ud.clearUsers();
        User user = new User();
        user.setUserName("h");
        user.setPassword("i");
        user.setEmail("j");
        user.setFirstName("k");
        user.setLastName("l");
        user.setGender("m");
        user.setPersonID("n");
        ud.addUser(user);
        UserDao ud1 = new UserDao(db);
        User user1 = ud1.getUser("h");
        assertEquals(user1.getUserName(), user.getUserName());
    }

    @Test
    public void getUserByPersonID() throws Exception {
        ud.clearUsers();
        User user = new User();
        user.setUserName("o");
        user.setPassword("p");
        user.setEmail("q");
        user.setFirstName("r");
        user.setLastName("s");
        user.setGender("t");
        user.setPersonID("u");
        ud.addUser(user);
        UserDao ud1 = new UserDao(db);
        User user1 = ud1.getUserByPersonID("u");
        assertEquals(user1.getPersonID(), user.getPersonID());
    }

    @Test
    public void authenticateUser() throws Exception {
        ud.clearUsers();
        User user = new User();
        User user1 = new User();
        user.setUserName("v");
        user.setPassword("w");
        user.setEmail("x");
        user.setFirstName("y");
        user.setLastName("z");
        user.setGender("a");
        user.setPersonID("b");
        ud.addUser(user);

        user1.setUserName("v");
        user1.setPassword("w");
        assertTrue(ud.authenticateUser(user1));
    }

    @Test
    public void clearUsers() throws Exception {
        User user = new User();
        user.setUserName("a");
        user.setPassword("b");
        user.setEmail("c");
        user.setFirstName("d");
        user.setLastName("e");
        user.setGender("f");
        user.setPersonID("g");
        ud.addUser(user);
        ud.clearUsers();
        assertNull(ud.getUser("a"));
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
        ud = null;
    }
}