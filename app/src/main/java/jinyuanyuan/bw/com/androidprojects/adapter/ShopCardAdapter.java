package jinyuanyuan.bw.com.androidprojects.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.bean.SeleteDataBean;
import jinyuanyuan.bw.com.androidprojects.custom.JiaJianView;

/*
 *Author:Ahri_Love
 *Date:2019/1/11
 */
public class ShopCardAdapter extends RecyclerView.Adapter<ShopCardAdapter.ViewHolder> {
    private List<SeleteDataBean.ResultBean> result;
    private Context context;

    public ShopCardAdapter(List<SeleteDataBean.ResultBean> result, Context context) {
        this.result = result;
        this.context = context;
    }


    @NonNull
    @Override
    public ShopCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_card_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopCardAdapter.ViewHolder viewHolder, final int i) {
        //图片
        Glide.with(context).load(result.get(i).getPic()).into(viewHolder.shopIcon);
        //标题
        viewHolder.shopTitle.setText(result.get(i).getCommodityName());
        //价格
        viewHolder.shopPrice.setText(result.get(i).getPrice() + "");
        //给CheckBox赋值
        viewHolder.shopCheck.setChecked(result.get(i).isChecked());
        //加减视图赋值
        viewHolder.shopJiaJian.setNums(result.get(i).getCount());

        viewHolder.shopCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapterCallback != null) {
                    adapterCallback.setChildCheck(i);
                }
            }
        });
        viewHolder.shopJiaJian.setOnCountChange(new JiaJianView.OnCountChange() {
            @Override
            public void setCount(int count) {
                if (adapterCallback != null) {
                    adapterCallback.setNum(i, count);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox shopCheck;
        private ImageView shopIcon;
        private TextView shopTitle;
        private TextView shopPrice;
        private JiaJianView shopJiaJian;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopCheck = itemView.findViewById(R.id.Shop_Check);
            shopIcon = itemView.findViewById(R.id.Shop_Icon);
            shopTitle = itemView.findViewById(R.id.shop_title);
            shopPrice = itemView.findViewById(R.id.shop_price);
            shopJiaJian = itemView.findViewById(R.id.shop_jia_jian);
        }
    }

    //是否单个选中
    public boolean isChecked(int i) {
        if (!result.get(i).isChecked()) {
            return false;
        }
        return true;
    }

    //将选中的状态set上
    public void setChildCheck(int i, boolean isCheckBox) {
        result.get(i).setChecked(isCheckBox);
    }

    //是否全部选中
    public boolean isCheckAll() {
        for (int i = 0; i < result.size(); i++) {
            if (!result.get(i).isChecked()) {
                return false;
            }
        }
        return true;
    }

    // //将选中的状态set上
    public void checkAll(boolean a) {
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setChecked(a);
        }
    }

    //总价格
    public float getShopPrice() {
        float price = 0;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).isChecked()) {
                price += result.get(i).getPrice() * result.get(i).getCount();
            }
        }
        return price;
    }

    //给商品数量进行赋值

    public void setGoodsNumber(int postion, int counts) {
        SeleteDataBean.ResultBean resultBean = result.get(postion);
        resultBean.setCount(counts);
    }

    //被选中的CheckBox，将id传过去
    public String getChecked() {
        String id = "";
        for (int i = 0; i < result.size(); i++) {
            boolean checked = result.get(i).isChecked();
            if (checked) {
                int commodityId = result.get(i).getCommodityId();
                result.get(i).getCommodityId();
                id += commodityId + " ";
            }
        }
        return id;
    }

    //判断复选框是否被选中
    public boolean isCheckedGoods() {
        boolean boo = true;
        for (int i = 0; i < result.size(); i++) {
            boolean checked = result.get(i).isChecked();
            if (checked) {
                return true;
            }
        }
        return false;
    }


    //接口
    public interface AdapterCallback {
        void setChildCheck(int i);

        void setNum(int index, int num);


    }

    private AdapterCallback adapterCallback;

    public void setCallback(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }
}
