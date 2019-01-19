package jinyuanyuan.bw.com.androidprojects.utils;

import java.util.HashMap;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkhttpUtils {
    private OkHttpClient okHttpClient;
    private OkhttpUtils(){
        okHttpClient = new OkHttpClient();
    }

    public static OkhttpUtils getInstance(){
        return OkHttpHolder.utils;
    }

    static class OkHttpHolder{
        private static final OkhttpUtils utils = new OkhttpUtils();
    }

    //异步get方法
    public void getAsync(String url, Callback callback){
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //异步post方法
    public void postAsyc(String mUrl, HashMap<String,String> map,Callback callback){
        FormBody.Builder body = new FormBody.Builder();
        for(String key : map.keySet()){
            body.add(key,map.get(key));
        }
        Request request = new Request.Builder().url(mUrl).post(body.build()).build();
        okHttpClient.newCall(request).enqueue(callback);
    }


}
