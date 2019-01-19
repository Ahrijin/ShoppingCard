package jinyuanyuan.bw.com.androidprojects.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.bean.MyWalletBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class MyWalletActivity extends AppCompatActivity implements IView {

    @BindView(R.id.balance)
    TextView balance;
    private PresenterImpls presenterImpls;
    private Map<String,Object> headmap = new HashMap<>();
    private Map<String,Object> map = new HashMap<>();
    private SharedPreferences jyy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);
        //获取userId和sessionId
        jyy = getSharedPreferences("jyy", MODE_PRIVATE);
        final String sess = jyy.getString("sess", null);
        final int userid = jyy.getInt("userid", 0);
        headmap.put("userId",userid);
        headmap.put("sessionId",sess);
        //入参需要拼接
        map.put("page",1);
        map.put("count",1);
        //请求数据
        presenterImpls = new PresenterImpls(this);
        presenterImpls.getHeader(Contacts.FIND_USER_WALLET,headmap,map,MyWalletBean.class);
    }

    @OnClick(R.id.balance)
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.balance:

                break;
        }
    }

    @Override
    public void setSuccess(Object datas) {
        MyWalletBean myWalletBean = (MyWalletBean) datas;
        Toast.makeText(this, myWalletBean.getMessage(), Toast.LENGTH_SHORT).show();
        //bal是int类型，需要加引号
        int bal = myWalletBean.getResult().getBalance();
        balance.setText(bal+"");
    }

    @Override
    public void setError(Object errors) {

    }
}
