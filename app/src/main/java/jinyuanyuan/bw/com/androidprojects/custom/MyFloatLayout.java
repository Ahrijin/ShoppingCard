package jinyuanyuan.bw.com.androidprojects.custom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/*
 *Author:Ahri_Love
 *Date:2019/1/8
 */public class MyFloatLayout extends LinearLayout {
    private int mScreenWidth;
    private String mColor;

    public MyFloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取屏幕宽度
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mScreenWidth = metrics.widthPixels;
        setOrientation(VERTICAL);

       /* TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GroupDemoView);
        if (typedArray != null) {
            mColor = (String) typedArray.getText(R.styleable.GroupDemoView_textColor);
            typedArray.recycle();//释放资源
        }*/
    }

    public void setData(ArrayList<String> data) {
        LinearLayout linearLayout = getLin();
        for (int i = 0; i < data.size(); i++) {
            final String temp = data.get(i);
            int numWidth = 0;
            //获得子控件数量
            int childCount = linearLayout.getChildCount();
            for (int j = 0; j < childCount; j++) {
                //获得每行linelayout的所有子控件TextView
                TextView tv = (TextView) linearLayout.getChildAt(j);
                //获取TextView的leftmargin并测量tv的宽高
                LayoutParams params = (LayoutParams) tv.getLayoutParams();
                int leftMargin = params.leftMargin;
                tv.measure(getMeasuredWidth(), getMeasuredHeight());
                numWidth += tv.getMeasuredWidth() + leftMargin + tv.getPaddingRight() + tv.getPaddingLeft();
            }
            //得到TextView
            TextView textView = getText();
            //设置属性
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 15;
            params.topMargin = 10;
            textView.setLayoutParams(params);
            //设置值
            textView.setText(temp);
            //再次测量宽高
            textView.measure(textView.getMeasuredWidth(), textView.getMeasuredHeight());
            //得到文本的最终大小
            int dataTextWidth = textView.getMeasuredWidth() + textView.getPaddingLeft() + textView.getPaddingRight();
            //判断 一行所有子控件的宽度是否大于屏幕的宽度
            if (mScreenWidth >= numWidth + dataTextWidth) {
                linearLayout.addView(textView);//小于的话，再次添加一个子控件TextView
            } else {
                //换行
                linearLayout = getLin();
                //重新添加view
                linearLayout.addView(textView);
            }

        }
    }

    //初始化子LinearLayout
    public LinearLayout getLin() {
        //初始化
        LinearLayout linearLayout = new LinearLayout(getContext());
        //设置组件大小
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        this.addView(linearLayout);//重新添加view
        return linearLayout;
    }

    public void removeChildView() {
        removeAllViews();//移除所有子控件
    }

    //初始化TextView
    public TextView getText() {
        TextView tv = new TextView(getContext());
        //要显示的TextView的颜色，大小
        tv.setTextSize(20);
        tv.setTextColor(Color.BLACK);//设置文字颜色
        tv.setPadding(10, 3, 10, 3);
        return tv;
    }
}
