package pager;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zsmnews.R;

public class BasePager {


    public final View root_view;
    public final Context context;

    public FrameLayout fl_basepager;
    public TextView tv_basepager;


    public BasePager(Context context){
        this.context=context;
        root_view=this.initView();
    }

    private View initView() {
        View view=View.inflate(context, R.layout.basepager_layout,null);
        fl_basepager = (FrameLayout) view.findViewById(R.id.fl_basepager);
        tv_basepager = (TextView) view.findViewById(R.id.tv_basepager);

        return view;
    }

    //初始化数据，当孩子需要初始化数据或者绑定数据，或者联网请求并绑定数据时，重写该方法
    public void initData(){


    }
}
