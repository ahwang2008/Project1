package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import dataAccess.AuthTokenDao;
import dataAccess.Database;
import dataAccess.EventDao;
import model.AuthToken;
import model.Event;
import result.EventResult;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class EventServiceTest {

    private Database db;
    private EventDao ed;
    private AuthTokenDao ad;
    private EventResult er;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        ed = new EventDao(db);
        ad = new AuthTokenDao(db);
    }

    @Test
    public void event() throws Exception {
        Event event = new Event();
        EventService es = new EventService();
        ed.clearEvents();
        event.setDescendant("a");
        event.setEventID("d");
        event.setPersonID("g");
        event.setLatitude("f");
        event.setLongitude("h");
        event.setCountry("e");
        event.setCity("u");
        event.setEventType("q");
        event.setYear("o");
        ed.addEvent(event);
        Event event1 = new Event();
        event1.setDescendant("a");
        event1.setEventID("z");
        event1.setPersonID("y");
        event1.setLatitude("x");
        event1.setLongitude("w");
        event1.setCountry("v");
        event1.setCity("u");
        event1.setEventType("t");
        event1.setYear("s");
        ed.addEvent(event1);
        AuthToken authToken = new AuthToken();
        authToken.setUserName("a");
        authToken.setAuthID("hello");
        Calendar cal = Calendar.getInstance();
        long currTime = cal.getTimeInMillis();
        long testTime = currTime + 30000; //30 sec to test before expires
        authToken.setExp_time(Long.toString(testTime));
        ad.addAuthToken(authToken);
        db.getConn().commit();
        er = es.event("hello");
        assertEquals(er.getData().size(), 2);
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);

    }
}