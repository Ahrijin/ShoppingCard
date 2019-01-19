package jinyuanyuan.bw.com.androidprojects.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.bean.MyAddressBean;

/*
 *Author:Ahri_Love
 *Date:2019/1/15
 */public class PopAdapter extends RecyclerView.Adapter<PopAdapter.ViewHolder> {
    private List<MyAddressBean.ResultBean> addressList;
    private Context context;
    public PopAdapter(List<MyAddressBean.ResultBean> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
    }


    @NonNull
    @Override
    public PopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_item, viewGroup, false);
        PopAdapter.ViewHolder holder = new PopAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.address.setText(addressList.get(i).getAddress());
        viewHolder.addressPhone.setText(addressList.get(i).getPhone());
        viewHolder.addressName.setText(addressList.get(i).getRealName());
        viewHolder.addressSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popListener!=null){
                    popListener.getData(i);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView address;
        private TextView addressName;
        private TextView addressPhone;
        private TextView addressSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addressName = itemView.findViewById(R.id.pop_address_name);//收件人姓名
            addressPhone = itemView.findViewById(R.id.pop_address_phone);//电话号码
            address = itemView.findViewById(R.id.pop_address_address);//收获地址
            addressSelect = itemView.findViewById(R.id.pop_order_elect);//选择
        }
    }
    public interface PopListener{
        void getData(int position);
    }
    private  PopListener  popListener;
    public void setPopListener(PopListener  popListener){
        this.popListener = popListener;
    }

}
