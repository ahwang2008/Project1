package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import dataAccess.AuthTokenDao;
import dataAccess.Database;
import dataAccess.PersonDao;
import model.AuthToken;
import model.Person;
import result.PersonResult;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class PersonServiceTest {

    private Database db;
    private PersonDao pd;
    private AuthTokenDao ad;
    private PersonResult pr;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        pd = new PersonDao(db);
        ad = new AuthTokenDao(db);
    }

    @Test
    public void person() throws Exception {
        Person person = new Person();
        Person person1 = new Person();
        PersonService ps = new PersonService();
        pd.clearPersons();
        person.setDescendant("a");
        person.setPersonID("b");
        person.setFirstName("c");
        person.setLastName("d");
        person.setGender("e");
        person.setFather("f");
        person.setMother("g");
        person.setSpouse("h");
        pd.addPerson(person);
        person1.setDescendant("a");
        person1.setPersonID("z");
        person1.setFirstName("y");
        person1.setLastName("x");
        person1.setGender("w");
        person1.setFather("v");
        person1.setMother("u");
        person1.setSpouse("t");
        pd.addPerson(person1);
        AuthToken authToken = new AuthToken();
        authToken.setUserName("a");
        authToken.setAuthID("hello");
        Calendar cal = Calendar.getInstance();
        long currTime = cal.getTimeInMillis();
        long testTime = currTime + 30000; //30 sec to test before expires
        authToken.setExp_time(Long.toString(testTime));
        ad.addAuthToken(authToken);
        db.getConn().commit();
        pr = ps.person("hello");
        assertEquals(pr.getData().size(), 2);
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }
}