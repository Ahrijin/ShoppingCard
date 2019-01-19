package jinyuanyuan.bw.com.androidprojects.model;

import android.util.Log;

import com.google.gson.Gson;
import java.util.Map;
import jinyuanyuan.bw.com.androidprojects.callback.MyCallBack;
import jinyuanyuan.bw.com.androidprojects.utils.RetrofitUtils;

public class ModelImpls implements Model {

    //post 请求数据
    @Override
    public void postHeader(String url, Map<String, Object> headmaps, Map<String, Object> map, final Class clazz, final MyCallBack callBack) {
        RetrofitUtils.getInstance().postHeader(url, headmaps, map, new RetrofitUtils.HttpListener() {
            @Override
            public void onSuccess(String jsonStr) {
                Gson gson = new Gson();
                Object json = gson.fromJson(jsonStr, clazz);
                callBack.Success(json);
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    //get 请求数据
    @Override
    public void getHeader(String url, Map<String, Object> headmaps, Map<String, Object> map, final Class claas, final MyCallBack callBack) {
        RetrofitUtils.getInstance().getHeader(url, headmaps, map, new RetrofitUtils.HttpListener() {
            @Override
            public void onSuccess(String jsonStr) {
                Gson gson = new Gson();
                Object json = gson.fromJson(jsonStr, claas);
                callBack.Success(json);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void deleteRequest(String url, Map<String, Object> headmaps, Map<String, Object> map, final Class classes, final MyCallBack callBack) {
        RetrofitUtils.getInstance().del(url, headmaps, map, new RetrofitUtils.HttpListener() {
            @Override
            public void onSuccess(String jsonStr) {
                Gson gson = new Gson();
                Object json = gson.fromJson(jsonStr,classes);
                callBack.Success(json);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void putRequest(String url, Map<String, Object> headmaps, Map<String, Object> map, final Class classes, final MyCallBack callBack) {
        RetrofitUtils.getInstance().put(url, headmaps, map, new RetrofitUtils.HttpListener() {
            @Override
            public void onSuccess(String jsonStr) {
                Gson gson = new Gson();
                Object json = gson.fromJson(jsonStr,classes);
                Log.d("zzz",json+"");
                callBack.Success(json);
            }

            @Override
            public void onError(String error) {

            }
        });
    }


}
