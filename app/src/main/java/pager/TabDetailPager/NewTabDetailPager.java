package pager.TabDetailPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zsmnews.R;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import Bean.JiSuNews_bean;
import Bean.JuheNews_Bean;
import Utils.CacheUtils;
import pager.MenuDetailBasePager;
import pager.realpager.RealNewsPager;
import api.Myapi;

public class NewTabDetailPager extends MenuDetailBasePager {

    private final String pindaolistItem;

    private RefreshLayout refreshlayout;
    private ListView lv_newstabdetailpager;
    private ImageButton ib_newstabdetailrefresh;
    private String url;
    private List<JuheNews_Bean.ResultEntity.DataEntity> newslist;
    private MyListViewAdapter mylistAdapter;
    private String preresult;
    //private int prenewslistsize;
    private int pager=1;
    Boolean netisrefresh=false;
    private String preresulturl;


    public NewTabDetailPager(Context context, String pindaolistItem) {
        super(context);
        LogUtil.e("调用了NewTabDetailPager"+pindaolistItem);
        this.pindaolistItem=pindaolistItem;
        ib_newstabdetailrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllNewsRefresh();
            }
        });

    }



    @Override
    public View initView() {

        View view=View.inflate(context, R.layout.newstabdetailpager,null);
        x.view().inject(this,view);

        refreshlayout=view.findViewById(R.id.refreshLayout);
        ib_newstabdetailrefresh=view.findViewById(R.id.ib_newstabdetailrefresh);

        refreshlayout.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refreshlayout.setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
        lv_newstabdetailpager=view.findViewById(R.id.lv_newstabdetailpager);

        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                String onrefreshurl;
                if(pager<=1){
                    onrefreshurl=Myapi.JUHEBASE_URL+pindaolistItem+"&page=1"+Myapi.JUHENEWS_URL;

                }else {
                    pager--;
                    String page=Integer.toString(pager);
                    onrefreshurl=Myapi.JUHEBASE_URL+pindaolistItem+"&page="+page+Myapi.JUHENEWS_URL;

                }
                netisrefresh=true;
                getDataFromNet(onrefreshurl);
                String page2=Integer.toString(pager);
                //Toast.makeText(context,"刷新数据成功",Toast.LENGTH_LONG);
                LogUtil.e("刷新数据成功,现在的页数是"+page2);
                //mylistAdapter.notifyDataSetChanged();
                //lv_newstabdetailpager.smoothScrollToPosition(29);
                refreshLayout.finishRefresh(2000);
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pager++;
                String page=Integer.toString(pager);
                //当前页面小于等于50时，就可以接着底部加载新闻
                if (pager <= 50) {
                    netisrefresh=true;
                    String onloadmorehurl = Myapi.JUHEBASE_URL + pindaolistItem + "&page="+page + Myapi.JUHENEWS_URL;
                    //initData();
                    getDataFromNet(onloadmorehurl);
                    mylistAdapter.notifyDataSetChanged();
                    LogUtil.e("底部加载数据成功,这次的页数为："+page);
                    refreshLayout.finishLoadMore(2000);//加载完成

                }else {
                    LogUtil.e("底部加载数据成功,已加载完所有新闻");
                    Toast.makeText(context,"已加载完所有新闻",Toast.LENGTH_LONG).show();
                }
            }
        });




        lv_newstabdetailpager.setOnItemClickListener(new MyOnItemClickListener());
        return view;
    }

    //给刷新按钮调用
    public void AllNewsRefresh() {
        netisrefresh=true;
        String allnewsrefreshurl=Myapi.JUHEBASE_URL+pindaolistItem+"&page=1"+Myapi.JUHENEWS_URL;
        pager=1;
        getDataFromNet(allnewsrefreshurl);
        lv_newstabdetailpager.smoothScrollToPosition(0);
        LogUtil.e("已调用刷新按钮回到第一页"+pager);

    }


    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            JuheNews_Bean.ResultEntity.DataEntity newslistitem = newslist.get(position);
            LogUtil.e("新闻详情页面的内容的地址URL是："+newslistitem.getUrl());
            Intent intent=new Intent(context, RealNewsPager.class);
            Bundle bd=new Bundle();
            bd.putString("realnewsurl",newslistitem.getUrl());
            bd.putString("realnewstitle",newslistitem.getTitle());
            bd.putString("realnewsdate",newslistitem.getDate());
            bd.putString("realnewspic",newslistitem.getThumbnail_pic_s());
            intent.putExtras(bd);
            context.startActivity(intent);

        }
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e(pindaolistItem);
        //极速新闻url
        //url= Myapi.JISULIBIE_URL+pindaolistItem+Myapi.JISULIBIEHOUZHUI_URL;
        //聚合新闻url
        url=Myapi.JUHEBASE_URL+pindaolistItem+"&page=1"+Myapi.JUHENEWS_URL;

        LogUtil.e("聚合新闻的请求的URL为："+url);
        preresult=CacheUtils.getString(context,preresulturl);
        //如果上次存有数据,那么就加载上次的数据
        if (!TextUtils.isEmpty(preresult)){
            //ProcessData(preresult);
            LogUtil.e("这次的显示的缓存是"+preresult);
            ProcessData(preresult);

        }else{
            netisrefresh=true;
            getDataFromNet(url);
        }

    }

    private void getDataFromNet(String neturl) {

        if (netisrefresh==true){
            netisrefresh=false;
            preresulturl=neturl;
            RequestParams requestParams=new RequestParams(neturl);
            requestParams.setConnectTimeout(10000);
            x.http().get(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {


                    if (result !="{\"resultcode\":\"112\",\"reason\"" +
                            ":\"超过每日可允许请求次数!\",\"result\":null,\"error_code\":10012}"){
                    CacheUtils.putString(context,neturl,result);
                    }
                    LogUtil.e("newtabdetailpager联网成功"+result);
                    ProcessData(result);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    LogUtil.e("傻逼,newtabdetailpager没有得到数据"+ex.getMessage());

                }

                @Override
                public void onCancelled(CancelledException cex) {
                    LogUtil.e("newtabdetailpager取消了"+cex.getMessage());

                }

                @Override
                public void onFinished() {
                    LogUtil.e("newtabdetailpager完成了");

                }
            });

        }


    }

    private void ProcessData(String result) {
        //JiSuNews_bean jiSuNews_bean=parseJson(result);
        JuheNews_Bean juheNews_bean=parseJson(result);
        //准备listview对应的数据
        newslist=juheNews_bean
                .getResult()
                .getData();

        mylistAdapter=new MyListViewAdapter();
        lv_newstabdetailpager.setAdapter(mylistAdapter);

    }


    private JuheNews_Bean parseJson(String result) {

        return new Gson().fromJson(result, JuheNews_Bean.class);

    }

    class MyListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() { return newslist.size(); }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder=new ViewHolder();
            if (view==null){
                view=View.inflate(context, R.layout.listnewsitem,null);
                viewHolder=new ViewHolder();
                viewHolder.iv_image=(ImageView)view.findViewById(R.id.iv_image);
                viewHolder.tv_newstitle=(TextView)view.findViewById(R.id.tv_newstitle);
                viewHolder.tv_date=(TextView) view.findViewById(R.id.tv_date);
                viewHolder.tv_src=(TextView) view.findViewById(R.id.tv_src);

                view.setTag(viewHolder);
            }
            else{
                viewHolder= (ViewHolder) view.getTag();
            }

            JuheNews_Bean.ResultEntity.DataEntity newslistitem = newslist.get(position);



            String imgurl=newslistitem.getThumbnail_pic_s();
            x.image().bind(viewHolder.iv_image,imgurl);
            viewHolder.tv_newstitle.setText(newslistitem.getTitle());
            viewHolder.tv_date.setText(newslistitem.getDate());
            viewHolder.tv_src.setText(newslistitem.getAuthor_name());

            return view;
        }
    }

    class ViewHolder{
        ImageView iv_image;
        TextView tv_newstitle;
        TextView tv_date;
        TextView tv_src;
    }
}
