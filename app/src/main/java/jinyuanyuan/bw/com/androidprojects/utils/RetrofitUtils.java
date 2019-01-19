package jinyuanyuan.bw.com.androidprojects.utils;

import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RetrofitUtils {
    private MyApiService myApiService1;

    private RetrofitUtils(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Contacts.BASE_URL)
                .client(okHttpClient)
                .build();
        myApiService1 = retrofit.create(MyApiService.class);
    }
    public static RetrofitUtils getInstance() {
        return RetroHolder.retro;
    }

    private static class RetroHolder {
        private static final RetrofitUtils retro = new RetrofitUtils();
    }

    public void getHeader(String url, Map<String, Object> headmaps,Map<String, Object> map, final HttpListener listener) {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.e("onCompleted", "onCompleted");
            }

            //网络处理失败
            @Override
            public void onError(Throwable e) {
                Log.e("onError", "onError" + e.getMessage());
                if (listener != null) {
                    listener.onError(e.getMessage());
                }
            }

            //网络处理成功
            @Override
            public void onNext(ResponseBody responseBody) {
                Log.d("onNext", "onNext");
                if (listener != null) {
                    try {
                        listener.onSuccess(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        myApiService1.getHeader(url,headmaps, map)
                .subscribeOn(Schedulers.io())//io就是子线程
                .observeOn(AndroidSchedulers.mainThread())//在主线程调用
                .subscribe(observer);
    }


    public void postHeader(String url, Map<String, Object> headmaps, Map<String, Object> map, final HttpListener listener) {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.e("onCompleted", "onCompleted");
            }

            //网络处理失败
            @Override
            public void onError(Throwable e) {
                Log.e("onError", "onError" + e.getMessage());
                if (listener != null) {
                    listener.onError(e.getMessage());
                }
            }

            //网络处理成功
            @Override
            public void onNext(ResponseBody responseBody) {
                Log.d("onNext", "onNext");
                if (listener != null) {
                    try {
                        listener.onSuccess(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        myApiService1.postHeader(url,headmaps, map)
                .subscribeOn(Schedulers.io())//io就是子线程
                //在主线程调用
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    //取消点赞
    public void del(String url,Map<String,Object> headmaps,Map<String, Object> map, final HttpListener listener) {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.e("onCompleted", "onCompleted");
            }

            //网络处理失败
            @Override
            public void onError(Throwable e) {
                Log.e("onError", "onError" + e.getMessage());
                if (listener != null) {
                    listener.onError(e.getMessage());
                }
            }

            //网络处理成功
            @Override
            public void onNext(ResponseBody responseBody) {
                Log.d("onNext", "onNext");
                if (listener != null) {
                    try {
                        listener.onSuccess(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        myApiService1.delete(url,headmaps, map)
                .subscribeOn(Schedulers.io())//io就是子线程
                //在主线程调用
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    //put
    public void put(String url,Map<String,Object> headmaps,Map<String, Object> map, final HttpListener listener) {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.e("onCompleted", "onCompleted");
            }

            //网络处理失败
            @Override
            public void onError(Throwable e) {
                Log.e("onError", "onError" + e.getMessage());
                if (listener != null) {
                    listener.onError(e.getMessage());
                }
            }

            //网络处理成功
            @Override
            public void onNext(ResponseBody responseBody) {
                Log.d("onNext", "onNext");
                if (listener != null) {
                    try {
                        listener.onSuccess(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        myApiService1.putHeader(url,headmaps, map)
                .subscribeOn(Schedulers.io())//io就是子线程
                //在主线程调用
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public interface HttpListener {
        void onSuccess(String jsonStr);

        void onError(String error);
    }

}
