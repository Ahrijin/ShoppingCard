package jinyuanyuan.bw.com.androidprojects.model;

import java.util.Map;

import jinyuanyuan.bw.com.androidprojects.callback.MyCallBack;
import retrofit2.http.HeaderMap;

public interface Model {

     //post请求数据
    void postHeader(String url,  Map<String, Object> headmaps, Map<String, Object> map, Class clazz, MyCallBack callBack);

    //get请求数据
    void getHeader(String url,  Map<String, Object> headmaps,Map<String, Object> map,Class claas, MyCallBack callBack);

    //delete请求数据
    void deleteRequest(String url,Map<String,Object> headmaps, Map<String, Object> map, Class classes, MyCallBack callBack);

    //put请求数据
    void putRequest(String url,Map<String,Object> headmaps, Map<String, Object> map, Class classes, MyCallBack callBack);

}
