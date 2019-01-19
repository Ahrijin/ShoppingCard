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

public class RecycleMoAdapter extends RecyclerView.Adapter<RecycleMoAdapter.ViewHolder>{
    private List<RecycleBean.ResultBean.MlssBean.CommodityListBeanXX> mlssList;
    private Context context;

    public RecycleMoAdapter(List<RecycleBean.ResultBean.MlssBean.CommodityListBeanXX> mlssList, Context context) {
        this.mlssList = mlssList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleMoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_mo_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleMoAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.myhome_home_item_card.setRadius(10);
        viewHolder.myhome_home_item_card.setCardElevation(5);
        viewHolder.myhome_home_item_card.setContentPadding(5,5,5,5);

        String masterPic = mlssList.get(i).getMasterPic();
        Glide.with(context).load(masterPic).into(viewHolder.image);
        viewHolder.title.setText(mlssList.get(i).getCommodityName());
        viewHolder.price.setText("￥"+mlssList.get(i).getPrice());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",mlssList.get(i).getCommodityId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlssList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView price;
        private CardView myhome_home_item_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.mo_imgs);
            title = itemView.findViewById(R.id.mo_title);
            price = itemView.findViewById(R.id.mo_price);
            myhome_home_item_card = itemView.findViewById(R.id.myhome_home_item_card);
        }
    }
}
