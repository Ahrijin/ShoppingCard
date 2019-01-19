package jinyuanyuan.bw.com.androidprojects.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.adapter.FootAdapter;
import jinyuanyuan.bw.com.androidprojects.bean.CircleBean;
import jinyuanyuan.bw.com.androidprojects.bean.FootBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class MyFootActivity extends AppCompatActivity implements IView {

    @BindView(R.id.xrecy_view)
    XRecyclerView xrecyView;
    private PresenterImpls presenterImpls;
    private Map<String, Object> map = new HashMap<>();
    private Map<String, Object> maps = new HashMap<>();
    private Map<String, Object> headmaps = new HashMap<>();
    private SharedPreferences jyy;
    private FootAdapter footAdapter;
    private int index = 1;
    private List<FootBean.ResultBean> footLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_foot);
        ButterKnife.bind(this);

        //取值SharedPreferences中保存的sessionId和userId
        jyy = getSharedPreferences("jyy", MODE_PRIVATE);
        final String sess = jyy.getString("sess", null);
        final int userid = jyy.getInt("userid", 0);

        map.put("page",1);
        map.put("count",6);
        headmaps.put("userId",userid);
        headmaps.put("sessionId",sess);

        footAdapter = new FootAdapter(footLists,MyFootActivity.this);
        xrecyView.setAdapter(footAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        xrecyView.setLayoutManager(staggeredGridLayoutManager);

        presenterImpls = new PresenterImpls(this);
        presenterImpls.getHeader(Contacts.BROWSELIST_URL,headmaps,map,FootBean.class);

        xrecyView.setPullRefreshEnabled(true);
        xrecyView.setLoadingMoreEnabled(true);
        xrecyView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                footLists.clear();
                index = 1;
                maps.put("page",1);
                maps.put("count",5);
                presenterImpls.getHeader(Contacts.BROWSELIST_URL,headmaps,maps,FootBean.class);
                footAdapter.notifyDataSetChanged();
                xrecyView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                maps.put("page", (++index) + "");
                presenterImpls.getHeader(Contacts.BROWSELIST_URL, headmaps, maps, FootBean.class);
                footAdapter.notifyDataSetChanged();
                xrecyView.loadMoreComplete();
            }
        });
    }

    @Override
    public void setSuccess(Object datas) {
        FootBean footBean = (FootBean) datas;
        footLists.addAll(footBean.getResult());
        footAdapter.notifyDataSetChanged();
    }

    @Override
    public void setError(Object errors) {

    }
}
