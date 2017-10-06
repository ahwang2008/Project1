package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class PersonTest {
    private Person p;

    @Before
    public void setUp() throws Exception {
        p = new Person ("m", "m", "m", "m", "m", "m", "m", "m");
    }

    @Test
    public void getDescendant() throws Exception {
        assertTrue(p.getDescendant().equals("m"));
    }

    @Test
    public void setDescendant() throws Exception {
        p.setDescendant("r");
        assertTrue(p.getDescendant().equals("r"));
    }

    @Test
    public void getPersonID() throws Exception {
        assertTrue(p.getPersonID().equals("m"));
    }

    @Test
    public void setPersonID() throws Exception {
        p.setPersonID("r");
        assertTrue(p.getPersonID().equals("r"));
    }

    @Test
    public void getFirstName() throws Exception {
        assertTrue(p.getFirstName().equals("m"));
    }

    @Test
    public void setFirstName() throws Exception {
        p.setFirstName("r");
        assertTrue(p.getFirstName().equals("r"));
    }

    @Test
    public void getLastName() throws Exception {
        assertTrue(p.getLastName().equals("m"));
    }

    @Test
    public void setLastName() throws Exception {
        p.setLastName("r");
        assertTrue(p.getLastName().equals("r"));
    }

    @Test
    public void getGender() throws Exception {
        assertTrue(p.getGender().equals("m"));
    }

    @Test
    public void setGender() throws Exception {
        p.setGender("r");
        assertTrue(p.getGender().equals("r"));
    }

    @Test
    public void getFather() throws Exception {
        assertTrue(p.getFather().equals("m"));
    }

    @Test
    public void setFather() throws Exception {
        p.setFather("r");
        assertTrue(p.getFather().equals("r"));
    }

    @Test
    public void getMother() throws Exception {
        assertTrue(p.getMother().equals("m"));
    }

    @Test
    public void setMother() throws Exception {
        p.setMother("r");
        assertTrue(p.getMother().equals("r"));
    }

    @Test
    public void getSpouse() throws Exception {
        assertTrue(p.getSpouse().equals("m"));
    }

    @Test
    public void setSpouse() throws Exception {
        p.setSpouse("r");
        assertTrue(p.getSpouse().equals("r"));
    }

}