package pager;

import android.content.Context;
import android.view.View;

public abstract class MenuDetailBasePager {



    public final Context context;
    //代表各个详情页面的视图
    public View rootview;

    public MenuDetailBasePager(Context context){
        this.context=context;
        rootview=initView();
    }

    //让每个详情页面实现不同的效果
    public abstract View initView();

    //绑定数据,联网请求数据
    public void initData(){

    }
}
