package dataAccess;

import model.AuthToken;
import java.sql.*;
import model.AuthToken;


/**
 * Created by ahwang13 on 5/18/17.
 */

/**
 * Accesses the authentication tokens in the database.
 */
public class AuthTokenDao {

    private Database db;
    private static long authTokenTimeout;

    public static long getAuthTokenTimeout()
    {
        return authTokenTimeout;
    }

    public static void setAuthTokenTimeout(long timeout)
    {
        authTokenTimeout = timeout * 1000;
    }
    public AuthTokenDao(Database db) {this.db = db;}

    /**
     *Adds a new authentication token to the database
     * @param token
     */
    public boolean addAuthToken(AuthToken token) throws SQLException, Database.DatabaseException {
        db.createTables();
        PreparedStatement stmt = null;
        String sql = "insert into authToken (userName, authID, exp_time) values (?, ?, ?);";
        boolean success = false;

        try
        {
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, token.getUserName());
            stmt.setString(2, token.getAuthID());
            stmt.setString(3, token.getExp_time());

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

    public AuthToken getAuthToken(String authID) throws SQLException, Database.DatabaseException
    {
        //db.createTables(); //Do I need to even create the tables?
        PreparedStatement stmt = null;
        ResultSet rs = null;
        AuthToken authToken = null;

        try
        {
            //I want to select the authToken from the username
            String sql = "select * from authToken where authToken.authID = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, authID);
            rs = stmt.executeQuery();

            while(rs.next())
            {
                authToken = new AuthToken();
                authToken.setUserName(rs.getString(2));
                authToken.setAuthID(rs.getString(3));
                authToken.setExp_time(rs.getString(4));

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (rs != null)
            {
                rs.close();
            }
        }
        return authToken;
    }

    /*
    public AuthToken getAuthTokenByUsername(String userName) throws SQLException, Database.DatabaseException
    {
        db.createTables(); //Do I need to even create the tables?
        PreparedStatement stmt = null;
        ResultSet rs = null;
        AuthToken authToken = null;

        try
        {
            //I want to select the authToken from the username
            String sql = "select * from authToken where authToken.userName = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while(rs.next())
            {
                authToken = new AuthToken();
                authToken.setUserName(rs.getString(1));
                authToken.setAuthID(rs.getString(2));
                authToken.setExp_time(rs.getString(3));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (rs != null)
            {
                rs.close();
            }
        }
        return authToken;
    }*/

    /**
     *Deletes an authentication token from the database
     // @param token
     */
    public void deleteAuthToken(String authID) throws SQLException {
        PreparedStatement stmt = null;

        try
        {
            //I want to select the authToken from the username
            String sql = "delete from authToken where authID = ?";
            stmt = db.getConn().prepareStatement(sql);
            stmt.setString(1, authID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (stmt != null)
            {
                stmt.close();
            }
        }
    }
}
