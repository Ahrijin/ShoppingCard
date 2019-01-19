package jinyuanyuan.bw.com.androidprojects.callback;

public interface MyCallBack<T> {
    void Success(T success);
    void Errors(T error);
}
