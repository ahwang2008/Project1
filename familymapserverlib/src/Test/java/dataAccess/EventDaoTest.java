package dataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.Event;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class EventDaoTest {

    private Database db;
    private EventDao et;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        et = new EventDao(db);
    }

    @Test
    public void addEvent() throws Exception {
        Event event = new Event();
        event.setDescendant("a");
        event.setEventID("b");
        event.setPersonID("c");
        event.setLatitude("d");
        event.setLongitude("e");
        event.setCountry("f");
        event.setCity("g");
        event.setEventType("h");
        event.setYear("i");
        et.addEvent(event);
        assertTrue(true);
    }

    @Test
    public void getEvent() throws Exception {
        et.clearEvents();
        Event event = new Event();
        event.setDescendant("j");
        event.setEventID("k");
        event.setPersonID("l");
        event.setLatitude("m");
        event.setLongitude("n");
        event.setCountry("o");
        event.setCity("p");
        event.setEventType("q");
        event.setYear("r");
        et.addEvent(event);
        EventDao et1 = new EventDao(db);
        Event event1 = et1.getEvent("k");
        assertEquals(event1.getEventID(), event.getEventID());
    }

    @Test
    public void getEvents() throws Exception {
        //ArrayList<Event> events = new ArrayList<>();
        et.clearEvents();
        Event event = new Event();
        Event event1 = new Event();
        Event event2 = new Event();
        event.setDescendant("a");
        event.setEventID("a");
        event.setPersonID("a");
        event.setLatitude("a");
        event.setLongitude("a");
        event.setCountry("a");
        event.setCity("a");
        event.setEventType("a");
        event.setYear("a");
        et.addEvent(event);

        event1.setDescendant("a");
        event1.setEventID("b");
        event1.setPersonID("b");
        event1.setLatitude("b");
        event1.setLongitude("b");
        event1.setCountry("b");
        event1.setCity("b");
        event1.setEventType("b");
        event1.setYear("b");
        et.addEvent(event1);

        event2.setDescendant("b");
        event2.setEventID("b");
        event2.setPersonID("b");
        event2.setLatitude("b");
        event2.setLongitude("b");
        event2.setCountry("b");
        event2.setCity("b");
        event2.setEventType("b");
        event2.setYear("b");
        et.addEvent(event2);

        ArrayList<Event> events = et.getEvents("a");
        assertEquals(events.size(), 2);
    }

    @Test
    public void createRandomEvents() throws Exception {
        clearEvents();
        et.createRandomEvents("a", "b", 2000, 0);
        et.createRandomEvents("c", "d", 1980, 0);
        assertNotEquals(et.getEvents("a"), et.getEvents("c"));
    }

    @Test
    public void clearEvents() throws Exception {
        Event event = new Event();
        event.setDescendant("a");
        event.setEventID("b");
        event.setPersonID("c");
        event.setLatitude("d");
        event.setLongitude("e");
        event.setCountry("f");
        event.setCity("g");
        event.setEventType("h");
        event.setYear("i");
        et.addEvent(event);
        et.clearEvents();
        assertNull(et.getEvent("a"));
    }

    @Test
    public void deleteEventByDescendant() throws Exception {
        et.clearEvents();
        Event event = new Event();
        event.setDescendant("a");
        event.setEventID("b");
        event.setPersonID("c");
        event.setLatitude("d");
        event.setLongitude("e");
        event.setCountry("f");
        event.setCity("g");
        event.setEventType("h");
        event.setYear("i");
        et.addEvent(event);
        et.deleteEventByDescendant("a");
        assertNull(et.getEvent("a"));
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
        et = null;
    }
}