package util;

import okhttp3.*;
import okhttp3.OkHttpClient.Builder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 静态化一个okhttp实例
 * @author zsq
 * @version 1.0
 * @date 2021/3/31 10:58
 */
public class HttpClient {

    /**okhttp实例**/
    public static Builder client;

    private static String host = "cat-match.easygame2021.com";
    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36 Edg/105.0.1343.33";
    private static String xRequestedWith = "XMLHttpRequest";

    static {
        //初始化一个okhttp实例
        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 20, TimeUnit.SECONDS))
                .readTimeout(15, TimeUnit.SECONDS)
                //添加请求头
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //添加请求头
                        Request request = chain.request().newBuilder()
                                .addHeader("Host", host)
                                .addHeader("User-Agent", userAgent)
                                .addHeader("xRequestedWith", xRequestedWith)
                                .build();

                        return chain.proceed(request);
                    }
                });
    }

}
