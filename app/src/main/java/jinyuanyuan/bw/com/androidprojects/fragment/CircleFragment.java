package jinyuanyuan.bw.com.androidprojects.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.adapter.RecycleQuanAdapter;
import jinyuanyuan.bw.com.androidprojects.bean.CircleBean;
import jinyuanyuan.bw.com.androidprojects.bean.EnrollBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment extends Fragment implements IView {
    @BindView(R.id.recycle_quan)
    XRecyclerView recycleQuan;
    Unbinder unbinder;
    private View v;
    private List<CircleBean.ResultBean> circleList = new ArrayList<>();
    private RecycleQuanAdapter recycleQuanAdapter;
    private PresenterImpls presenterImpls;
    private Map<String, Object> map = new HashMap<>();
    private Map<String, Object> headmaps = new HashMap<>();
    private int index = 1;
    private SharedPreferences jyy;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_circle, container, false);
        unbinder = ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //取值SharedPreferences中保存的sessionId和userId
        jyy = getActivity().getSharedPreferences("jyy", getActivity().MODE_PRIVATE);
        final String sess = jyy.getString("sess", null);
        final int userid = jyy.getInt("userid", 0);


        recycleQuanAdapter = new RecycleQuanAdapter(circleList, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleQuan.setLayoutManager(linearLayoutManager);
        recycleQuan.setAdapter(recycleQuanAdapter);

        map.put("userId", userid + "");
        map.put("sessionId", sess);
        map.put("page", "1");
        map.put("count", "5");
        presenterImpls = new PresenterImpls(this);
        presenterImpls.getHeader(Contacts.FINDCIRCLELIST_URL, headmaps, map, CircleBean.class);
        //Toast.makeText(getActivity(),userid+"" , Toast.LENGTH_SHORT).show();

        //XRecyclerView
        recycleQuan.setLoadingMoreEnabled(true);
        recycleQuan.setPullRefreshEnabled(true);
        recycleQuan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                circleList.clear();
                index = 1;
                map.put("page", index + "");
                presenterImpls.getHeader(Contacts.FINDCIRCLELIST_URL, headmaps, map, CircleBean.class);
                recycleQuanAdapter.notifyDataSetChanged();
                recycleQuan.refreshComplete();

            }

            @Override
            public void onLoadMore() {
                map.put("page", (++index) + "");
                presenterImpls.getHeader(Contacts.FINDCIRCLELIST_URL, headmaps, map, CircleBean.class);
                recycleQuanAdapter.notifyDataSetChanged();
                recycleQuan.loadMoreComplete();
            }
        });

        //圈子点赞与取消
        recycleQuanAdapter.setCheckItem(new RecycleQuanAdapter.CheckItem() {

            @Override
            public void setItemCheck(View view, int position) {
                map.put("circleId", circleList.get(position).getId());
                if (circleList.get(position).getWhetherGreat() == 2) {
                    circleList.get(position).setWhetherGreat(1);
                    circleList.get(position).setGreatNum(circleList.get(position).getGreatNum() + 1);
                    recycleQuanAdapter.notifyDataSetChanged();
                    headmaps.put("userId", userid);
                    headmaps.put("sessionId", sess);
                    presenterImpls.postHeader(Contacts.ADD_CICLE_GREAT_URL, headmaps, map, EnrollBean.class);
                } else {
                    circleList.get(position).setWhetherGreat(2);
                    circleList.get(position).setGreatNum(circleList.get(position).getGreatNum() - 1);
                    recycleQuanAdapter.notifyDataSetChanged();
                   // map.put("circleId", circleList.get(position).getId());
                    presenterImpls.delRequest(Contacts.CACEL_CIRCLE_GREAT_URL, headmaps, map, EnrollBean.class);
                }
            }
        });
    }

    @Override
    public void setSuccess(Object datas) {
        if (datas instanceof CircleBean) {
            CircleBean circleBean = (CircleBean) datas;
            circleList.addAll(circleBean.getResult());
            recycleQuanAdapter.setCircleList(circleList);
            if (circleBean.getStatus().equals("0000")) {
                Log.e("zzz", "setSuccess: " + circleBean.getMessage());
            }
        } else if (datas instanceof EnrollBean) {
            EnrollBean bean = (EnrollBean) datas;
            Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
            recycleQuanAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void setError(Object errors) {
        String d = (String) errors;
        Log.e("zzz", "setSuccess:fsfsdfsdf " + d);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
