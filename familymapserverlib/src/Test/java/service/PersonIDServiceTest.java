package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import dataAccess.AuthTokenDao;
import dataAccess.Database;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import model.AuthToken;
import model.Person;
import model.User;
import result.PersonIDResult;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class PersonIDServiceTest {

    private Database db;
    private PersonDao pd;
    private AuthTokenDao ad;
    private UserDao ud;
    private PersonIDResult pir;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        pd = new PersonDao(db);
        ad = new AuthTokenDao(db);
        ud = new UserDao(db);
    }

    @Test
    public void personID() throws Exception {
        Person person = new Person();
        User user = new User();
        PersonIDService ps = new PersonIDService();
        pd.clearPersons();
        user.setUserName("a");
        user.setPassword("z");
        user.setEmail("y");
        user.setFirstName("c");
        user.setLastName("d");
        user.setGender("e");
        user.setPersonID("b");
        ud.addUser(user);
        person.setDescendant("a");
        person.setPersonID("b");
        person.setFirstName("c");
        person.setLastName("d");
        person.setGender("e");
        person.setFather("f");
        person.setMother("g");
        person.setSpouse("h");
        pd.addPerson(person);
        AuthToken authToken = new AuthToken();
        authToken.setUserName("a");
        authToken.setAuthID("hello");
        Calendar cal = Calendar.getInstance();
        long currTime = cal.getTimeInMillis();
        long testTime = currTime + 3000000; //30 sec to test
        authToken.setExp_time(Long.toString(testTime));
        ad.addAuthToken(authToken);
        pir = ps.personID("hello", "b");
        assertEquals(pir.getDescendant(), person.getDescendant());
        assertEquals(pir.getPersonID(), person.getPersonID());
        assertEquals(pir.getFirstName(), person.getFirstName());
        assertEquals(pir.getLastName(), person.getLastName());
        assertEquals(pir.getGender(), person.getGender());
        assertEquals(pir.getFather(), person.getFather());
        assertEquals(pir.getMother(), person.getMother());
        assertEquals(pir.getSpouse(), person.getSpouse());
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }
}