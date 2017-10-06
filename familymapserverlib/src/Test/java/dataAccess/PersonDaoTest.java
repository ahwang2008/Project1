package dataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.Person;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class PersonDaoTest {

    private Database db;
    private PersonDao pd;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        pd = new PersonDao(db);
    }

    @Test
    public void addPerson() throws Exception {
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
        assertTrue(true);
    }

    @Test
    public void getPerson() throws Exception {
        pd.clearPersons();
        Person person = new Person();
        person.setDescendant("i");
        person.setPersonID("j");
        person.setFirstName("k");
        person.setLastName("l");
        person.setGender("m");
        person.setFather("n");
        person.setMother("o");
        person.setSpouse("p");
        pd.addPerson(person);
        PersonDao pd1 = new PersonDao(db);
        Person person1 = pd1.getPerson("j");
        assertEquals(person1.getPersonID(), person.getPersonID());
    }

    @Test
    public void getPersons() throws Exception {
        pd.clearPersons();
        Person person = new Person();
        Person person1 = new Person();
        Person person2 = new Person();
        person.setDescendant("a");
        person.setPersonID("a");
        person.setFirstName("a");
        person.setLastName("a");
        person.setGender("a");
        person.setFather("a");
        person.setMother("a");
        person.setSpouse("a");
        pd.addPerson(person);

        person1.setDescendant("a");
        person1.setPersonID("b");
        person1.setFirstName("b");
        person1.setLastName("b");
        person1.setGender("b");
        person1.setFather("b");
        person1.setMother("b");
        person1.setSpouse("b");
        pd.addPerson(person1);

        person2.setDescendant("b");
        person2.setPersonID("b");
        person2.setFirstName("b");
        person2.setLastName("b");
        person2.setGender("b");
        person2.setFather("b");
        person2.setMother("b");
        person2.setSpouse("b");
        pd.addPerson(person2);

        ArrayList<Person> persons = pd.getPersons("a");
        assertEquals(persons.size(), 2);
    }

    @Test
    public void clearPersons() throws Exception {
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
        pd.clearPersons();
        assertNull(pd.getPerson("b"));
    }

    @Test
    public void deletePersonByDescendant() throws Exception {
        pd.clearPersons();
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
        pd.deletePersonByDescendant("a");
        assertNull(pd.getPerson("b"));
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
        pd = null;
    }
}