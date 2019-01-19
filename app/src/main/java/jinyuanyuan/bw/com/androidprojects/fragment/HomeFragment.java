package jinyuanyuan.bw.com.androidprojects.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jinyuanyuan.bw.com.androidprojects.R;
import jinyuanyuan.bw.com.androidprojects.activity.SeacherActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.home_imags)
    ImageView homeImags;
    @BindView(R.id.frames)
    FrameLayout frames;
    @BindView(R.id.menu_img)
    ImageView menuImg;
    private View v;
    private FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, v);

        manager = getChildFragmentManager();
        manager.beginTransaction().replace(R.id.frames, new ContentFragment()).commit();

        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.home_imags,R.id.menu_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_imags:
                startActivity(new Intent(getActivity(), SeacherActivity.class));
                break;
            case R.id.menu_img:

                break;
        }
    }

}
