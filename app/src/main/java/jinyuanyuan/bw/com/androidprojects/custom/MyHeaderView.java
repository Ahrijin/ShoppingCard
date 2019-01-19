package jinyuanyuan.bw.com.androidprojects.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import jinyuanyuan.bw.com.androidprojects.R;

/*
 *Author:Ahri_Love
 *Date:2019/1/8
 */public class MyHeaderView extends LinearLayout {
    private ImageView seacherMenuImg;
    private EditText searchText;
    private TextView seacherImags;
    public MyHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.my_head_view, this);
        //搜索
        seacherImags = findViewById(R.id.seacher_imags);
        //输入框
        searchText = findViewById(R.id.seacher_search);
    }

    //搜索
    public String getSearchText(){
        return searchText.getText().toString().trim();
    }
    //添加
    public TextView getAddTexts(){
        return seacherImags;
    }
}
