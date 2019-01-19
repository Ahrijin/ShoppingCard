package jinyuanyuan.bw.com.androidprojects.utils;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface MyApiService {
    //封装get
    @GET
    Observable<ResponseBody> getHeader(@Url String url,@HeaderMap Map<String,Object> headmaps,@QueryMap Map<String,Object> map);

    //封装post
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postHeader(@Url String url,@HeaderMap Map<String,Object> headmaps, @FieldMap Map<String, Object> map);

    //圈子取消点赞
    @DELETE
    Observable<ResponseBody> delete(@Url String url,@HeaderMap Map<String,Object> headmaps,@QueryMap Map<String,Object> map);

    //封装put
    @PUT
    Observable<ResponseBody> putHeader(@Url String url,@HeaderMap Map<String,Object> headmaps,@QueryMap Map<String,Object> map);
}
