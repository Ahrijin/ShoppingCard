package jinyuanyuan.bw.com.androidprojects.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

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
import jinyuanyuan.bw.com.androidprojects.bean.DetailsXiangBean;
import jinyuanyuan.bw.com.androidprojects.bean.EnrollBean;
import jinyuanyuan.bw.com.androidprojects.bean.SeleteDataBean;
import jinyuanyuan.bw.com.androidprojects.fragment.ShoppingCardFragment;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class DetailsActivity extends AppCompatActivity implements IView {
    @BindView(R.id.imgs_back)
    ImageView imgsBack;
    @BindView(R.id.detail_price)
    TextView detailPrice;
    @BindView(R.id.yishou)
    TextView yishou;
    @BindView(R.id.detail_names)
    TextView detailNames;
    @BindView(R.id.view_page)
    XBanner viewPage;
    @BindView(R.id.rel_layout)
    RelativeLayout relLayout;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.gou_card)
    ImageView gouCard;
    @BindView(R.id.buys)
    ImageView buys;
    private Map<String, Object> headmap = new HashMap<>();
    private Map<String, Object> map = new HashMap<>();
    private Map<String, Object> tong = new HashMap<>();
    private Map<String, Object> yuan = new HashMap<>();
    private SharedPreferences jyy;
    private PresenterImpls presenterImpls;
    private List<String> mLists = new ArrayList<>();
    private List<SeleteDataBean.ResultBean> mResults = new ArrayList<>();
    private int commodityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        //获取userId和sessionId
        jyy = getSharedPreferences("jyy", MODE_PRIVATE);
        final String sess = jyy.getString("sess", null);
        final int userid = jyy.getInt("userid", 0);
        headmap.put("userId", userid);
        headmap.put("sessionId", sess);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        map.put("commodityId", id);

        presenterImpls = new PresenterImpls(this);
        //商品详情
        presenterImpls.getHeader(Contacts.FIND_COMMODITY_DETAILSBYID, headmap, map, DetailsXiangBean.class);

    }

    @OnClick({R.id.gou_card, R.id.imgs_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //购物车
            case R.id.gou_card:
                tong.put("data", "[{commodityId:" + commodityId + ",count:" + 1 + "}]");
                //查询购物车
                presenterImpls.getHeader(Contacts.FIND_SHOPINGCART, headmap, yuan, SeleteDataBean.class);
                break;
            //买
            case R.id.imgs_back:
                finish();
                break;
        }
    }

    @Override
    public void setSuccess(Object datas) {
        if (datas instanceof SeleteDataBean) {
            SeleteDataBean seleteDataBean = (SeleteDataBean) datas;
            mResults.addAll(seleteDataBean.getResult());
            Log.e("wee", "setSuccess: "+mResults.size() );
            if (mResults.size() != 0) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < mResults.size(); i++) {
                    JSONObject jsonObject = null;
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("commodityId", mResults.get(i).getCommodityId());
                        jsonObject.put("count", 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }
                try {
                    JSONObject jsonObject = null;
                    jsonObject = new JSONObject();
                    jsonObject.put("commodityId", commodityId);
                    jsonObject.put("count", 1);
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tong.put("data", jsonArray.toString());//同步
                presenterImpls.putRequest(Contacts.SYNC_SHOPPING_CART, headmap, tong, SeleteDataBean.class);
            } else {
                presenterImpls.putRequest(Contacts.SYNC_SHOPPING_CART, headmap, tong, SeleteDataBean.class);
            }
        }

        if (datas instanceof EnrollBean) {
            EnrollBean enrollBean = (EnrollBean) datas;
            if (enrollBean.getStatus().equals("0000")) {
                Toast.makeText(DetailsActivity.this, "同步购物车成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailsActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        }

        if (datas instanceof DetailsXiangBean) {
            DetailsXiangBean detailsXiangBean = (DetailsXiangBean) datas;
            DetailsXiangBean.ResultBean results = detailsXiangBean.getResult();
            //商品id
            commodityId = results.getCommodityId();
            Log.d("sss", "setSuccess: "+commodityId);
            //赋值
            detailPrice.setText("￥" + results.getPrice());
            detailNames.setText(results.getCategoryName() + "");
            yishou.setText("已售" + results.getSaleNum() + "件");
            //加载轮播图片
            String[] split = results.getPicture().split("\\,");
            for (int i = 0; i < split.length; i++) {
                mLists.add(split[i]);
            }
            if (!mLists.isEmpty()) {
                viewPage.setData(mLists, null);
                viewPage.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(DetailsActivity.this).load(mLists.get(position)).into((ImageView) view);
                        Log.e("zzz", mLists.get(position));
                    }
                    //横向移动

                });
                viewPage.setPageTransformer(Transformer.Default);

            }
            //webview
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);//支持JS
            String js = "<script type=\"text/javascript\">" +
                    "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                    "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                    "imgs[i].style.width = '100%';" +  // 宽度改为100%
                    "imgs[i].style.height = 'auto';" +
                    "}" +
                    "</script>";
            webView.loadDataWithBaseURL(null, detailsXiangBean.getResult().getDetails() + js, "text/html", "utf-8", null);
        }


    }

    @Override
    public void setError(Object errors) {

    }


}


