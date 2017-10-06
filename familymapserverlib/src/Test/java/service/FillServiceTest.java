package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.Database;
import dataAccess.EventDao;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import model.Person;
import request.FillRequest;
import result.FillResult;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class FillServiceTest {

    private Database db;
    private FillResult fillResult;
    private UserDao ud;
    private PersonDao pd;
    private EventDao ed;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        ud = new UserDao(db);
        pd = new PersonDao(db);
        ed = new EventDao(db);
    }

    @Test
    public void fill() throws Exception {
        FillService fs = new FillService();
        FillRequest fillRequest = new FillRequest("a", "4");
        fillResult = fs.fill(fillRequest);
        assertEquals(fillResult.getMessage(), "Successfully added 31 persons and 124 events to the database.");
    }

    @Test
    public void createFamilyTree() throws Exception {
        FillService fs = new FillService();
        Person person = new Person();
        person.setDescendant("a");
        person.setPersonID("b");
        person.setFirstName("c");
        person.setLastName("d");
        person.setGender("e");
        person.setFather("f");
        person.setMother("g");
        person.setSpouse("h");
        pd.addPerson(person);
        assertTrue(fs.createFamilyTree(db, person, person.getPersonID(), 4, "a", "e", 0));
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }
}