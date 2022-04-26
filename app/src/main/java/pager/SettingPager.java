package pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import pager.DetailPagers.SetttingDetailPager;

public class SettingPager extends BasePager {


    public SettingPager(Activity context) {
        super(context);
    }


    @Override
    public void initData() {
        super.initData();
        tv_basepager.setText("设置中心");

        TextView textView=new TextView(context);
        textView.setText("设置中心");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(40);
        fl_basepager.addView(textView);

        //清楚原来默认的view，给设置页面加上view
        fl_basepager.removeAllViews();
        SetttingDetailPager settingDetailPager=new SetttingDetailPager(context);
        settingDetailPager.initData();
        View rootview=settingDetailPager.rootview;
        fl_basepager.addView(rootview);
    }
}
