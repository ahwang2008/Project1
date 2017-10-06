package model;

import android.util.ArraySet;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 6/19/2017.
 */
public class ModelTest {

    private String m = "m";
    private String n = "n";
    private String o = "o";
    private Person person1;
    private Person person2;
    private Person person3;
    private Event event1;
    private Event event2;
    private Event event3;

    @Before
    public void setUp() throws Exception {
        person1 = new Person(m, m, m, m, "m", n, o, m);
        person2 = new Person(n, n, n, n, "m", null, null, n);
        person3 = new Person(o, o, o, o, "f", null, null, o);
        event1 = new Event(m, m, m, m, m, m, m, m, "1965");
        event2 = new Event(n, n, n, n, n, n, n, n, "1940");
        event3 = new Event(o, o, o, o, o, o, o, o, "1980");
    }

    @Test
    public void instance() throws Exception {

    }

    @Test
    public void setEventsChronologically() throws Exception {
        List<Event> resultsActual = new ArrayList<>();
        resultsActual.add(event2);
        resultsActual.add(event1);
        resultsActual.add(event3);
        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        List<Event> results = Model.instance().setEventsChronologically(events);
        assertEquals(results, resultsActual);
    }

    @Test
    public void getPersonSearchResults() throws Exception {
        Set<Person> resultsActual = new HashSet<>();
        resultsActual.add(person1);
        Model.instance().setSearchString("m");
        ArrayList<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        people.add(person3);
        Model.instance().setPeopleList(people);
        Set<Person> results = Model.instance().getPersonSearchResults();
        assertEquals(results, resultsActual);
    }

    @Test
    public void getEventSearchResults() throws Exception {
        Set<Event> resultsActual = new HashSet<>();
        resultsActual.add(event1);
        Model.instance().setSearchString("m");
        Set<Event> events = new HashSet<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        Model.instance().setFilteredEventList(events);
        Set<Event> results = Model.instance().getEventSearchResults();
        assertEquals(results, resultsActual);
    }

    @Test
    public void getChildByPersonID() throws Exception {
        ArrayList<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        people.add(person3);
        Model.instance().setPeopleList(people);
        Person dad = Model.instance().getChildByPersonID(person2.getPersonID());
        assertEquals(dad, person1);
        Person mom = Model.instance().getChildByPersonID(person3.getPersonID());
        assertEquals(mom, person1);
    }

    @Test
    public void getPersonByPersonID() throws Exception {
        ArrayList<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        people.add(person3);
        Model.instance().setPeopleList(people);
        Person result = Model.instance().getPersonByPersonID(person2.getPersonID());
        assertEquals(person2, result);
    }

    @Test
    public void getEventByEventID() throws Exception {
        ArrayList<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        Model.instance().setEventList(events);
        Event result = Model.instance().getEventByEventID(event2.getEventID());
        assertEquals(event2, result);
    }

    @Test
    public void getUser() throws Exception {
        ArrayList<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        people.add(person3);
        Model.instance().setPeopleList(people);
        Model.instance().setCurrPersonID(m);
        Person user = Model.instance().getUser();
        assertEquals(user, person1);
    }

    @Test
    public void getEventList() throws Exception {
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Person> people = new ArrayList<>();

        //SETUP
        Person person4 = new Person("sheila", "sheila", "sheila", "parker", "f", "blaine", "betty", "davis");
        Person person5 = new Person("sheila", "davis", "davis", "hyer", "m", null, null, "sheila");
        Person person6 = new Person("sheila", "blaine", "blaine", "mcgary", "m", null, null, "betty");
        Person person7 = new Person("sheila", "betty", "betty", "white", "f", null, null, "blaine");
        people.add(person4);
        people.add(person5);
        people.add(person6);
        people.add(person7);
        Model.instance().setPeopleList(people);
        Model.instance().setCurrPersonID(person4.getPersonID());
        Event event4 = new Event("sheila", "1", "sheila", "1", "1", m, m, m, "1960");
        Event event5 = new Event("sheila", "2", "davis", "2", "2", n, n, n, "1970");
        Event event6 = new Event("sheila", "3", "davis", "3", "3", o, o, o, "1980");
        Event event7 = new Event("sheila", "4", "blaine", "4", "4", "p", "p", "p", "1990");
        Event event8 = new Event("sheila", "5", "blaine", "5", "5", "q", "q", "q", "1991");
        Event event9 = new Event("sheila", "6", "betty", "6", "6", "r", "r", "r", "1992");
        Event event10 = new Event("sheila", "7", "betty", "7", "7", "s", "s", "s", "1993");
        Event event11 = new Event("sheila", "8", "davis", "8", "8", "t", "t", "t", "1994");
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);
        events.add(event8);
        events.add(event9);
        events.add(event10);
        events.add(event11);
        Model.instance().setEventList(events);

        //MALE ON, FEMALE OFF
        Model.instance().setMaleSwitchBoolean(true);
        Model.instance().setFemaleSwitchBoolean(false);
        Set<Event> resultsMale = Model.instance().getEventList();
        Set<Event> resultsActualMale = new HashSet<>();
        resultsActualMale.add(event5);
        resultsActualMale.add(event6);
        resultsActualMale.add(event7);
        resultsActualMale.add(event8);
        resultsActualMale.add(event11);
        assertEquals(resultsMale, resultsActualMale);

        //FEMALE ON, MALE OFF
        Model.instance().setFemaleSwitchBoolean(true);
        Model.instance().setMaleSwitchBoolean(false);
        Set<Event> resultsFemale = Model.instance().getEventList();
        Set<Event> resultsActualFemale = new HashSet<>();
        resultsActualFemale.add(event4);
        resultsActualFemale.add(event9);
        resultsActualFemale.add(event10);
        assertEquals(resultsFemale, resultsActualFemale);

        //MOTHER ON, FATHER OFF
        Model.instance().setMaleSwitchBoolean(true);
        Model.instance().setMotherSwitchBoolean(true);
        Model.instance().setFatherSwitchBoolean(false);

        Set<Event> resultsMother = Model.instance().getEventList();
        Set<Event> resultsActualMother = new HashSet<>();
        resultsActualMother.add(event4); //INCLUDES SHEILA
        resultsActualMother.add(event9);
        resultsActualMother.add(event10);
        resultsActualMother.add(event5); //INCLUDES SPOUSE EVENTS
        resultsActualMother.add(event6);
        resultsActualMother.add(event11);
        assertEquals(resultsMother, resultsActualMother);

        //FATHER ON, MOTHER OFF
        Model.instance().setFatherSwitchBoolean(true);
        Model.instance().setMotherSwitchBoolean(false);

        Set<Event> resultsFather = Model.instance().getEventList();
        Set<Event> resultsActualFather = new HashSet<>();
        resultsActualFather.add(event4); //INCLUDES SHEILA
        resultsActualFather.add(event5); //INCLUDES SPOUSE EVENTS
        resultsActualFather.add(event6);
        resultsActualFather.add(event7);
        resultsActualFather.add(event8);
        resultsActualFather.add(event11);
        assertEquals(resultsFather, resultsActualFather);

    }

    @Test
    public void getGender() throws Exception {
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        Model.instance().setPeopleList(personList);
        String female = Model.instance().getGender(person3.getPersonID());
        assertEquals(female, "f");
        String male = Model.instance().getGender(person1.getPersonID());
        assertEquals(male, "m");
    }

    @Test
    public void getParentAncestorsEvents() throws Exception {
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Person> people = new ArrayList<>();
        Person person4 = new Person("sheila", "sheila", "sheila", "parker", "f", "blaine", "betty", "davis");
        Person person5 = new Person("sheila", "davis", "davis", "hyer", "m", null, null, "sheila");
        Person person6 = new Person("sheila", "blaine", "blaine", "mcgary", "m", null, null, "betty");
        Person person7 = new Person("sheila", "betty", "betty", "white", "f", null, null, "blaine");
        people.add(person4);
        people.add(person5);
        people.add(person6);
        people.add(person7);
        Model.instance().setPeopleList(people);
        Event event4 = new Event("sheila", "1", "sheila", "1", "1", m, m, m, "1960");
        Event event5 = new Event("sheila", "2", "davis", "2", "2", n, n, n, "1970");
        Event event6 = new Event("sheila", "3", "davis", "3", "3", o, o, o, "1980");
        Event event7 = new Event("sheila", "4", "blaine", "4", "4", "p", "p", "p", "1990");
        Event event8 = new Event("sheila", "5", "blaine", "5", "5", "q", "q", "q", "1991");
        Event event9 = new Event("sheila", "6", "betty", "6", "6", "r", "r", "r", "1992");
        Event event10 = new Event("sheila", "7", "betty", "7", "7", "s", "s", "s", "1993");
        Event event11 = new Event("sheila", "8", "davis", "8", "8", "t", "t", "t", "1994");
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);
        events.add(event8);
        events.add(event9);
        events.add(event10);
        events.add(event11);
        Model.instance().setEventList(events);
        Set<Event> results = Model.instance().getParentAncestorsEvents("sheila");
        Set<Event> resultsActual = new HashSet<>();
        resultsActual.add(event4);
        resultsActual.add(event7);
        resultsActual.add(event8);
        resultsActual.add(event9);
        resultsActual.add(event10);
        assertEquals(results, resultsActual);
    }
}