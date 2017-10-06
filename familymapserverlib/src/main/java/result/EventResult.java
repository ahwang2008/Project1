package result;

import java.util.ArrayList;

import model.Event;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Holds the return values for event result.
 */
public class EventResult {

    private ArrayList<Event> data;
    private String errorMessage;

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
