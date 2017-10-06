package dataAccess;

import java.sql.*;

import model.User;
/**
 * Created by ahwang13 on 5/18/17.
 */

/**
 * Accesses the users in the database.
 */
public class UserDao {

    private Database db;

    public UserDao(Database db) {
        this.db = db;
    }

    /**
     *Adds a new user to the database
     * @param user
     */
    public boolean addUser(User user) throws SQLException, Database.DatabaseException
    {
        db.createTables();
        PreparedStatement stmt = null;
        String sql = "insert into user (userName, password, email, firstName, lastName, gender, personID) values (?, ?, ?, ?, ?, ?, ?);";
        boolean success = false;

        try
        {
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            if(stmt.executeUpdate() == 1)
            {
                success = true;
                //updateUserToken(user.getUsername());
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


    public User getUser(String userName) throws SQLException, Database.DatabaseException
    {
        db.createTables();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try
        {
            String sql = "select * from user where user.userName = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while(rs.next())
            {
                user = new User();
                user.setUserName(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setFirstName(rs.getString(5));
                user.setLastName(rs.getString(6));
                user.setGender(rs.getString(7));
                user.setPersonID(rs.getString(8));
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
        return user;
    }

    public User getUserByPersonID(String personID) throws Database.DatabaseException, SQLException {
        db.createTables();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try
        {
            String sql = "select * from user where user.personID = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            while(rs.next())
            {
                user = new User();
                user.setUserName(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setFirstName(rs.getString(5));
                user.setLastName(rs.getString(6));
                user.setGender(rs.getString(7));
                user.setPersonID(rs.getString(8));
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
        return user;
    }

    public boolean authenticateUser(User user) throws SQLException, Database.DatabaseException
    {
        User existingUser = getUser(user.getUserName());
        if (existingUser == null)
        {
            return false;
        }
        if (user.getPassword().equals(existingUser.getPassword()))
        {
            return true;
        }
        return false;
    }
/*
    public int countRows(String userName) throws SQLException, Database.DatabaseException {
        db.createTables();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        int count = 0;

        try
        {
            String sql = "select * from user where user.userName = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while(rs.next())
            {
                user = new User();
                user.setUserName(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setFirstName(rs.getString(5));
                user.setLastName(rs.getString(6));
                user.setGender(rs.getString(7));
                user.setPersonID(rs.getString(8));
                count++;
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
        return count;
    }
*/
    /**
     * Clears the users from the database
     */
    public void clearUsers() throws SQLException {
        Statement stmt = null;
        stmt = db.getConn().createStatement();
        stmt.executeUpdate("drop table if exists user");
        stmt.executeUpdate("create table if not exists user ( " +
                "id integer not null primary key autoincrement, " +
                "userName varchar(255) not null, " +
                "password varchar(255) not null, " +
                "email varchar(255) not null, " +
                "firstName varchar(255) not null, " +
                "lastName varchar(255) not null, " +
                "gender varchar(255) not null, " +
                "personID varchar(255) not null)");
        if(stmt != null){
            stmt.close();
        }
    }
}
