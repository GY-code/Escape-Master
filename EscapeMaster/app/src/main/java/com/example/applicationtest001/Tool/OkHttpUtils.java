package com.example.applicationtest001.Tool;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpUtils {
    private static int TimeOut = 120;
    //单例获取ohttp3对象
    private static OkHttpClient client = null;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static synchronized OkHttpClient getInstance() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .readTimeout(TimeOut, TimeUnit.SECONDS)
                    .connectTimeout(TimeOut, TimeUnit.SECONDS)
                    .writeTimeout(TimeOut, TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }

    public static void sendOkHttpRequest(final String address, final Callback callback){
        client=getInstance();
        Request request=new Request.Builder()
                .url(address)
                .build();
        //enqueue方法内部已经帮助我们开启好了线程，最终的结果会回调到callback中
        client.newCall(request).enqueue(callback);
    }

    //使用POST方式向服务器提交数据并获取返回提示数据
    public static void sendOkHttpResponse(final String address,
                                          final RequestBody requestBody, final Callback callback){
        client=getInstance();
        //JSONObject这里是要提交的数据部分
        Request request=new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

//    public static void sendOkHttpFile(final String address,String path, final Callback callback)
//    {
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);
//        builder.addFormDataPart(key1, file.getName(), fileBody);
//        //创建请求体
//        RequestBody requestBody = builder.build();
//        OkHttpClient okhttp = getInstance();
//        okhttp.newBuilder().followRedirects(true);
//        Request request = new Request.Builder().url(address).post(requestBody).build();
//        client.newCall(request).enqueue(callback);
//    }

}
