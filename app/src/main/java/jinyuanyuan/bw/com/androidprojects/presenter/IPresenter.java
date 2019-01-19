package jinyuanyuan.bw.com.androidprojects.presenter;

import java.util.Map;

public interface IPresenter {

    //post 请求数据
    void postHeader(String url, Map<String, Object> headmaps, Map<String, Object> map, Class clazz);

    //get 请求数据
    void getHeader(String url, Map<String, Object> headmaps, Map<String, Object> map, Class claas);

    //delete 请求数据
    void delRequest(String url,Map<String, Object> headmaps, Map<String, Object> map, Class classes);

    //put 请求数据
    void putRequest(String url,Map<String, Object> headmaps, Map<String, Object> map, Class classes);
}
