package jinyuanyuan.bw.com.androidprojects.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.adapter.MyAddressAdapter;
import jinyuanyuan.bw.com.androidprojects.bean.EnrollBean;
import jinyuanyuan.bw.com.androidprojects.bean.MyAddressBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class MyAddressActivity extends AppCompatActivity implements IView {

    @BindView(R.id.my_address_recy)
    RecyclerView myAddressRecy;
    @BindView(R.id.new_add_address)
    Button newAddAddress;
    private PresenterImpls presenterImpls;
    private Map<String, Object> headmap = new HashMap<>();
    private Map<String, Object> map = new HashMap<>();
    private Map<String, Object> mapmo = new HashMap<>();
    private Map<String, Object> mapup = new HashMap<>();
    private SharedPreferences jyy;
    private List<MyAddressBean.ResultBean> resultList = new ArrayList<>();
    private MyAddressAdapter myAddressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);

        //获取userId和sessionId
        jyy = getSharedPreferences("jyy", MODE_PRIVATE);
        final String sess = jyy.getString("sess", null);
        final int userid = jyy.getInt("userid", 0);
        headmap.put("userId", userid);
        headmap.put("sessionId", sess);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        myAddressRecy.setLayoutManager(linearLayoutManager);//设置显示样式

        myAddressAdapter = new MyAddressAdapter(resultList, MyAddressActivity.this);
        myAddressRecy.setAdapter(myAddressAdapter);//设置适配器

        presenterImpls = new PresenterImpls(this);
        presenterImpls.getHeader(Contacts.RECEIVE_ADDRESS_LIST,headmap,map,MyAddressBean.class);//收获地址列表I


        myAddressAdapter.setBtnListener(new MyAddressAdapter.ButtonListener() {
            @Override
            public void radioItemClick(View view, int position) {
                int id = resultList.get(position).getId();
                mapmo.put("id",id);
                presenterImpls.postHeader(Contacts.SET_DEFAULT_RECEIVE_ADDRESS,headmap,mapmo,EnrollBean.class);
            }

            @Override
            public void updateClick(View view, int position) {
                Intent intent = new Intent(MyAddressActivity.this,UpdateAddressActivity.class);
                intent.putExtra("name",resultList.get(position).getRealName());
                intent.putExtra("phone",resultList.get(position).getPhone());
                intent.putExtra("address",resultList.get(position).getAddress());
                intent.putExtra("code",resultList.get(position).getZipCode());
                intent.putExtra("id",resultList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setSuccess(Object datas) {
        if( datas instanceof MyAddressBean ){
            MyAddressBean myAddressBean = (MyAddressBean) datas;
            resultList.addAll(myAddressBean.getResult());
            myAddressAdapter.notifyDataSetChanged();
        }else if(datas instanceof EnrollBean){
            EnrollBean enrollBean = (EnrollBean) datas;
            Toast.makeText(this, enrollBean.getMessage(), Toast.LENGTH_SHORT).show();
            myAddressAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setError(Object errors) {

    }

    @OnClick(R.id.new_add_address)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.new_add_address:
                this.startActivity(new Intent(this,AddAddressActivity.class));
                break;
        }
    }
}
