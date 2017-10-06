package randomData;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * Created by Hwang on 5/27/2017.
 */

public class LocationData extends RandomGenerator {

    private ArrayList<Location> data;

    public LocationData() {
        data = new ArrayList<Location>();
    }

    public LocationData transferLocationData() throws IOException {
        //Gson gson = new Gson();
        Reader reader = new FileReader("jsondocs/locations.json");
        LocationData locData = new Gson().fromJson(reader, LocationData.class);
        //System.out.println(locData.toString());
        return locData;
    }

    public Location getRandomLocation() throws IOException {
        LocationData locData = transferLocationData();
        int locationMax = locData.getData().size();
        return locData.getData().get(generateRandomNumber(locationMax));
    }

    public ArrayList<Location> getData() {
        return data;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < data.size(); i++)
        {
            str.append(data.get(i).toString());
        }
        return str.toString();
    }
}
