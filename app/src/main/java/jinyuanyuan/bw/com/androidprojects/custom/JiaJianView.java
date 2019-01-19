package jinyuanyuan.bw.com.androidprojects.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import jinyuanyuan.bw.com.androidprojects.R;

/*
 *Author:Ahri_Love
 *Date:2019/1/9
 */public class JiaJianView extends LinearLayout implements View.OnClickListener {

    private TextView add;
    private TextView del;
    private TextView nums;
    private int mCount;

    public JiaJianView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.jia_jian_item, this);
        //查找控件
        initView();
    }

    //给控件赋值，数据中已有的
    public void setNums(int num) {
        this.mCount = num;
        nums.setText(num + "");
    }

    private void initView() {
        add = findViewById(R.id.jia);
        add.setOnClickListener(this);
        del = findViewById(R.id.jian);
        del.setOnClickListener(this);
        nums = findViewById(R.id.shu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jia:
                mCount++;
                nums.setText(mCount + "");
                if (onCountChange != null) {
                    onCountChange.setCount(mCount);
                }
                break;
            case R.id.jian:
                if (mCount > 0) {
                    mCount--;
                    nums.setText(mCount + "");
                    if (onCountChange != null) {
                        onCountChange.setCount(mCount);
                    }
                }
                break;
        }
    }

    //接口回调
    public interface OnCountChange {
        void setCount(int count);
    }

    private OnCountChange onCountChange;

    public void setOnCountChange(OnCountChange onCountChange) {
        this.onCountChange = onCountChange;
    }
}
