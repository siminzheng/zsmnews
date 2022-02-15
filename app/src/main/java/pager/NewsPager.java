package pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import Bean.JiSuNewsPindao_Bean;
import pager.DetailPagers.NewsDetailPager;
import api.Myapi;
import pager.TabDetailPager.NewTabDetailPager;


public class NewsPager extends BasePager {
    //新闻频道
    private List<String> pindaolist;
    //private List<JiSuNews_bean.ResultEntity.ListEntity> datalist;
    //data是一个List
    //private List<JuheNews_Bean.ResultEntity.DataEntity> data;
    //private List<News_Bean.DataEntity> data;

    public NewsPager(Activity context) {
        super(context);
    }



    @Override
    public void initData() {
        super.initData();
        tv_basepager.setText("新闻中心");

        TextView textView=new TextView(context);
        textView.setText("新闻中心");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(40);
        fl_basepager.addView(textView);

        //String savejson= CacheUtils.getString(context, Myapi.NEWSCENTER_URL);
        //使用xutil联网请求数据
        //当需要使用极速数据时启用下面的getDataFromNet
        //getDataFromNet();


        //当需要使用极速数据时,注释下面五行
        fl_basepager.removeAllViews();
        NewsDetailPager newsDetailPager =new NewsDetailPager(context);
        newsDetailPager.initData();
        View rootview=newsDetailPager.rootview;
        fl_basepager.addView(rootview);


    }

    private void getDataFromNet() {
        RequestParams requestParams=new RequestParams(Myapi.JISUPINDAO_URL);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                LogUtil.e("联网成功");
                //缓存联网得到的数据
                //CacheUtils.putString(context,Myapi.JUHENEWS_URL,result);
                //处理数据
                ProcessData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("联网不成功"+ex.getMessage());


            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("取消了"+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("完成了");
            }
        });
    }

    private void ProcessData(String json) {
        JiSuNewsPindao_Bean jiSuNewsPindao_bean=parseJson(json);
        //测试是否解析成功
        String pindao=jiSuNewsPindao_bean.getResult().get(3);
        LogUtil.e("gson解析成功"+pindao);

        //data是一个List
        pindaolist=jiSuNewsPindao_bean.getResult();

        //
        fl_basepager.removeAllViews();
        NewsDetailPager newsDetailPager =new NewsDetailPager(context);
        newsDetailPager.initData();
        View rootview=newsDetailPager.rootview;
        fl_basepager.addView(rootview);

    }

    private JiSuNewsPindao_Bean parseJson(String json) {
        Gson gson=new Gson();
        JiSuNewsPindao_Bean bean = gson.fromJson(json, JiSuNewsPindao_Bean.class);
        return bean;
    }
}
