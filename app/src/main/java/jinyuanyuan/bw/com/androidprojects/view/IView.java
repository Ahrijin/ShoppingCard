package jinyuanyuan.bw.com.androidprojects.view;

public interface IView<T> {
    void setSuccess(T datas);
    void setError(T errors);
}
