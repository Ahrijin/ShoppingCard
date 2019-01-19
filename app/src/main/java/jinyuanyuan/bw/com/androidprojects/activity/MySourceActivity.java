package jinyuanyuan.bw.com.androidprojects.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.bean.SelectNameByIdBean;
import jinyuanyuan.bw.com.androidprojects.bean.SeleteDataBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class MySourceActivity extends AppCompatActivity implements IView {

    private Map<String, Object> headmap = new HashMap<>();
    private Map<String, Object> map = new HashMap<>();
    private Map<String, Object> maps = new HashMap<>();
    private SharedPreferences jyy;
    private PresenterImpls presenterImpls;
    private String headPic;
    private String nickName;
    private String password;
    private PopupWindow pop;
    private ImageView topPic;
    private TextView niNames;
    private TextView myPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_source);
        ButterKnife.bind(this);
        initView();
        setListener();
        //获取userId和sessionId
        jyy = getSharedPreferences("jyy", MODE_PRIVATE);
        final String sess = jyy.getString("sess", null);
        final int userid = jyy.getInt("userid", 0);
        headmap.put("userId", userid);
        headmap.put("sessionId", sess);

        //请求数据
        presenterImpls = new PresenterImpls(this);
        presenterImpls.getHeader(Contacts.GET_USER_BY_ID, headmap, map, SelectNameByIdBean.class);


    }

    private void setListener() {
        topPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(MySourceActivity.this, R.layout.pop, null);
                pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                pop.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                TextView paizhao = (TextView) view.findViewById(R.id.paizhao);
                TextView xiangce = (TextView) view.findViewById(R.id.xiangce);
                TextView cancel = (TextView) view.findViewById(R.id.cancel);
                //拍照
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.addCategory("android.intent.category.DEFAULT");
                        startActivityForResult(intent, 1000);
                    }
                });
                //相册
                xiangce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 2000);
                    }
                });
                //取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                    }
                });
            }
        });

        //改变昵称
        niNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(MySourceActivity.this);//提示框
                final View view = factory.inflate(R.layout.alert, null);//这里必须是final的
                final EditText edit = (EditText) view.findViewById(R.id.editText);//获得输入框对象

                new AlertDialog.Builder(MySourceActivity.this)
                        .setTitle("请修改名称")//提示框标题
                        .setView(view)
                        .setPositiveButton("确定",//提示框的两个按钮
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //事件
                                        String trim = edit.getText().toString().trim();
                                        niNames.setText(trim + "");
                                        map.put("nickName",trim);
                                        presenterImpls.putRequest(Contacts.MODIFY_USER_NICK_URL, headmap, map, SelectNameByIdBean.class);
                                    }
                                }).setNegativeButton("取消", null).create().show();
            }
        });

        //改变密码
        myPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(MySourceActivity.this);//提示框
                final View view = factory.inflate(R.layout.alert, null);//这里必须是final的
                final EditText edit = (EditText) view.findViewById(R.id.editText);//获得输入框对象

                new AlertDialog.Builder(MySourceActivity.this)
                        .setTitle("请修改密码")//提示框标题
                        .setView(view)
                        .setPositiveButton("确定",//提示框的两个按钮
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //事件
                                        String trim = edit.getText().toString().trim();
                                        myPass.setText(trim + "");
                                        presenterImpls.putRequest(Contacts.MODIFY_USERPWD_URL, headmap, map, SelectNameByIdBean.class);
                                    }
                                }).setNegativeButton("取消", null).create().show();
            }
        });
    }

    private void initView() {
        topPic = findViewById(R.id.my_tou_pic);
        niNames = findViewById(R.id.my_ni_name);
        myPass = findViewById(R.id.my_passes);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Bitmap bitmap = data.getParcelableExtra("data");
            topPic.setImageBitmap(bitmap);
            pop.dismiss();

        }
        if (requestCode == 2000) {
            Uri uri = data.getData();
            topPic.setImageURI(uri);
            pop.dismiss();
        }
        if (requestCode == 3000) {
            Bitmap bitmap = data.getParcelableExtra("data");
            topPic.setImageBitmap(bitmap);
        }
    }

    //裁剪
    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        //裁减比例1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        //裁剪后图片大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDatection", false);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 3000);
    }

    @Override
    public void setSuccess(Object datas) {
        if(datas instanceof SelectNameByIdBean ){
            SelectNameByIdBean selectNameByIdBean = (SelectNameByIdBean) datas;
            Toast.makeText(this, selectNameByIdBean.getMessage(), Toast.LENGTH_SHORT).show();
            SelectNameByIdBean.ResultBean result = selectNameByIdBean.getResult();
            headPic = result.getHeadPic();
            nickName = result.getNickName();
            password = result.getPassword();
            //加载图片，设置昵称和密码
            Glide.with(this).load(headPic).into(topPic);
            niNames.setText(nickName);
            myPass.setText(password);
        }

    }

    @Override
    public void setError(Object errors) {

    }
}
