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
import result.EventIDResult;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class EventIDServiceTest {

    private Database db;
    private EventDao ed;
    private AuthTokenDao ad;
    private EventIDResult eir;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        ed = new EventDao(db);
        ad = new AuthTokenDao(db);
    }

    @Test
    public void eventID() throws Exception {
        Event event = new Event();
        EventIDService es = new EventIDService();
        ed.clearEvents();
        event.setDescendant("a");
        event.setEventID("b");
        event.setPersonID("c");
        event.setLatitude("d");
        event.setLongitude("e");
        event.setCountry("f");
        event.setCity("g");
        event.setEventType("h");
        event.setYear("i");
        ed.addEvent(event);
        AuthToken authToken = new AuthToken();
        authToken.setUserName("a");
        authToken.setAuthID("hello");
        Calendar cal = Calendar.getInstance();
        long currTime = cal.getTimeInMillis();
        long testTime = currTime + 30000; //30 sec to test before expires
        authToken.setExp_time(Long.toString(testTime));
        ad.addAuthToken(authToken);
        eir = es.eventID("hello", "b");
        assertEquals(eir.getDescendant(), event.getDescendant());
        assertEquals(eir.getEventID(), event.getEventID());
        assertEquals(eir.getPersonID(), event.getPersonID());
        assertEquals(eir.getLatitude(), event.getLatitude());
        assertEquals(eir.getLongitude(), event.getLongitude());
        assertEquals(eir.getCountry(), event.getCountry());
        assertEquals(eir.getCity(), event.getCity());
        assertEquals(eir.getEventType(), event.getEventType());
        assertEquals(eir.getYear(), event.getYear());
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);

    }
}