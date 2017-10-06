package dataAccess;

import java.sql.*;
import java.util.*;

import model.Person;

/**
 * Created by ahwang13 on 5/18/17.
 */

/**
 * Accesses the persons in the database.
 */

public class PersonDao {

    private Database db;

    public PersonDao(Database db)
    {
        this.db = db;
    }

    /**
     *Adds a new person to the database
     * @param person
     */
    public boolean addPerson(Person person) throws SQLException, Database.DatabaseException {
        db.createTables();
        PreparedStatement stmt = null;
        String sql = "insert into person (descendant, personID, firstName, lastName, gender, father, mother, spouse) values (?, ?, ?, ?, ?, ?, ?, ?);";
        boolean success = false;

        try
        {
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, person.getDescendant());
            stmt.setString(2, person.getPersonID());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFather());
            stmt.setString(7, person.getMother());
            stmt.setString(8, person.getSpouse());

            if(stmt.executeUpdate() == 1)
            {
                success = true;
            }
            else
            {
                throw new SQLException();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(stmt != null)
            {
                stmt.close();
            }
        }
        return success;
    }

    public Person getPerson(String personID) throws SQLException
    {
        if(personID == null)
        {
            return null;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Person person = null;

        try
        {
            String sql = "select * from person where person.personID = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            while(rs.next())
            {
                person = new Person();
                person.setDescendant(rs.getString(2));
                person.setPersonID(rs.getString(3));
                person.setFirstName(rs.getString(4));
                person.setLastName(rs.getString(5));
                person.setGender(rs.getString(6));
                person.setFather(rs.getString(7));
                person.setMother(rs.getString(8));
                person.setSpouse(rs.getString(9));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null)
            {
                rs.close();
            }
        }
        return person;
    }

    public ArrayList<Person> getPersons(String userName) throws SQLException, Database.DatabaseException {
        db.createTables();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Person> personArrayList = new ArrayList<Person>();
        Person person = null;

        try
        {
            String sql = "select * from person where person.descendant = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while(rs.next())
            {
                person = new Person();
                person.setDescendant(rs.getString(2));
                person.setPersonID(rs.getString(3));
                person.setFirstName(rs.getString(4));
                person.setLastName(rs.getString(5));
                person.setGender(rs.getString(6));
                person.setFather(rs.getString(7));
                person.setMother(rs.getString(8));
                person.setSpouse(rs.getString(9));
                personArrayList.add(person);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null)
            {
                rs.close();
            }
        }
        return personArrayList;
    }
    /**
     * Clears the people from the database
     */
    public void clearPersons() throws SQLException {
        Statement stmt = null;
        stmt = db.getConn().createStatement();
        stmt.executeUpdate("drop table if exists person");
        stmt.executeUpdate("create table if not exists person ( " +
                "id integer not null primary key autoincrement, " +
                "descendant varchar(255) not null, " +
                "personID varchar(255) not null, " +
                "firstName varchar(255) not null, " +
                "lastName varchar(255) not null, " +
                "gender varchar(255) not null, " +
                "father varchar(255), " +
                "mother varchar(255), " +
                "spouse varchar(255))");
        if(stmt != null){
            stmt.close();
        }

    }

    public void createMarriage(String fatherID, String motherID) throws SQLException {
        //Find the father and mother id in the person database and get those Ids
        //Access the person database and store the father id in the mother person and the mother id in the father person
        Person father = getPerson(fatherID);
        Person mother = getPerson(motherID);
        Statement stmt = null;
        stmt = db.getConn().createStatement();
        stmt.executeUpdate("update person set spouse = '" + fatherID + "' where personID = '" + motherID + "'");
        stmt.executeUpdate("update person set spouse = '" + motherID + "' where personID = '" + fatherID + "'");
    }

    public void deletePersonByDescendant(String username) throws SQLException {
        PreparedStatement stmt = null;
        try
        {
            String sql = "delete from person where descendant = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
