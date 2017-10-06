package service;

import java.sql.SQLException;
import java.util.Calendar;

import dataAccess.AuthTokenDao;
import dataAccess.Database;
import dataAccess.EventDao;
import model.AuthToken;
import model.Event;
import result.EventIDResult;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Returns the single Event object with the specified ID.
 */
public class EventIDService extends Service {

    public EventIDService() {
    }

    /**
     * Returns the single Event object with the specified ID.
     */
    public EventIDResult eventID(String authID, String eventID) throws Database.DatabaseException, SQLException {
        Database db = new Database();
        EventIDResult er = new EventIDResult();
        try{
            db.openConnection();
        }
        catch (Database.DatabaseException e)
        {
            e.printStackTrace();
        }
        AuthTokenDao authTokendb = new AuthTokenDao(db);
        EventDao eventdb = new EventDao(db);

        AuthToken authToken = authTokendb.getAuthToken(authID);
        Event event = eventdb.getEvent(eventID);

        long expTime = Long.parseLong(authToken.getExp_time());
        Calendar cal = Calendar.getInstance();
        long currTime = cal.getTimeInMillis();

        if (currTime < expTime)
        {
            if(eventdb.getEvent(eventID) != null) //If the event exists...
            {
                if (event.getDescendant().equals(authToken.getUserName()))
                {
                    er.setDescendant(event.getDescendant());
                    er.setEventID(event.getEventID());
                    er.setPersonID(event.getPersonID());
                    er.setLatitude(event.getLatitude());
                    er.setLongitude(event.getLongitude());
                    er.setCountry(event.getCountry());
                    er.setCity(event.getCity());
                    er.setEventType(event.getEventType());
                    er.setYear(event.getYear());
                }
                else
                {
                    er.setErrorMessage("Requested person does not belong to this user");
                }
            }
            else
            {
                er.setErrorMessage("Invalid eventID parameter");
            }
        }
        else
        {
            er.setErrorMessage("Auth token has expired");
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
