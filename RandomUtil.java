package util;

import action.MyX509TrustManager;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.net.URL;
import java.util.Map;
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
