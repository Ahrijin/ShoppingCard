package jinyuanyuan.bw.com.androidprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xw.repo.XEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jinyuanyuan.bw.com.androidprojects.LoginActivity;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.bean.EnrollBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class RgisterActivity extends AppCompatActivity implements IView {

    @BindView(R.id.rgisters)
    Button btn;
    @BindView(R.id.tel)
    XEditText tel;
    @BindView(R.id.xedit_yan)
    XEditText xeditYan;
    @BindView(R.id.account)
    TextView account;
    private PresenterImpls presenterImpls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister);
        ButterKnife.bind(this);
        presenterImpls = new PresenterImpls(this);
    }

    @OnClick({R.id.rgisters,R.id.account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rgisters:
                String tels = tel.getTrimmedString().trim();
                String code = xeditYan.getTrimmedString().trim();
                Map<String, Object> maps = new HashMap<>();
                Map<String, Object> map = new HashMap<>();
                map.put("phone", tels);
                map.put("pwd", code);
                presenterImpls.postHeader(Contacts.REGISTER, maps,map, EnrollBean.class);
                break;
            case R.id.account:
                startActivity(new Intent(RgisterActivity.this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void setSuccess(Object datas) {
        EnrollBean enrollBean = (EnrollBean) datas;
        if (enrollBean.getMessage().equals("注册成功")) {
            Toast.makeText(this, enrollBean.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, enrollBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setError(Object errors) {

    }
}
