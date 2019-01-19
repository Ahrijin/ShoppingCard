package jinyuanyuan.bw.com.androidprojects.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.bean.FootBean;

/*
 *Author:Ahri_Love
 *Date:2019/1/15
 */public class FootAdapter extends RecyclerView.Adapter<FootAdapter.ViewHolder> {
    private List<FootBean.ResultBean> footLists;
    private Context context;

    public FootAdapter(List<FootBean.ResultBean> footLists, Context context) {
        this.footLists = footLists;
        this.context = context;
    }

    @NonNull
    @Override
    public FootAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.foot_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FootAdapter.ViewHolder viewHolder, int i) {
        //title
        final String commodityName = footLists.get(i).getCommodityName();
        String s = commodityName.substring(0,3);
        viewHolder.title.setText(s+"...");
        //price,nums
        viewHolder.price.setText("￥" + footLists.get(i).getPrice());
        viewHolder.nums.setText("已浏览" + footLists.get(i).getBrowseNum() + "次");
        //浏览时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = simpleDateFormat.format(footLists.get(i).getBrowseTime() );
        viewHolder.date.setText(format);
        //瀑布流
        FootBean.ResultBean resultBean = footLists.get(i);
        ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();
        Random random = new Random();
        int height = random.nextInt(300) + 300;
        params.height = height;
        viewHolder.itemView.setLayoutParams(params);
        Glide.with(context).load(resultBean.getMasterPic()).into(viewHolder.imgs);
    }

    @Override
    public int getItemCount() {
        return footLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgs;
        private TextView title;
        private TextView price;
        private TextView nums;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgs = itemView.findViewById(R.id.foot_imgs);
            title = itemView.findViewById(R.id.foot_title);
            price = itemView.findViewById(R.id.foot_price);
            nums = itemView.findViewById(R.id.foot_nums);
            date = itemView.findViewById(R.id.foot_data);
        }
    }
}
