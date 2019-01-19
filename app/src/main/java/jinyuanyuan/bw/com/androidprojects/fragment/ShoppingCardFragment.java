package jinyuanyuan.bw.com.androidprojects.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.activity.CheckoutActivity;
import jinyuanyuan.bw.com.androidprojects.adapter.ShopCardAdapter;
import jinyuanyuan.bw.com.androidprojects.bean.SeleteDataBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCardFragment extends Fragment implements IView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;
    @BindView(R.id.allcheck)
    CheckBox allcheck;
    @BindView(R.id.heji)
    TextView heji;
    @BindView(R.id.btn_jie)
    Button btnJie;
    private View v;
    private PresenterImpls presenterImpls;
    private ShopCardAdapter shopCardAdapter;
    private List<SeleteDataBean.ResultBean> mLists = new ArrayList<>();
    private Map<String, Object> headmap = new HashMap<>();
    private Map<String, Object> map = new HashMap<>();
    private SharedPreferences jyy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_shopping_card, container, false);
        unbinder = ButterKnife.bind(this, v);

        //获取userId和sessionId
        jyy = getActivity().getSharedPreferences("jyy", Context.MODE_PRIVATE);
        final String sess = jyy.getString("sess", null);
        final int userid = jyy.getInt("userid", 0);
        headmap.put("userId", userid);
        headmap.put("sessionId", sess);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);

        shopCardAdapter = new ShopCardAdapter(mLists, getActivity());
        recycleView.setAdapter(shopCardAdapter);

        presenterImpls = new PresenterImpls(this);
        presenterImpls.getHeader(Contacts.FIND_SHOPINGCART, headmap, map, SeleteDataBean.class);


        shopCardAdapter.setCallback(new ShopCardAdapter.AdapterCallback() {
            @Override
            public void setChildCheck(int i) {
                //判断CheckBox当前状态
                boolean checked = shopCardAdapter.isChecked(i);
                shopCardAdapter.setChildCheck(i, !checked);
                flushBottomLayout();
                shopCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void setNum(int index, int num) {
                shopCardAdapter.setGoodsNumber(index, num);
                shopCardAdapter.notifyDataSetChanged();
                flushBottomLayout();
                if(num <= 0){
                    Toast.makeText(getContext(), "商品已售空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public void flushBottomLayout() {
        boolean allGoods = shopCardAdapter.isCheckAll();
        allcheck.setChecked(allGoods);
        float allGoodsPrice = shopCardAdapter.getShopPrice();
        heji.setText("￥合计：" + allGoodsPrice);
    }

    @Override
    public void setSuccess(Object datas) {
        SeleteDataBean seleteDataBean = (SeleteDataBean) datas;
        mLists.addAll(seleteDataBean.getResult());
        shopCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void setError(Object errors) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.allcheck, R.id.heji, R.id.btn_jie})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.allcheck:
                boolean checkAll = shopCardAdapter.isCheckAll();
                shopCardAdapter.checkAll(!checkAll);
                flushBottomLayout();
                shopCardAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_jie:
                if(shopCardAdapter.isCheckedGoods()){
                    String checked = shopCardAdapter.getChecked();
                    Intent intent = new Intent(getActivity(),CheckoutActivity.class);
                    intent.putExtra("id",checked);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "请勾选商品！", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
