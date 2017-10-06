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

public class FemaleNameData extends RandomGenerator {

    private ArrayList<String> data;

    public FemaleNameData() {data = new ArrayList<String>();}

    public FemaleNameData transferFemaleNameData() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("jsondocs/fnames.json");
        FemaleNameData femaleNameData = gson.fromJson(reader, FemaleNameData.class);
        return femaleNameData;
    }

    public String getRandomFemaleName() throws FileNotFoundException {
        FemaleNameData femaleNameData = transferFemaleNameData();
        int femaleNameMax = femaleNameData.getData().size();
        return femaleNameData.getData().get(generateRandomNumber(femaleNameMax));
    }

    public ArrayList<String> getData() {
        return data;
    }
}
