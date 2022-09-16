package action.sheep;

import action.MyX509TrustManager;
import com.alibaba.fastjson.JSONObject;
import util.RandomUtil;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;

public class sheepData {

	private static String token = "ehbGciOiJIUI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTQ0MDY1MzYsIm5iZiI6MTY2MzMwNDMzNiwiaWF0IjoxNjYzMzAyNTM2LCJqdGkiOiJDTTpjYXRfbWF0Y2g6bHQxMjM0NTYiLCJvcGVuX2lkIjoiIiwidWlkIjo5Mzc2NjE4OSwiZGVidWciOiIiLCJsYW5nIjoiIn0.Bv-Wwmi6CN9Ya2MRhe8f3-WYrMV83DaVNVqRjOm0ZuE";

	public static void main(String[] args) {
		try {
			System.out.println("【羊了个羊一键闯关开始启动】");
			int cycle_count = 100;
			for (int i = 0; i < cycle_count; i++) {
				System.out.println("...第"+(i + 1)+"开始完成闯关...");
				int cost_time = RandomUtil.getRandom(1, 3600);
				System.out.println("生成随机完成耗时:"+cost_time);
				finish_game(1, cost_time);
				System.out.println("(【羊了个羊一键闯关开始结束】"+cost_time);
				Thread.sleep(3000L);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void finish_game(int state, int rank_time) {
		try {
			String finish_api = "https://cat-match.easygame2021.com/sheep/v1/game/game_over?rank_score=1&rank_state=%s&rank_time=%s&rank_role=1&skin=1";
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(String.format(finish_api, state, rank_time));
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setSSLSocketFactory(ssf);
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(20*1000);
			connection.setReadTimeout(20*1000);
			connection.setRequestProperty("Host", "cat-match.easygame2021.com");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36 Edg/105.0.1343.33");
			connection.setRequestProperty("t", token);
			if (connection.getResponseCode() == 200) {
				InputStream inputStream = connection.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"));
				String line = null;
				StringBuffer respBuffer = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					respBuffer.append(line);
				}
				JSONObject json = JSONObject.parseObject(respBuffer.toString());
				if (json.getInteger("err_code") != 0) {
					System.out.println("执行异常===>"+json.toString());
					return;
				}
				System.out.println("执行成功===>"+json.toString());

				reader.close();
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
