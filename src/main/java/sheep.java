import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import util.HttpClient;
import util.RandomUtil;

import okhttp3.OkHttpClient.Builder;
import java.io.IOException;

public class sheep {

	// 替换你的token
	private static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTQzOTY2NjgsIm5iZiI6MTY2MzI5NDQ2OCwiaWF0IjoxNjYzMjkyNjY4LCJqdGkiOiJDTTpjYXRfbWF0Y2g6bHQxMjM0NTYiLCJvcGVuX2lkIjoiIiwidWlkIjoyMDcyOTQ5OTcsImRlYnVnIjoiIiwibGFuZyI6IiJ9.NVap_hjF66D8HQzzu6R9ImME1zH-BgNIR_wr8PKz1Ik";
	private static Builder client = HttpClient.client;

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
				Thread.sleep(300L);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void finish_game(int state, int rank_time) {
		try {
			String finish_api = "https://cat-match.easygame2021.com/sheep/v1/game/game_over?rank_score=1&rank_state=%s&rank_time=%s&rank_role=1&skin=1";
			Request.Builder reqBuild = new Request.Builder();
			HttpUrl.Builder urlBuilder = HttpUrl.parse(String.format(finish_api, state, rank_time))
					.newBuilder();
			reqBuild.header("t", token);
			reqBuild.url(urlBuilder.build());
			Request request = reqBuild.build();

			Response response = null;
			try {
				response = client.build().newCall(request).execute();
				if (!response.isSuccessful()) {
					System.out.println("请求不成功===>"+response.toString());
				}
				System.out.println("执行成功===>"+response.body().string());
			} catch (IOException e) {
				System.out.println("===>请求超时，服务器太多请求了，偶尔超时很正常");
			} finally {
				if (response != null) {
					response.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
