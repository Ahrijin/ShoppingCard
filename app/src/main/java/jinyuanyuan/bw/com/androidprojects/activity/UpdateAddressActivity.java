package jinyuanyuan.bw.com.androidprojects.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPickerView;
import com.xw.repo.XEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.bean.EnrollBean;
import jinyuanyuan.bw.com.androidprojects.presenter.PresenterImpls;
import jinyuanyuan.bw.com.androidprojects.utils.Contacts;
import jinyuanyuan.bw.com.androidprojects.view.IView;

public class UpdateAddressActivity extends AppCompatActivity implements IView {

    @BindView(R.id.update_person)
    XEditText updatePerson;
    @BindView(R.id.update_telephone)
    XEditText updateTelephone;
    @BindView(R.id.imgs_next)
    ImageView imgsNext;
    @BindView(R.id.update_local)//所在地区
            XEditText updateLocal;
    @BindView(R.id.update_address)//详细地址
            XEditText updateAddress;
    @BindView(R.id.update_code)
    XEditText updateCode;
    @BindView(R.id.saves)
    Button saves;
    private PresenterImpls presenterImpls;
    private Map<String, Object> headmap = new HashMap<>();
    private Map<String, Object> maps = new HashMap<>();
    private SharedPreferences jyy;
    private String name;
    private String phone;
    private String address;
    private String code;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        ButterKnife.bind(this);

        //获取userId和sessionId
        jyy = getSharedPreferences("jyy", MODE_PRIVATE);
        final String sess = jyy.getString("sess", null);
        final int userid = jyy.getInt("userid", 0);
        headmap.put("userId", userid);
        headmap.put("sessionId", sess);
        presenterImpls = new PresenterImpls(this);

        //取值
        intent = getIntent();
        name = intent.getStringExtra("name");//收件人
        phone = intent.getStringExtra("phone");//电话
        code = intent.getStringExtra("code");  //邮编
        address = intent.getStringExtra("address");//地址
        updatePerson.setText(name);
        updateTelephone.setText(phone);
        updateCode.setText(code);

    }

    @OnClick({R.id.imgs_next, R.id.saves})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //地址
            case R.id.imgs_next:
                CityPickerView cityPickerView = new CityPickerView(UpdateAddressActivity.this);
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        //城市
                        String city = citySelected[1];
                        //区县
                        String district = citySelected[2];
                        //邮编
                        String code = citySelected[3];
                        updateLocal.setText(province + "-" + city + "-" + district);
                        updateCode.setText(code + "");
                    }
                });
                cityPickerView.show();
                break;
            //修改保存
            case R.id.saves:
                String emails = updatePerson.getTrimmedString().toString().trim();//收件人
                String local = updateAddress.getTrimmedString().toString().trim();//所在地区
                String address = updateAddress.getTrimmedString().toString().trim();//详细地址
                String[] split = local.split("-");
                for (int i = 0; i < split.length; i++) {
                    local += split[i];
                }
                String telephone = updateTelephone.getTrimmedString().toString().trim();//手机号码
                String code = updateCode.getTrimmedString().toString().trim();//邮政编码
                if (emails.isEmpty() || address.isEmpty() || local.isEmpty() || telephone.isEmpty() || code.isEmpty()) {
                    Toast.makeText(this, "请将信息填写完整！", Toast.LENGTH_SHORT).show();
                } else {
                    maps.put("id", intent.getIntExtra("id",0));
                    maps.put("realName", emails);
                    maps.put("phone", telephone);
                    maps.put("address", local + address);
                    maps.put("zipCode", code);
                    presenterImpls.putRequest(Contacts.CHANGE_RECEIVE_ADDRESS, headmap, maps, EnrollBean.class);
                }
                break;
        }
    }

    @Override
    public void setSuccess(Object datas) {
        EnrollBean enrollBean = (EnrollBean) datas;
        if (enrollBean.getMessage().equals("修改成功")) {
            Toast.makeText(this, enrollBean.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UpdateAddressActivity.this, MyAddressActivity.class));
        }
    }

    @Override
    public void setError(Object errors) {

    }
}
