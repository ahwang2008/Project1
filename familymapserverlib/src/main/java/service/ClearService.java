package service;

import java.sql.SQLException;

import dataAccess.Database;
import result.ClearResult;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Deletes ALL data from the database, including user accounts, auth tokens, and
 generated person and event data.
 */
public class ClearService extends Service {

    public ClearService() {
    }

    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and
     generated person and event data.
     */
    public boolean clear() throws Database.DatabaseException, SQLException {
        Database db = new Database();
        boolean success = false;
        try{
            db.openConnection();
        }
        catch (Database.DatabaseException e) {
            e.printStackTrace();
        }
        db.dropTables();
        success = true;
        try{
            db.closeConnection(true);
        }
        catch (Database.DatabaseException e){
            e.printStackTrace();
        }
        return success;
    }
}
