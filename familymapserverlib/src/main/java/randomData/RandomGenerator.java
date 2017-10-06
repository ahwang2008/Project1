package randomData;

import java.util.Random;

/**
 * Created by Hwang on 5/27/2017.
 */

public class RandomGenerator {
    public int generateRandomNumber(int number)
    {
        Random rand = new Random();
        int n = rand.nextInt(number) + 0;
        return n;
    }
}
