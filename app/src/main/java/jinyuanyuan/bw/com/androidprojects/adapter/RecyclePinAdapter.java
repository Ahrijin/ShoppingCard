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

/*
 *
 *aaa
 *
 */public class RecyclePinAdapter extends RecyclerView.Adapter<RecyclePinAdapter.ViewHolder> {
    private List<RecycleBean.ResultBean.PzshBean.CommodityListBeanX> pzshList;
    private Context context;

    public RecyclePinAdapter(List<RecycleBean.ResultBean.PzshBean.CommodityListBeanX> pzshList, Context context) {
        this.pzshList = pzshList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclePinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_pin_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclePinAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.myhome_home_item_card.setRadius(20);
        viewHolder.myhome_home_item_card.setCardElevation(8);
        viewHolder.myhome_home_item_card.setContentPadding(10,10,10,10);

        String masterPic = pzshList.get(i).getMasterPic();
        Glide.with(context).load(masterPic).into(viewHolder.imgs);
        viewHolder.title.setText(pzshList.get(i).getCommodityName());
        viewHolder.price.setText("￥"+pzshList.get(i).getPrice());

        String commodityName = pzshList.get(i).getCommodityName();
        String s = commodityName.substring(0,3);
        viewHolder.title.setText(s+"...");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",pzshList.get(i).getCommodityId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pzshList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgs;
        private TextView title;
        private TextView price;
        private CardView myhome_home_item_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgs = itemView.findViewById(R.id.pin_imgs);
            price = itemView.findViewById(R.id.pin_price);
            title = itemView.findViewById(R.id.pin_title);
            myhome_home_item_card = itemView.findViewById(R.id.myhome_home_item_card);
        }
    }
}
