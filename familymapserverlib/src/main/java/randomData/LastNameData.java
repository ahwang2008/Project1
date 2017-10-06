package randomData;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Hwang on 5/27/2017.
 */

public class LastNameData extends RandomGenerator {

    private ArrayList<String> data;

    public LastNameData() {data = new ArrayList<String>();}

    public LastNameData transferLastNameData() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("jsondocs/snames.json");
        LastNameData lastNameData = gson.fromJson(reader, LastNameData.class);
        return lastNameData;
    }

    public String getRandomLastName() throws FileNotFoundException {
        LastNameData lastNameData = transferLastNameData();
        int lastNameMax = lastNameData.getData().size();
        return lastNameData.getData().get(generateRandomNumber(lastNameMax));
    }

    public ArrayList<String> getData() {
        return data;
    }
}
