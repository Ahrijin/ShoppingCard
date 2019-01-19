package jinyuanyuan.bw.com.androidprojects.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import jinyuanyuan.bw.com.androidprojects.bean.SearchBean;

/*
 *Author:Ahri_Love
 *Date:2019/1/5
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<SearchBean.ResultBean> searchList;
    private Context context;

    public SearchAdapter(List<SearchBean.ResultBean> searchList, Context context) {
        this.searchList = searchList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder viewHolder, final int i) {
        Glide.with(context).load(searchList.get(i).getMasterPic()).into(viewHolder.imgs);
       // viewHolder.title.setText(searchList.get(i).getCommodityName());
        viewHolder.price.setText(searchList.get(i).getPrice()+"");

        String commodityName = searchList.get(i).getCommodityName();
        String s = commodityName.substring(0,5);
        viewHolder.title.setText(s+"...");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",searchList.get(i).getCommodityId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgs;
        private TextView title;
        private TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgs = itemView.findViewById(R.id.search_imgs);
            title = itemView.findViewById(R.id.search_title);
            price = itemView.findViewById(R.id.search_price);
        }
    }
}
