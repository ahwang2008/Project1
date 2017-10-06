package model;

import android.graphics.Color;
import android.provider.Contacts;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Hwang on 6/2/2017.
 */

public class Model {

    private static Model model_instance = new Model();
    private Map<String, List<Event>> personEvents;
    private Map<String, List<Person>> personChildren;
    private String authToken;
    private ArrayList<Person> peopleList;
    private ArrayList<Event> eventList;
    private Set<Event> filteredEventList;
    private String currPersonID;
    private String currEventID;
    private int lifeLineColor;
    private int spouseLineColor;
    private int familyLineColor;
    private int lifeLineColorSaved;
    private int spouseLineColorSaved;
    private int familyLineColorSaved;
    private int mapTypeSaved;
    private boolean lifeSwitchBoolean;
    private boolean familySwitchBoolean;
    private boolean spouseSwitchBoolean;
    private boolean maleSwitchBoolean;
    private boolean femaleSwitchBoolean;
    private boolean fatherSwitchBoolean;
    private boolean motherSwitchBoolean;
    private String searchString;
    private Set parentsAncestorsEvents;
    private Person user;

    private Model() {
        personEvents = new TreeMap();
        personChildren = new TreeMap();
        parentsAncestorsEvents = new HashSet<>();
        filteredEventList = new HashSet<>();
        lifeLineColor = Color.RED;
        spouseLineColor = Color.YELLOW;
        familyLineColor = Color.GREEN;
        lifeLineColorSaved = 0;
        spouseLineColorSaved = 2;
        familyLineColorSaved = 3;
        mapTypeSaved = 0;
        lifeSwitchBoolean = true;
        familySwitchBoolean = true;
        spouseSwitchBoolean = true;
        maleSwitchBoolean = true;
        femaleSwitchBoolean = true;
        fatherSwitchBoolean = true;
        motherSwitchBoolean = true;
        searchString = null;
    }

    public static Model instance() {
        return model_instance;
    }

    public List<Event> setEventsChronologically(List<Event> events){
        ArrayList<Event> chronologicalList = new ArrayList<>();
        for (Event e : events)
        {
            int eventYear = Integer.parseInt(e.getYear());
            if (chronologicalList.size() == 0){
                chronologicalList.add(e);
            }
            else {
                int size = chronologicalList.size();
                for (int i = 0; i <= size; i++) {
                    while (eventYear > Integer.parseInt(chronologicalList.get(i).getYear())) {
                        i++;
                        if (i == chronologicalList.size()) {
                            break;
                        }
                    }
                    chronologicalList.add(i, e);
                }
                if (size == 1) {
                    chronologicalList.remove(0);
                }
            }
        }
        return chronologicalList;
    }

    public Set<Person> getPersonSearchResults(){
        Set<Person> results = new HashSet<>();
        if (searchString.equals("")){
            return results;
        }
        else {
            for (Person p : peopleList) {
                if (p.getFirstName().contains(searchString)) {
                    results.add(p);
                }
                if (p.getLastName().contains(searchString)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    public Set<Event> getEventSearchResults(){
        Set<Event> results = new HashSet<>();
        if (searchString.equals("")) {
            return results;
        }
        else {
            for (Event e : filteredEventList) {
                if (e.getCountry().contains(searchString)) {
                    results.add(e);
                }
                if (e.getCity().contains(searchString)) {
                    results.add(e);
                }
                if (e.getEventType().contains(searchString)) {
                    results.add(e);
                }
                if (e.getYear().contains(searchString)) {
                    results.add(e);
                }
            }
        }
        return results;
    }

    public Person getChildByPersonID(String personID)
    {
        for (Person p : peopleList)
        {
            if (p.getFather() == null || p.getMother() == null)
            {
                //return null;
            }
            else if (p.getFather().equals(personID) || p.getMother().equals(personID))
            {
                return p;
            }
        }
        return null;
    }

    public Person getPersonByPersonID(String personID)
    {
        for (Person p: peopleList)
        {
            if(p.getPersonID().equals(personID))
            {
                return p;
            }
        }
        return null;
    }

    public Event getEventByEventID(String eventID)
    {
        for (Event e : eventList)
        {
            if(e.getEventID().equals(eventID))
            {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Person> getPeopleList() {
        return peopleList;
    }

    //Map is now initialized
    public void setPeopleList(ArrayList<Person> peopleList) {
        this.peopleList = peopleList;
        for (Person p : peopleList)
        {
            String personID = p.getPersonID();
            if(!personChildren.containsKey(personID))
            {
                personChildren.put(personID, new ArrayList<Person>());
            }
            personChildren.get(personID).add(p);
        }
    }

    public Person getUser() {
        for (Person p: peopleList){
            if (p.getPersonID().equals(currPersonID)){
                return p;
            }
        }
        return null;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public Set<Event> getEventList() {

        personEvents.clear();
        filteredEventList.clear();

        //Adding the users events and their spouses events to the list
        Person user = getUser();
        for (Person p : peopleList) {
            if (p.getDescendant().toLowerCase().equals(p.getFirstName().toLowerCase())) {
                //user = p;
                for (Event e : eventList) {
                    if (p.getPersonID().equals(e.getPersonID())) {
                        if (!personEvents.containsKey(e.getPersonID())){
                            personEvents.put(e.getPersonID(), new ArrayList<Event>());
                        }
                        personEvents.get(e.getPersonID()).add(e);
                        filteredEventList.add(e);
                    }
                }
            }
            if (user.getSpouse() != null) {
                if (user.getSpouse().equals(p.getSpouse())) {
                    for (Event e : eventList) {
                        if (user.getSpouse().equals(e.getPersonID())) {
                            if (!personEvents.containsKey(e.getPersonID())) {
                                personEvents.put(e.getPersonID(), new ArrayList<Event>());
                            }
                            personEvents.get(e.getPersonID()).add(e);
                            filteredEventList.add(e);
                        }
                    }
                }
            }
        }
        if (fatherSwitchBoolean) {
            for (Person p : peopleList) {
                if (p.getDescendant().toLowerCase().equals(p.getFirstName().toLowerCase())) {
                    String fatherID = p.getFather();
                    parentsAncestorsEvents.clear();
                    Set<Event> paternalAncestorEventList = getParentAncestorsEvents(fatherID);
                    for (Event e : paternalAncestorEventList) {
                        if (!personEvents.containsKey(e.getPersonID())){
                            personEvents.put(e.getPersonID(), new ArrayList<Event>());
                        }
                        personEvents.get(e.getPersonID()).add(e);
                        filteredEventList.add(e);
                    }
                }
            }
        }
        if(motherSwitchBoolean) {
            for (Person p : peopleList) {
                if (p.getDescendant().toLowerCase().equals(p.getFirstName().toLowerCase())) {
                    String motherID = p.getMother();
                    parentsAncestorsEvents.clear();
                    Set<Event> maternalAncestorEventList = getParentAncestorsEvents(motherID);
                    for (Event e : maternalAncestorEventList) {
                        if (!personEvents.containsKey(e.getPersonID())){
                            personEvents.put(e.getPersonID(), new ArrayList<Event>());
                        }
                        personEvents.get(e.getPersonID()).add(e);
                        filteredEventList.add(e);
                    }
                }
            }
        }

        Set<Event> temp = new HashSet<>(filteredEventList);
        if (!maleSwitchBoolean) {
            for (Event e: temp) {
                if (getGender(e.getPersonID()).equals("m")) {
                    filteredEventList.remove(e);
                }
            }
        }
        if (!femaleSwitchBoolean) {
            for (Event e: temp) {
                if (getGender(e.getPersonID()).equals("f")) {
                    filteredEventList.remove(e);
                }
            }
        }
        return filteredEventList;
    }

    public void setFilteredEventList(Set<Event> filteredEventList) {
        this.filteredEventList = filteredEventList;
    }

    public String getGender(String personID) {
        for(Person p : peopleList) {
            if (p.getPersonID().equals(personID)) {
                return p.getGender();
            }
        }
        return null;
    }

    //Map is now initalized
    public Set<Event> getParentAncestorsEvents(String personID) {
        Person p = getPersonByPersonID(personID);
        for (Event e : eventList) {
            if(e.getPersonID().equals(personID)) {
                parentsAncestorsEvents.add(e);
            }
        }
        if (p.getFather() != null) {
            getParentAncestorsEvents(p.getFather());
        }
        if (p.getMother() != null) {
            getParentAncestorsEvents(p.getMother());
        }
        return parentsAncestorsEvents;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;

        for (Event e : eventList) {
            String personID = e.getPersonID();
            if(!personEvents.containsKey(personID))
            {
                personEvents.put(personID, new ArrayList<Event>());
            }
            personEvents.get(personID).add(e);
        }
    }

    public Map<String, List<Event>> getPersonEvents() {
        return personEvents;
    }


    public Map<String, List<Person>> getPersonChildren() {
        return personChildren;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getCurrPersonID() {
        return currPersonID;
    }

    public String getCurrEventID() {
        return currEventID;
    }

    public void setCurrPersonID(String currPersonID) {
        this.currPersonID = currPersonID;
    }

    public void setCurrEventID(String currEventID) {
        this.currEventID = currEventID;
    }

    public int getLifeLineColor() {
        return lifeLineColor;
    }

    public int getSpouseLineColor() {
        return spouseLineColor;
    }

    public int getFamilyLineColor() {
        return familyLineColor;
    }

    public void setLifeLineColor(int lifeLineColor) {
        this.lifeLineColor = lifeLineColor;
    }

    public void setSpouseLineColor(int spouseLineColor) {
        this.spouseLineColor = spouseLineColor;
    }

    public void setFamilyLineColor(int familyLineColor) {
        this.familyLineColor = familyLineColor;
    }

    public int getLifeLineColorSaved() {
        return lifeLineColorSaved;
    }

    public void setLifeLineColorSaved(int lifeLineColorSaved) {
        this.lifeLineColorSaved = lifeLineColorSaved;
    }

    public int getSpouseLineColorSaved() {
        return spouseLineColorSaved;
    }

    public void setSpouseLineColorSaved(int spouseLineColorSaved) {
        this.spouseLineColorSaved = spouseLineColorSaved;
    }

    public int getFamilyLineColorSaved() {
        return familyLineColorSaved;
    }

    public void setFamilyLineColorSaved(int familyLineColorSaved) {
        this.familyLineColorSaved = familyLineColorSaved;
    }

    public int getMapTypeSaved() {
        return mapTypeSaved;
    }

    public void setMapTypeSaved(int mapTypeSaved) {
        this.mapTypeSaved = mapTypeSaved;
    }

    public boolean isLifeSwitchBoolean() {
        return lifeSwitchBoolean;
    }

    public void setLifeSwitchBoolean(boolean lifeSwitchBoolean) {
        this.lifeSwitchBoolean = lifeSwitchBoolean;
    }

    public boolean isFamilySwitchBoolean() {
        return familySwitchBoolean;
    }

    public void setFamilySwitchBoolean(boolean familySwitchBoolean) {
        this.familySwitchBoolean = familySwitchBoolean;
    }

    public boolean isSpouseSwitchBoolean() {
        return spouseSwitchBoolean;
    }

    public void setSpouseSwitchBoolean(boolean spouseSwitchBoolean) {
        this.spouseSwitchBoolean = spouseSwitchBoolean;
    }

    public boolean isMaleSwitchBoolean() {
        return maleSwitchBoolean;
    }

    public void setMaleSwitchBoolean(boolean maleSwitchBoolean) {
        this.maleSwitchBoolean = maleSwitchBoolean;
    }

    public boolean isFemaleSwitchBoolean() {
        return femaleSwitchBoolean;
    }

    public void setFemaleSwitchBoolean(boolean femaleSwitchBoolean) {
        this.femaleSwitchBoolean = femaleSwitchBoolean;
    }

    public boolean isFatherSwitchBoolean() {
        return fatherSwitchBoolean;
    }

    public void setFatherSwitchBoolean(boolean fatherSwitchBoolean) {
        this.fatherSwitchBoolean = fatherSwitchBoolean;
    }

    public boolean isMotherSwitchBoolean() {
        return motherSwitchBoolean;
    }

    public void setMotherSwitchBoolean(boolean motherSwitchBoolean) {
        this.motherSwitchBoolean = motherSwitchBoolean;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
