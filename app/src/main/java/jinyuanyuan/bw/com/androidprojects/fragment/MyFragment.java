package jinyuanyuan.bw.com.androidprojects.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.activity.MyAddressActivity;
import jinyuanyuan.bw.com.androidprojects.activity.MyFootActivity;
import jinyuanyuan.bw.com.androidprojects.activity.MySourceActivity;
import jinyuanyuan.bw.com.androidprojects.activity.MyWalletActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    @BindView(R.id.person_souce)
    TextView personSouce;
    @BindView(R.id.my_circle)
    TextView myCircle;
    @BindView(R.id.my_foot)
    TextView myFoot;
    @BindView(R.id.my_wallet)
    TextView myWallet;
    @BindView(R.id.my_address)
    TextView myAddress;
    Unbinder unbinder;
    private View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.person_souce, R.id.my_circle, R.id.my_foot, R.id.my_wallet, R.id.my_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.person_souce:
                startActivity(new Intent(getActivity(), MySourceActivity.class));
                break;
            case R.id.my_circle:
                //    startActivity(new Intent(getActivity(),MyCircleActivity.class));
                break;
            case R.id.my_foot:
                startActivity(new Intent(getActivity(), MyFootActivity.class));
                break;
            case R.id.my_wallet:
                startActivity(new Intent(getActivity(), MyWalletActivity.class));
                break;
            case R.id.my_address:
                startActivity(new Intent(getActivity(), MyAddressActivity.class));
                break;

        }
    }
}
