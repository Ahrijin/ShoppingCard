package jinyuanyuan.bw.com.androidprojects.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.bean.MyAddressBean;

/*
 *Author:Ahri_Love
 *Date:2019/1/9
 */
public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder> {
    private List<MyAddressBean.ResultBean> addressList;
    private Context context;

    public MyAddressAdapter(List<MyAddressBean.ResultBean> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_address_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAddressAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.address.setText(addressList.get(i).getAddress());
        viewHolder.addressPhone.setText(addressList.get(i).getPhone());
        viewHolder.addressName.setText(addressList.get(i).getRealName());

        if(addressList.get(i).getWhetherDefault() == 1){
            viewHolder.addressMo.setChecked(true);
        }else {
            viewHolder.addressMo.setChecked(false);
        }

        //设置默认地址
        viewHolder.addressMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonListener!=null){
                    buttonListener.radioItemClick(v,i);
                }
            }
        });

        //修改地址
        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonListener!=null){
                    buttonListener.updateClick(v,i);
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
        private TextView btnDel;
        private TextView btnUpdate;
        private CheckBox addressMo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addressName = itemView.findViewById(R.id.address_name);//收件人姓名
            addressPhone = itemView.findViewById(R.id.address_phone);//电话号码
            addressMo = itemView.findViewById(R.id.address_mo);
            address = itemView.findViewById(R.id.address);//收获地址
            btnDel = itemView.findViewById(R.id.btn_del);
            btnUpdate = itemView.findViewById(R.id.btn_update);
        }
    }

    public interface ButtonListener{
        void radioItemClick(View view,int position);
        void updateClick(View view,int position);
    }
    private ButtonListener buttonListener;
    public void setBtnListener(ButtonListener buttonListener){
        this.buttonListener = buttonListener;
    }
}
