package randomData;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import com.google.gson.*;

/**
 * Created by Hwang on 5/27/2017.
 */

public class Location {
    private String latitude;
    private String longitude;
    private String city;
    private String country;

    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("Latitude:" + latitude);
        str.append("Longitude:" + longitude);
        str.append("City:" + city);
        str.append("Country:" + country);
        return str.toString();
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
