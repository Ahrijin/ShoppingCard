package jinyuanyuan.bw.com.androidprojects.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jinyuanyuan.bw.com.androidprojects.activity.DetailsActivity;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.bean.RecycleBean;

public class RecycleHotAdapter extends RecyclerView.Adapter<RecycleHotAdapter.ViewHolder> {
    private List<RecycleBean.ResultBean.RxxpBean.CommodityListBean> rxxpList;
    private Context context;

    public RecycleHotAdapter(List<RecycleBean.ResultBean.RxxpBean.CommodityListBean> rxxpList, Context context) {
        this.rxxpList = rxxpList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleHotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_hot_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHotAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.myhome_home_item_card.setRadius(20);
        viewHolder.myhome_home_item_card.setCardElevation(8);
        viewHolder.myhome_home_item_card.setContentPadding(5,5,5,5);

        String masterPic = rxxpList.get(i).getMasterPic();
        Glide.with(context).load(masterPic).into(viewHolder.imgs);
        viewHolder.prices.setText("￥"+rxxpList.get(i).getPrice());

        final String commodityName = rxxpList.get(i).getCommodityName();
        String s = commodityName.substring(0,3);
        viewHolder.titles.setText(s+"...");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",rxxpList.get(i).getCommodityId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rxxpList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgs;
        private TextView titles;
        private TextView prices;
        private CardView myhome_home_item_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgs = itemView.findViewById(R.id.hot_imgs);
            titles = itemView.findViewById(R.id.hot_title);
            prices = itemView.findViewById(R.id.hot_price);
            myhome_home_item_card = itemView.findViewById(R.id.myhome_home_item_card);
        }
    }





}
