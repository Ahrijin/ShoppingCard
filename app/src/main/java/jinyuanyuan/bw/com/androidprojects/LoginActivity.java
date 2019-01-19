package jinyuanyuan.bw.com.androidprojects;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.xw.repo.XEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jinyuanyuan.bw.com.androidprojects.activity.HomeActivity;
import jinyuanyuan.bw.com.androidprojects.activity.RgisterActivity;
import jinyuanyuan.bw.com.androidprojects.bean.LoginBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class LoginActivity<T> extends AppCompatActivity implements IView<T> {

    //手机号
    @BindView(R.id.Main_Phone)
    XEditText MainPhone;
    //密码
    @BindView(R.id.Main_Password)
    XEditText MainPassword;
    //记住密码
    @BindView(R.id.Main_CheckBox)
    CheckBox MainCheckBox;
    //快速注册
    @BindView(R.id.fast_register)
    TextView fastRegister;
    //登录
    @BindView(R.id.Main_Login)
    Button MainLogin;

    private PresenterImpls presenterImpls;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String phone;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenterImpls = new PresenterImpls(this);
        sp = getSharedPreferences("jyy", MODE_PRIVATE);
        editor = sp.edit();
        boolean jizhumima = sp.getBoolean("jizhumima", false);
        String name = sp.getString("name", null);
        String pass = sp.getString("pass", null);
        MainCheckBox.setChecked(jizhumima);
        if(jizhumima){
            MainPhone.setText(name);
            MainPassword.setText(pass);
        }

    }

    @OnClick({R.id.Main_Phone, R.id.Main_Password, R.id.Main_CheckBox, R.id.fast_register, R.id.Main_Login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Main_Phone:
                break;
            case R.id.Main_Password:
                break;
            case R.id.Main_CheckBox:
                if(!MainCheckBox.isChecked()){
                    MainCheckBox.setChecked(false);
                }
                break;
            case R.id.fast_register:
                startActivity(new Intent(LoginActivity.this, RgisterActivity.class));
                break;
            case R.id.Main_Login:
                //获取输入框中输入的手机号和密码
                phone = MainPhone.getTrimmedString().trim();
                pass = MainPassword.getTrimmedString().trim();
                Map<String, Object> headmaps = new HashMap<>();
                Map<String, Object> map = new HashMap<>();
                map.put("phone", phone);
                map.put("pwd", pass);
                presenterImpls.postHeader(Contacts.LOGIN,headmaps,map,LoginBean.class);
                break;
        }
    }

    @Override
    public void setSuccess(T datas) {
        LoginBean loginBean = (LoginBean) datas;
        if (loginBean.getMessage().equals("登录成功")) {
            if(MainCheckBox.isChecked()){
                editor.putBoolean("jizhumima", true);
                editor.putString("name", phone);
                editor.putString("pass", pass);
            }else {
                editor.putBoolean("jizhumima",false);
            }
            editor.putInt("userid",loginBean.getResult().getUserId());
            editor.putString("sess",loginBean.getResult().getSessionId());
            //提交
            editor.commit();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        } else {
            Toast.makeText(this, loginBean.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void setError(T errors) {

    }
}
