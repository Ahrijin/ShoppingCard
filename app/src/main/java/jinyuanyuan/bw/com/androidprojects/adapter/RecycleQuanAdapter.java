package jinyuanyuan.bw.com.androidprojects.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.bean.CircleBean;

/*
 *作者：Ahri_Love
 */
public class RecycleQuanAdapter extends RecyclerView.Adapter<RecycleQuanAdapter.ViewHolder> {

    private List<CircleBean.ResultBean> circleList;
    private Context context;

    public RecycleQuanAdapter(List<CircleBean.ResultBean> circleList, Context context) {
        this.circleList = circleList;
        this.context = context;
    }

    public void setCircleList(List<CircleBean.ResultBean> circleList) {
        this.circleList = circleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_quan, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        //头像
        String headPic = circleList.get(i).getHeadPic();
        //圈子图片
        String image = circleList.get(i).getImage();

        Glide.with(context).load(headPic).into(viewHolder.imgs1);
        Glide.with(context).load(image).into(viewHolder.imgs2);
        viewHolder.title.setText(circleList.get(i).getContent());
        viewHolder.name.setText(circleList.get(i).getNickName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String format = simpleDateFormat.format(circleList.get(i).getCreateTime());
        viewHolder.dates.setText(format);
        //点赞数
        viewHolder.nums.setText(circleList.get(i).getGreatNum()+"");

        if(circleList.get(i).getWhetherGreat() == 1){
            Glide.with(context).load(R.mipmap.common_btn_prise_s).into(viewHolder.imgs3);
        }else {
            Glide.with(context).load(R.mipmap.common_btn_prise_n).into(viewHolder.imgs3);
        }
        viewHolder.imgs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkItem != null){
                    checkItem.setItemCheck(v,i);
                }else {
                    Log.e("zzz", "onClick: 为空");
                }
            }
        });
    }

    public boolean isAllChecked(int i){
        int whetherGreat = circleList.get(i).getWhetherGreat();
        Log.e("zzz", "isAllChecked: "+whetherGreat );
        if (whetherGreat == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return circleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgs1;
        private ImageView imgs2;
        private ImageView imgs3;
        private TextView name;
        private TextView title;
        private TextView dates;
        private TextView nums;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgs1 = itemView.findViewById(R.id.circle_imgs1);
            imgs2 = itemView.findViewById(R.id.circle_imgs2);
            imgs3 = itemView.findViewById(R.id.circle_imgs3);
            name = itemView.findViewById(R.id.circle_name);
            title = itemView.findViewById(R.id.circle_title);
            dates = itemView.findViewById(R.id.circle_date);
            nums = itemView.findViewById(R.id.circle_zan_num);
        }
    }

    //接口回调，点赞
    public interface CheckItem{
        void setItemCheck(View view,int position);
    }

    private CheckItem checkItem;

    public void setCheckItem(CheckItem checkItem) {
        this.checkItem = checkItem;
    }
}
