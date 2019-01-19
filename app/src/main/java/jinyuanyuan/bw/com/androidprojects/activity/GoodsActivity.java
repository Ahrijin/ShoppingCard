package jinyuanyuan.bw.com.androidprojects.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

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
import jinyuanyuan.bw.com.androidprojects.adapter.SearchAdapter;
import jinyuanyuan.bw.com.androidprojects.bean.SearchBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class GoodsActivity extends AppCompatActivity implements IView {

    @BindView(R.id.search_recy)
    XRecyclerView searchRecy;
  /*  @BindView(R.id.goods_menu)
    ImageView goodsMenu;*/
    private int index = 1;
    private String dataes;
    private int pages = 1;
    private PresenterImpls presenterImpls;
    private SearchAdapter searchAdapter;
    private List<SearchBean.ResultBean> searchList = new ArrayList<>();
    Map<String, Object> headmap = new HashMap<>();
    Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);

        //eventbus注册
        EventBus.getDefault().register(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(GoodsActivity.this, 2);
        searchRecy.setLayoutManager(gridLayoutManager);

        searchAdapter = new SearchAdapter(searchList, GoodsActivity.this);
        searchRecy.setAdapter(searchAdapter);

        map.put("keyword", dataes);
        map.put("page", (++pages) + "");
        map.put("count", 5 + "");
        presenterImpls = new PresenterImpls(this);
        presenterImpls.getHeader(Contacts.FIND_COMMODITY_BY_KEYWORD_URL, headmap, map, SearchBean.class);

        //
        searchRecy.setPullRefreshEnabled(true);
        searchRecy.setLoadingMoreEnabled(true);
        searchRecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override//上拉加载
            public void onRefresh() {
                searchList.clear();
                index = 1;
                presenterImpls.getHeader(Contacts.FIND_COMMODITY_BY_KEYWORD_URL, headmap, map, SearchBean.class);
                searchAdapter.notifyDataSetChanged();
                searchRecy.refreshComplete();
            }

            @Override//加载更多
            public void onLoadMore() {
                map.put("page", (++index) + "");
                presenterImpls.getHeader(Contacts.FIND_COMMODITY_BY_KEYWORD_URL, headmap, map, SearchBean.class);
                searchAdapter.notifyDataSetChanged();
                searchRecy.loadMoreComplete();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getData(String data) {
        dataes = data;
        Toast.makeText(GoodsActivity.this, dataes + "", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setSuccess(Object datas) {
        Log.d("zzz", "成功了");
        SearchBean searchBean = (SearchBean) datas;
        searchList.addAll(searchBean.getResult());
        Log.e("zzz", searchBean.getMessage() + "++++");
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void setError(Object errors) {
        Log.d("zzz", "失败了");
    }

}
