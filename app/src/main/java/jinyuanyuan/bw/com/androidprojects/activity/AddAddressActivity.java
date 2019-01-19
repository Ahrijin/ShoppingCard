package jinyuanyuan.bw.com.androidprojects.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class AddAddressActivity extends AppCompatActivity implements IView {

    @BindView(R.id.save)
    Button save;
    //收件人
    @BindView(R.id.xedit_email)
    XEditText xeditEmail;
    //手机号码
    @BindView(R.id.xedit_telephone)
    XEditText xeditTelephone;
    //所在地区
    @BindView(R.id.xedit_local)
    XEditText xeditLocal;
    //详细地址
    @BindView(R.id.xedit_address)
    XEditText xeditAddress;
    @BindView(R.id.xedit_code)
    XEditText xeditCode;
    @BindView(R.id.imgs_next)
    ImageView imgsNext;
    private PresenterImpls presenterImpls;
    private Map<String, Object> headmap = new HashMap<>();
    private Map<String, Object> maps = new HashMap<>();
    private SharedPreferences jyy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        //获取userId和sessionId
        jyy = getSharedPreferences("jyy", MODE_PRIVATE);
        final String sess = jyy.getString("sess", null);
        final int userid = jyy.getInt("userid", 0);
        headmap.put("userId", userid);
        headmap.put("sessionId", sess);

        presenterImpls = new PresenterImpls(this);


    }

    @OnClick({R.id.save,R.id.imgs_next})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.save:
                String emails = xeditEmail.getTrimmedString().toString().trim();//收件人
                String address = xeditAddress.getTrimmedString().toString().trim();//详细地址
                String local = xeditLocal.getTrimmedString().toString().trim();//所在地区
                String[] split = local.split("-");
                for (int i = 0; i < split.length; i++) {
                    local += split[i];
                }
                String telephone = xeditTelephone.getTrimmedString().toString().trim();//手机号码
                String code = xeditCode.getTrimmedString().toString().trim();//邮政编码
                if (emails.isEmpty() || address.isEmpty() || local.isEmpty() || telephone.isEmpty() || code.isEmpty()) {
                    Toast.makeText(this, "请将信息填写完整！", Toast.LENGTH_SHORT).show();
                } else {
                    maps.put("realName", emails);
                    maps.put("phone", telephone);
                    maps.put("address", local + address);
                    maps.put("zipCode", code);
                    presenterImpls.postHeader(Contacts.ADD_RECEIVEADDRESS, headmap, maps, EnrollBean.class);
                }
                break;
            case R.id.imgs_next:
                CityPickerView cityPickerView = new CityPickerView(AddAddressActivity.this);
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
                        xeditLocal.setText(province + "-" + city + "-" + district);
                        xeditCode.setText(code + "");
                    }
                });
                cityPickerView.show();
                break;
        }
    }

    @Override
    public void setSuccess(Object datas) {
        EnrollBean enrollBean = (EnrollBean) datas;
        if (enrollBean.getMessage().equals("添加成功")) {
            Toast.makeText(this, enrollBean.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddAddressActivity.this, MyAddressActivity.class));
        }
    }

    @Override
    public void setError(Object errors) {

    }


}
