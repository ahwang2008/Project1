package dataAccess;

import model.Event;
import model.Person;
import randomData.Location;
import randomData.LocationData;
import service.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by ahwang13 on 5/18/17.
 */

/**
 * Accesses the events in the database.
 */
public class EventDao {

    private Database db;

    public EventDao(Database db) {
        this.db = db;
    }

    /**
     * Adds a new event to the database
     *
     * @param event
     */
    public boolean addEvent(Event event) throws SQLException, Database.DatabaseException {
        db.createTables();
        PreparedStatement stmt = null;
        //ResultSet rs = null;
        String sql = "insert into event (descendant, eventID, personID, latitude, longitude, country, city, eventType, year) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        boolean success = false;
        try {
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, event.getDescendant());
            stmt.setString(2, event.getEventID());
            stmt.setString(3, event.getPersonID());
            stmt.setString(4, event.getLatitude());
            stmt.setString(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setString(9, event.getYear());
            if (stmt.executeUpdate() == 1) {
                success = true;
            }
            else{
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return success;
    }

    public Event getEvent(String eventID) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Event event = null;

        try {
            String sql = "select * from event where event.eventID = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new Event();
                event.setDescendant(rs.getString(2));
                event.setEventID(rs.getString(3));
                event.setPersonID(rs.getString(4));
                //System.out.println(rs.getString(4));
                event.setLatitude(rs.getString(5));
                //System.out.println(rs.getString(5));
                event.setLongitude(rs.getString(6));
                event.setCountry(rs.getString(7));
                event.setCity(rs.getString(8));
                event.setEventType(rs.getString(9));
                event.setYear(rs.getString(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return event;
    }

    public ArrayList<Event> getEvents(String userName) throws SQLException, Database.DatabaseException
    {
        db.createTables();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Event> eventArrayList = new ArrayList<Event>();
        Event event = null;

        try
        {
            String sql = "select * from event where event.descendant = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while(rs.next())
            {
                event = new Event();
                event.setDescendant(rs.getString(2));
                event.setEventID(rs.getString(3));
                event.setPersonID(rs.getString(4));
                event.setLatitude(rs.getString(5));
                event.setLongitude(rs.getString(6));
                event.setCountry(rs.getString(7));
                event.setCity(rs.getString(8));
                event.setEventType(rs.getString(9));
                event.setYear(rs.getString(10));
                eventArrayList.add(event);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if (stmt != null){
                stmt.close();
            }
            if (rs != null){
                rs.close();
            }
        }
        return eventArrayList;
    }

    public void createRandomEvents(String userName, String personID, int year, int generationCount) throws SQLException, IOException, Database.DatabaseException {
        Event event = new Event();
        int numberOfEvents = 4;
        for (int i = 0; i < numberOfEvents; i++) {
            event.setDescendant(userName);
            Service service = new Service();
            event.setEventID(service.generateRandomToken());
            event.setPersonID(personID);
            LocationData locData = new LocationData();
            Location loc = locData.getRandomLocation();
            event.setLatitude(loc.getLatitude());
            event.setLongitude(loc.getLongitude());
            event.setCountry(loc.getCountry());
            event.setCity(loc.getCity());
            int birthYear = year - (30 * generationCount);
            int baptismYear = birthYear + 8;
            int marriageYear = birthYear + 30;
            int deathYear = birthYear + 70;
            if (i == 0) {
                event.setEventType("birth");
                event.setYear(Integer.toString(birthYear));
                addEvent(event);
            } else if (i == 1) {
                event.setEventType("baptism");
                event.setYear(Integer.toString(baptismYear));
                addEvent(event);
            } else if (i == 2) {
                event.setEventType("marriage");
                event.setYear(Integer.toString(marriageYear));
                addEvent(event);
            } else if (i == 3) {
                event.setEventType("death");
                event.setYear(Integer.toString(deathYear));
                addEvent(event);
            }
        }
    }

    /**
     * Clears the events from the database
     */
    public void clearEvents() throws SQLException {
        Statement stmt = null;
        stmt = db.getConn().createStatement();
        stmt.executeUpdate("drop table if exists event");
        stmt.executeUpdate("create table if not exists event ( " +
                "id integer not null primary key autoincrement, " +
                "descendant varchar(255) not null, " +
                "eventID varchar(255) not null, " +
                "personID varchar(255) not null, " +
                "latitude varchar(255) not null, " +
                "longitude varchar(255) not null, " +
                "country varchar(255) not null, " +
                "city varchar(255) not null, " +
                "eventType varchar(255) not null, " +
                "year varchar(255) not null)");
        if(stmt != null){
            stmt.close();
        }
    }


    public void deleteEventByDescendant(String username) throws SQLException {
        PreparedStatement stmt = null;

        try {
            String sql = "delete from event where descendant = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
