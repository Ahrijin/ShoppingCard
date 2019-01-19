package jinyuanyuan.bw.com.androidprojects.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.custom.MyFloatLayout;
import jinyuanyuan.bw.com.androidprojects.custom.MyHeaderView;
import jinyuanyuan.bw.com.androidprojects.sqlite.MyDao;

public class SeacherActivity extends AppCompatActivity implements View.OnClickListener {
    private String[] data = {"外套", "上衣", "半身裙", "衬衫", "豆豆鞋", "高跟鞋", "水杯", "辣条", "电脑", "手机", "充电宝"};
    private MyHeaderView myheader_view;
    private TextView delete;
    private MyFloatLayout my_float_history;
    private MyFloatLayout my_float;
    //热门搜索
    private ArrayList<String> mList = new ArrayList<>();
    private ArrayList<String> mHistory = new ArrayList<>();
    private MyDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seacher);
        dao = new MyDao(SeacherActivity.this);
        mHistory = dao.selectName();

        initData();
        initView();

        if(!mHistory.isEmpty()){
            my_float_history.setData(mHistory);
        }

    }
    private void initData() {
        for (int i = 0; i <data.length ; i++) {
            mList.add(data[i]);
        }
    }

    private void initView() {
        myheader_view = (MyHeaderView) findViewById(R.id.myheader_view);
        myheader_view.getAddTexts().setOnClickListener(this);
        delete = (TextView) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        my_float_history = (MyFloatLayout) findViewById(R.id.my_float_history);
        my_float = (MyFloatLayout) findViewById(R.id.my_float);
        my_float.setData(mList);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //添加
            case R.id.seacher_imags:
                //获取输入框的值
                String name = myheader_view.getSearchText().trim();
                //判断name是否为空
                if(name.equals("")){
                    Toast.makeText(SeacherActivity.this,"不能为空",Toast.LENGTH_LONG).show();
                }else {
                    //添加到数据库
                    dao.insert(myheader_view.getSearchText().trim());
                    //清空
                    my_float_history.removeAllViews();
                    //添加到搜索历史集合中mHistory
                    mHistory.add(name);
                    //调用my_float_history中的方法展示数据
                    my_float_history.setData(mHistory);

                    EventBus.getDefault().postSticky(name);
                    startActivity(new Intent(SeacherActivity.this,GoodsActivity.class));
                }
                break;
            //删除
            case R.id.delete:
                //数据库删除
                dao.delete();
                //清空所有数据
                my_float_history.removeAllViews();
                mHistory.clear();//清空历史记录集合
                break;
        }
    }
}
