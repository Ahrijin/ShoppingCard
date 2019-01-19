package jinyuanyuan.bw.com.androidprojects.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.List;

/*
 *Author:Ahri_Love
 *Date:2019/1/10
 */
public class DetailsAdapter extends PagerAdapter {

    private List<String> mList ;
    private Context context;

    public DetailsAdapter(List<String> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imgs = new ImageView(context);
        Glide.with(context).load(mList.get(position)).into(imgs);
        container.addView(imgs);
        return imgs;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
