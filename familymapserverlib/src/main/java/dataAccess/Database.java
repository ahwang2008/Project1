package dataAccess;

import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import java.sql.*;
import java.util.*;
//import dataAccess.AuthTokenDao;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Accesses all the data access objects, which accesses the database.
 */
public class Database {

    private Connection conn;

    public Database()
    {
        loadDriver();
    }
    public void loadDriver()
    {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean openConnection() throws DatabaseException {
        boolean success = false;
        try {
            final String CONNECTION_URL = "jdbc:sqlite:spellcheck.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
            success = true;
        }
        catch (SQLException e) {
            throw new DatabaseException("openConnection failed", e);
        }
        return success;
    }

    public boolean closeConnection(boolean commit) throws DatabaseException {
        boolean success = false;
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
            success = true;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed", e);
        }
        return success;
    }

    public boolean createTables() throws DatabaseException {
        boolean success = false;
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                //stmt.executeUpdate("drop table if exists dictionary");
                //stmt.executeUpdate("create table dictionary ( word text not null unique )");
                stmt.executeUpdate("create table if not exists user ( " +
                        "id integer not null primary key autoincrement, " +
                        "userName varchar(255) not null, " +
                        "password varchar(255) not null, " +
                        "email varchar(255) not null, " +
                        "firstName varchar(255) not null, " +
                        "lastName varchar(255) not null, " +
                        "gender varchar(255) not null, " +
                        "personID varchar(255) not null)");

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

                stmt.executeUpdate("create table if not exists authToken ( " +
                        "id integer not null primary key autoincrement, " +
                        "userName varchar(255) not null, " +
                        "authID varchar(255) not null, " +
                        "exp_time varchar(255) not null)");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
            success = true;
        }
        catch (SQLException e) {
            throw new DatabaseException("createTables failed", e);
        }
        return success;
    }

    public void dropTables() throws SQLException
    {
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("drop table if exists user");
        stmt.executeUpdate("drop table if exists person");
        stmt.executeUpdate("drop table if exists event");
        stmt.executeUpdate("drop table if exists authToken");
    }

    /*
    public static void main(String[] args) {
        try {
            Database db = new Database();

            db.openConnection();
            db.createTables();
            //db.fillDictionary();
            //Set<String> words = db.loadDictionary();
            db.closeConnection(true);

            System.out.println("OK");
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
*/
    public class DatabaseException extends Exception {

        public DatabaseException(String s) {
            super(s);
        }

        public DatabaseException(String s, Throwable throwable) {
            super(s, throwable);
        }
    }

    public Connection getConn()
    {
        return conn;
    }
}
