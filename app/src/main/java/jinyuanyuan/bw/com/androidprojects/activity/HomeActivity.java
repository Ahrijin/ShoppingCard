package jinyuanyuan.bw.com.androidprojects.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.fragment.CircleFragment;
import jinyuanyuan.bw.com.androidprojects.fragment.HomeFragment;
import jinyuanyuan.bw.com.androidprojects.fragment.ListFragment;
import jinyuanyuan.bw.com.androidprojects.fragment.MyFragment;
import jinyuanyuan.bw.com.androidprojects.fragment.ShoppingCardFragment;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.Radio_Group)
    android.widget.RadioGroup RadioGroup;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_layout,new HomeFragment()).commit();
    }

    //点击事件
    @OnClick({R.id.Radio_Home,R.id.Radio_Circle,R.id.Radio_Shopping_Card,R.id.Radio_List,R.id.Radio_My})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.Radio_Home:
                manager.beginTransaction().replace(R.id.frame_layout,new HomeFragment()).commit();
                break;
            case R.id.Radio_Circle:
                manager.beginTransaction().replace(R.id.frame_layout,new CircleFragment()).commit();
                break;
            case R.id.Radio_Shopping_Card:
                manager.beginTransaction().replace(R.id.frame_layout,new ShoppingCardFragment()).commit();
                break;
            case R.id.Radio_List:
                manager.beginTransaction().replace(R.id.frame_layout,new ListFragment()).commit();
                break;
            case R.id.Radio_My:
                manager.beginTransaction().replace(R.id.frame_layout,new MyFragment()).commit();
                break;
        }
    }
}
