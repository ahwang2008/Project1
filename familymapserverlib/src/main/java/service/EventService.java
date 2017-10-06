package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import dataAccess.AuthTokenDao;
import dataAccess.Database;
import dataAccess.EventDao;
import model.AuthToken;
import model.Event;
import result.EventResult;

/**
 * Created by ahwang13 on 5/19/17.
 */


/**
 * ALL events for ALL family members of the current user. The current
 user is determined from the provided auth token.
 */
public class EventService extends Service{
    public EventService() {
    }

    /**
     * ALL events for ALL family members of the current user. The current
     user is determined from the provided auth token.
     */
    public EventResult event(String authID) throws Database.DatabaseException, SQLException {
        Database db = new Database();
        EventResult er = new EventResult();

        try{
            db.openConnection();
        }
        catch(Database.DatabaseException e) {
            e.printStackTrace();
        }
        AuthTokenDao authTokendb = new AuthTokenDao(db);
        EventDao eventdb = new EventDao(db);

        AuthToken authToken = null;
        try{
            authToken = authTokendb.getAuthToken(authID);
        }
        catch(SQLException e)
        {
            er.setErrorMessage("Invalid auth token");
            e.printStackTrace();
        }
        catch(Database.DatabaseException e)
        {
            e.printStackTrace();
        }
        long expTime = Long.parseLong(authToken.getExp_time());
        Calendar cal = Calendar.getInstance();
        long currTime = cal.getTimeInMillis();

        if (currTime < expTime)
        {
            ArrayList<Event> eventArrayList = eventdb.getEvents(authToken.getUserName());
            er.setData(eventArrayList);
        }
        else
        {
            er.setErrorMessage("Chosen auth token has expired");
            authTokendb.deleteAuthToken(authID);
        }
        try{
            db.closeConnection(true);
        }
        catch (Database.DatabaseException e)
        {
            e.printStackTrace();
        }
        return er;
    }
}
