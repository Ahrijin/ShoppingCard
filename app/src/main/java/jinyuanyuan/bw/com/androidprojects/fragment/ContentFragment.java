package jinyuanyuan.bw.com.androidprojects.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.adapter.RecycleHotAdapter;
import jinyuanyuan.bw.com.androidprojects.adapter.RecycleMoAdapter;
import jinyuanyuan.bw.com.androidprojects.adapter.RecyclePinAdapter;
import jinyuanyuan.bw.com.androidprojects.bean.BannerBean;
import jinyuanyuan.bw.com.androidprojects.bean.RecycleBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment implements IView {

    @BindView(R.id.xbanner)
    XBanner xbanner;
    @BindView(R.id.recycle_hot)
    RecyclerView recycleHot;
    @BindView(R.id.recycle_mo)
    RecyclerView recycleMo;
    @BindView(R.id.recycle_pin)
    RecyclerView recyclePin;
    Unbinder unbinder;
    private View v;
    private PresenterImpls presenterImpls;
    //xbanner轮播
    private ArrayList<String> lists = new ArrayList<>();
    //热销商品
    private List<RecycleBean.ResultBean.RxxpBean.CommodityListBean> rxxpList = new ArrayList<>();
    private RecycleHotAdapter hotAdapter;
    //魔力时尚
    private List<RecycleBean.ResultBean.MlssBean.CommodityListBeanXX> mlssList = new ArrayList<>();
    private RecycleMoAdapter moAdapter;
    //品质生活
    private List<RecycleBean.ResultBean.PzshBean.CommodityListBeanX> pzshList = new ArrayList<>();
    private RecyclePinAdapter pinAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_content, container, false);
        unbinder = ButterKnife.bind(this, v);
        hotAdapter = new RecycleHotAdapter(rxxpList, getActivity());
        moAdapter = new RecycleMoAdapter(mlssList, getActivity());
        pinAdapter = new RecyclePinAdapter(pzshList,getActivity());
        Map<String, Object> headmap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        presenterImpls = new PresenterImpls(this);
        presenterImpls.getHeader(Contacts.XBANNER,headmap, map, BannerBean.class);
        presenterImpls.getHeader(Contacts.COMMODITYLIST_URL, headmap,map, RecycleBean.class);
        initView();
        return v;
    }

    private void initView() {
        //热销商品
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recycleHot.setLayoutManager(gridLayoutManager);
        recycleHot.setAdapter(hotAdapter);

        //魔力时尚
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycleMo.setLayoutManager(linearLayoutManager);
        recycleMo.setAdapter(moAdapter);

        //品质生活
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2);
        recyclePin.setLayoutManager(gridLayoutManager1);
        recyclePin.setAdapter(pinAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setSuccess(Object datas) {
        if (datas instanceof BannerBean) {
            final BannerBean bannerBean = (BannerBean) datas;
            List<BannerBean.ResultBean> result = bannerBean.getResult();
            for (int i = 0; i < bannerBean.getResult().size(); i++) {
                lists.add(bannerBean.getResult().get(i).getImageUrl());
            }
            if (!lists.isEmpty()) {
                xbanner.setData(bannerBean.getResult(), null);
                xbanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(getActivity()).load(bannerBean.getResult().get(position).getImageUrl()).into((ImageView) view);
                    }
                });
                //横向移动
                xbanner.setPageTransformer(Transformer.Default);
            }
        }
        //热销商品
        if (datas instanceof RecycleBean) {
            RecycleBean recycleBean = (RecycleBean) datas;
            rxxpList.addAll(recycleBean.getResult().getRxxp().get(0).getCommodityList());
            hotAdapter.notifyDataSetChanged();
        }
        //魔力时尚
        if (datas instanceof RecycleBean) {
            RecycleBean recycleBean = (RecycleBean) datas;
            mlssList.addAll(recycleBean.getResult().getMlss().get(0).getCommodityList());
            moAdapter.notifyDataSetChanged();
        }

        //品质生活
        if (datas instanceof RecycleBean) {
            RecycleBean recycleBean = (RecycleBean) datas;
            pzshList.addAll(recycleBean.getResult().getPzsh().get(0).getCommodityList());
            pinAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setError(Object errors) {

    }
}
