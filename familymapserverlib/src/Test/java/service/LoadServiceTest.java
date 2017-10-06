package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import dataAccess.Database;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class LoadServiceTest {

    private Database db;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
    }

    @Test
    public void load() throws Exception {
        LoadRequest loadRequest = new LoadRequest();
        User user = new User("a", "b", "c", "d", "e", "f", "g");
        Person person = new Person("h", "i", "j", "k", "l", "m", "n", "o");
        Person person1 = new Person("p", "q", "r", "s", "t", "u", "v", "w");
        Event event = new Event("x", "y", "z", "a", "b", "c", "d", "e", "f");
        Event event1 = new Event("g", "h", "i", "j", "k", "l", "m", "n", "o");
        Event event2 = new Event("p", "q", "r", "s", "t", "u", "v", "w", "x");
        ArrayList<User> userArrayList = new ArrayList<>();
        ArrayList<Person> personArrayList = new ArrayList<>();
        ArrayList<Event> eventArrayList = new ArrayList<>();
        userArrayList.add(user);
        personArrayList.add(person);
        personArrayList.add(person1);
        eventArrayList.add(event);
        eventArrayList.add(event1);
        eventArrayList.add(event2);
        loadRequest.setUsers(userArrayList);
        loadRequest.setPersons(personArrayList);
        loadRequest.setEvents(eventArrayList);
        assertEquals(loadRequest.getUsers().size(), 1);
        assertEquals(loadRequest.getPersons().size(), 2);
        assertEquals(loadRequest.getEvents().size(), 3);
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }
}