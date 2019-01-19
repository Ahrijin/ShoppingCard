package jinyuanyuan.bw.com.androidprojects.presenter;

import java.util.Map;

import jinyuanyuan.bw.com.androidprojects.callback.MyCallBack;
import jinyuanyuan.bw.com.androidprojects.model.ModelImpls;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class PresenterImpls implements IPresenter {
    private ModelImpls modelImpls;
    private IView iView;

    public PresenterImpls(IView iView) {
        this.iView = iView;
        modelImpls = new ModelImpls();
    }

    @Override
    public void postHeader(String url, Map<String, Object> headmaps, Map<String, Object> map, Class clazz) {
        modelImpls.postHeader(url, headmaps, map, clazz, new MyCallBack() {
            @Override
            public void Success(Object success) {
                iView.setSuccess(success);
            }

            @Override
            public void Errors(Object error) {
                iView.setError(error);
            }
        });
    }

    @Override
    public void getHeader(String url, Map<String, Object> headmaps, Map<String, Object> map, Class claas) {
        modelImpls.getHeader(url, headmaps, map, claas, new MyCallBack() {
            @Override
            public void Success(Object success) {
                iView.setSuccess(success);
            }

            @Override
            public void Errors(Object error) {
                iView.setError(error);
            }
        });
    }

    @Override
    public void delRequest(String url, Map<String, Object> headmaps, Map<String, Object> map, Class classes) {
        modelImpls.deleteRequest(url, headmaps, map, classes, new MyCallBack() {
            @Override
            public void Success(Object success) {
                iView.setSuccess(success);
            }

            @Override
            public void Errors(Object error) {
                iView.setError(error);
            }
        });
    }

    @Override
    public void putRequest(String url, Map<String, Object> headmaps, Map<String, Object> map, Class classes) {
        modelImpls.putRequest(url, headmaps, map, classes, new MyCallBack() {
            @Override
            public void Success(Object success) {
                iView.setSuccess(success);
            }

            @Override
            public void Errors(Object error) {
                iView.setError(error);
            }
        });
    }

    public void destory() {
        if (modelImpls != null) {
            modelImpls = null;
        }
        if (iView != null) {
            iView = null;
        }
    }


}
