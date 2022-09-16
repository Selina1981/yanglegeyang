package util;

import java.util.Random;

public class RandomUtil {
    public static int getRandom(int m, int n) {
        try {
            Random random = new Random();
            int index = random.nextInt(n - m) + m;
            return index;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 15;
    }
}
