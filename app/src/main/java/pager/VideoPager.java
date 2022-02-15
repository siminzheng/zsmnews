package pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import pager.DetailPagers.PhotoDetailPager;
import pager.DetailPagers.VideoDetailPager;

public class VideoPager extends BasePager {
    public VideoPager(Activity context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_basepager.setText("视频中心");

        TextView textView=new TextView(context);
        textView.setText("视频中心");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(40);
        fl_basepager.addView(textView);

        //调用NewsDetailPager，并且初始化,加入contentfragment的viewpager中
        fl_basepager.removeAllViews();
        VideoDetailPager videoDetailPager = new VideoDetailPager(context);
        videoDetailPager.initData();
        View rootview=videoDetailPager.rootview;
        fl_basepager.addView(rootview);
    }
}
