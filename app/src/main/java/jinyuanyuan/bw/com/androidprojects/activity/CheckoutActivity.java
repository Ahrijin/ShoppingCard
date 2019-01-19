package jinyuanyuan.bw.com.androidprojects.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.adapter.PopAdapter;
import jinyuanyuan.bw.com.androidprojects.adapter.ShopCardAdapter;
import jinyuanyuan.bw.com.androidprojects.bean.MyAddrBean;
import jinyuanyuan.bw.com.androidprojects.bean.MyAddressBean;
import jinyuanyuan.bw.com.androidprojects.bean.SeleteDataBean;
import jinyuanyuan.bw.com.androidprojects.bean.WaitPayBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class CheckoutActivity extends AppCompatActivity implements IView {

    @BindView(R.id.check_address)//暂无收获地址  点击添加
            TextView checkAddress;
    @BindView(R.id.checkout_recy)
    RecyclerView checkoutRecy;
    @BindView(R.id.text_view)
    TextView textView;
    @BindView(R.id.btn_checkout)//提交订单
            Button btnCheckout;
    @BindView(R.id.check_address_name)//姓名
            TextView checkAddressName;
    @BindView(R.id.check_address_phone)//电话
            TextView checkAddressPhone;
    @BindView(R.id.check_address_address)//地址
            TextView checkAddressAddress;
    @BindView(R.id.pops)
    RelativeLayout pops;

    private Map<String, Object> headmap = new HashMap<>();
    private Map<String, Object> map = new HashMap<>();
    private Map<String, Object> maps = new HashMap<>();
    private SharedPreferences jyy;
    private PresenterImpls presenterImpls;
    private ShopCardAdapter shopCardAdapter;
    private List<MyAddressBean.ResultBean> addressList = new ArrayList<>();//商品集合
    private List<MyAddrBean.ResultBean> addrsLists = new ArrayList<>();//收获地址
    private List<SeleteDataBean.ResultBean> list = new ArrayList<>();
    private List<SeleteDataBean.ResultBean> mLists = new ArrayList<>();//被选中的商品添加到的集合
    private String id;
    float sum = 0;
    private PopAdapter popAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);

        //获取userId和sessionId
        jyy = getSharedPreferences("jyy", MODE_PRIVATE);
        final String sess = jyy.getString("sess", null);
        final int userid = jyy.getInt("userid", 0);
        headmap.put("userId", userid);
        headmap.put("sessionId", sess);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        checkoutRecy.setLayoutManager(linearLayoutManager);

        shopCardAdapter = new ShopCardAdapter(mLists, this);
        checkoutRecy.setAdapter(shopCardAdapter);

        presenterImpls = new PresenterImpls(this);
        presenterImpls.getHeader(Contacts.FIND_SHOPINGCART, headmap, map, SeleteDataBean.class);


    }


    @OnClick({R.id.check_address, R.id.text_view, R.id.btn_checkout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //添加收获地址
            case R.id.check_address:
                View inflate = LayoutInflater.from(this).inflate(R.layout.addresspop, null);
                RecyclerView popRecy = inflate.findViewById(R.id.pop_recy);
                //设置收获地址
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                popRecy.setLayoutManager(linearLayoutManager);
                popAdapter = new PopAdapter(addressList, this);
                popRecy.setAdapter(popAdapter);
                presenterImpls = new PresenterImpls(this);
                presenterImpls.getHeader(Contacts.RECEIVE_ADDRESS_LIST, headmap, map, MyAddressBean.class);

                final PopupWindow popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, 500, true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAsDropDown(checkAddress);

                popAdapter.setPopListener(new PopAdapter.PopListener() {
                    @Override
                    public void getData(int position) {
                        checkAddress.setVisibility(View.GONE);
                        pops.setVisibility(View.VISIBLE);
                        checkAddressName.setText(addressList.get(position).getRealName() + "");
                        checkAddressPhone.setText(addressList.get(position).getPhone() + "");
                        checkAddressAddress.setText(addressList.get(position).getAddress() + "");
                        popupWindow.dismiss();//pop窗口关闭
                    }
                });

                break;
            //提交订单
            case R.id.btn_checkout:
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i <mLists.size() ; i++) {
                    JSONObject jsonObject = null;
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("commodityId", list.get(i).getCommodityId());
                        jsonObject.put("count", 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }
                maps.put("orderInfo",jsonArray.toString());
                maps.put("totalPrice",sum+"");
                maps.put("addressId",addrsLists.get(1).getId());
                presenterImpls.postHeader(Contacts.CREATEORDER,headmap,maps,WaitPayBean.class);
                break;
        }
    }

    @Override
    public void setSuccess(Object datas) {
        if (datas instanceof SeleteDataBean) {
            SeleteDataBean seleteDataBean = (SeleteDataBean) datas;
            list.addAll(seleteDataBean.getResult());
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            String[] split = id.split(" ");
            for (int j = 0; j < split.length; j++) {
                int s = Integer.parseInt(split[j]);
                for (int i = 0; i < list.size(); i++) {
                    if (s == list.get(i).getCommodityId()) {
                        mLists.add(list.get(i));
                        sum = sum + list.get(i).getCount() * list.get(i).getPrice();
                    }
                }
            }
            textView.setText("共" + split.length + "件商品，需要付款" + sum + "元");
            shopCardAdapter.notifyDataSetChanged();
        }

        if (datas instanceof MyAddrBean) {
            MyAddrBean myAddrBean = (MyAddrBean) datas;
            addrsLists.addAll(myAddrBean.getResult());
        }
    }

    @Override
    public void setError(Object errors) {

    }

}
