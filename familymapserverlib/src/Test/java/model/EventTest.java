package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class EventTest {
    private Event e;

    @Before
    public void setUp() throws Exception {
        e = new Event("m", "m", "m", "5", "5", "m", "m", "m", "5");
    }

    @Test
    public void getDescendant() throws Exception {
        assertTrue(e.getDescendant().equals("m"));
    }

    @Test
    public void setDescendant() throws Exception {
        e.setDescendant("r");
        assertTrue(e.getDescendant().equals("r"));
    }

    @Test
    public void getEventID() throws Exception {
        assertTrue(e.getEventID().equals("m"));
    }

    @Test
    public void setEventID() throws Exception {
        e.setEventID("r");
        assertTrue(e.getEventID().equals("r"));
    }

    @Test
    public void getPersonID() throws Exception {
        assertTrue(e.getPersonID().equals("m"));
    }

    @Test
    public void setPersonID() throws Exception {
        e.setPersonID("r");
        assertTrue(e.getPersonID().equals("r"));
    }

    @Test
    public void getLatitude() throws Exception {
        assertTrue(e.getLatitude().equals("5"));
    }

    @Test
    public void setLatitude() throws Exception {
        e.setLatitude("3");
        assertTrue(e.getLatitude().equals("3"));
    }

    @Test
    public void getLongitude() throws Exception {
        assertTrue(e.getLongitude().equals("5"));
    }

    @Test
    public void setLongitude() throws Exception {
        e.setLongitude("3");
        assertTrue(e.getLongitude().equals("3"));
    }

    @Test
    public void getCountry() throws Exception {
        assertTrue(e.getCountry().equals("m"));
    }

    @Test
    public void setCountry() throws Exception {
        e.setCountry("r");
        assertTrue(e.getCountry().equals("r"));
    }

    @Test
    public void getCity() throws Exception {
        assertTrue(e.getCity().equals("m"));
    }

    @Test
    public void setCity() throws Exception {
        e.setCity("r");
        assertTrue(e.getCity().equals("r"));
    }

    @Test
    public void getEventType() throws Exception {
        assertTrue(e.getEventType().equals("m"));
    }

    @Test
    public void setEventType() throws Exception {
        e.setEventType("r");
        assertTrue(e.getEventType().equals("r"));
    }

    @Test
    public void getYear() throws Exception {
        assertTrue(e.getYear().equals("5"));
    }

    @Test
    public void setYear() throws Exception {
        e.setYear("3");
        assertTrue(e.getYear().equals("3"));
    }

}