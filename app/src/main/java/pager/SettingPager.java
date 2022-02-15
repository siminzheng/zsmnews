package pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

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
    }
}
