package jinyuanyuan.bw.com.androidprojects.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jinyuanyuan.bw.com.androidprojects.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    @BindView(R.id.list_allorder)
    ImageView listAllorder;
    @BindView(R.id.list_completed)
    ImageView listCompleted;
    @BindView(R.id.list_goods)
    ImageView listGoods;
    @BindView(R.id.list_evaluate)
    ImageView listEvaluate;
    @BindView(R.id.list_finish)
    ImageView listFinish;
    @BindView(R.id.list_frame)
    FrameLayout listFrame;
    Unbinder unbinder;
    private View v;
    private FragmentManager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, v);
        manager = getChildFragmentManager();
        manager.beginTransaction().replace(R.id.list_frame,new AllOrdersFragment()).commit();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.list_allorder, R.id.list_completed, R.id.list_goods, R.id.list_evaluate, R.id.list_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.list_allorder://全部订单
                manager.beginTransaction().replace(R.id.list_frame,new AllOrdersFragment()).commit();
                break;
            case R.id.list_completed://待付款
                manager.beginTransaction().replace(R.id.list_frame,new ForPaymentFragment()).commit();
                break;
            case R.id.list_goods://待收货
                manager.beginTransaction().replace(R.id.list_frame,new ForGoodsFragment()).commit();
                break;
            case R.id.list_evaluate://待评价
                manager.beginTransaction().replace(R.id.list_frame,new ToEvaluateFragment()).commit();
                break;
            case R.id.list_finish://已完成
                manager.beginTransaction().replace(R.id.list_frame,new HasCompletedFragment()).commit();
                break;
        }
    }
}
