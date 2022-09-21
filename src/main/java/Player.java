import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.HttpClient;
import util.RandomUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Player {
    private static OkHttpClient.Builder client = HttpClient.client;
    String name;
    String token;

    long target = 999999999;
    ExecutorService executorService;

    AtomicLong tryTimes = new AtomicLong(0);

    AtomicLong successTimes = new AtomicLong(0);

    public Player(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public void play(){
        int n = 100;
        executorService = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++) {
            executorService.execute(this::task);
        }
        executorService.execute(this::statisticsTask);
    }

    public void task(){
        while (true){
            if(successTimes.get() <= target) {
                int cost_time = RandomUtil.getRandom(1, 3600);
                finish_game(1, cost_time);
            } else {
                System.out.println(name + "已完成目标" + target);
                break;
            }
        }
    }


    public void finish_game(int state, int rank_time) {
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
                tryTimes.incrementAndGet();
                response = client.build().newCall(request).execute();
                if (!response.isSuccessful()) {
//                    System.out.println("请求失败，尝试重试===>"+response.toString());
                    return;
                }
                long success = successTimes.incrementAndGet();
//                System.out.println(name + "已完成第" + success + "次闯关");

//                System.out.println("执行成功===>"+response.body().string());
            } catch (IOException e) {
//                System.out.println("===>请求超时，尝试重试");
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void statisticsTask(){
        while (true) {
            if(successTimes.get() >= target) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("%s已尝试 %d次, 成功%d次", name, tryTimes.get(), successTimes.get());
        }
    }
}
