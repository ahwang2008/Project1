package result;

import java.lang.reflect.Array;
import java.util.ArrayList;

import model.Person;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Holds the return values for person.
 */
public class PersonResult {

    private ArrayList<Person> data;
    private String errorMessage;

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
