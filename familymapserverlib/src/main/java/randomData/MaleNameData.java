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

public class MaleNameData extends RandomGenerator {

    //private String[] data;
    private ArrayList<String> data;

    public MaleNameData() {data = new ArrayList<String>();}

    public MaleNameData transferMaleNameData() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("jsondocs/mnames.json");
        MaleNameData maleNameData = gson.fromJson(reader, MaleNameData.class);
        return maleNameData;
    }

    public String getRandomMaleName() throws FileNotFoundException {
        //MaleName maleName = new MaleName();
        MaleNameData maleNameData = transferMaleNameData();
        int maleNameMax = maleNameData.getData().size();
        return maleNameData.getData().get(generateRandomNumber(maleNameMax));
    }

    public ArrayList<String> getData() {
        return data;
    }
}
