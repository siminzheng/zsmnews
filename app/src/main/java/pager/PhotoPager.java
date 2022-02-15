package pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import pager.DetailPagers.NewsDetailPager;
import pager.DetailPagers.PhotoDetailPager;

public class PhotoPager extends BasePager {
    public PhotoPager(Activity context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_basepager.setText("图片中心");

        TextView textView=new TextView(context);
        textView.setText("图片中心");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(40);
        fl_basepager.addView(textView);

        //调用NewsDetailPager，并且初始化,加入contentfragment的viewpager中
        fl_basepager.removeAllViews();
        PhotoDetailPager photoDetailPager = new PhotoDetailPager(context);
        photoDetailPager.initData();
        View rootview=photoDetailPager.rootview;
        fl_basepager.addView(rootview);

    }

}
