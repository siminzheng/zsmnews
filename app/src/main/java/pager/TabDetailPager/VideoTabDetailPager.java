package pager.TabDetailPager;

import android.content.Context;
import android.icu.util.Measure;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
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

import Bean.JuheNews_Bean;
import Bean.WangyiVideo_Bean;
import Bean.WangyiVideoerciyuan_Bean;
import Bean.WangyiVideoheikeji_Bean;
import Bean.WangyiVideomengwu_Bean;
import Bean.WangyiVideoxinwen_Bean;
import Bean.WangyiVideozhangzishi_Bean;
import api.Myapi;
import cn.jzvd.JzvdStd;
import pager.MenuDetailBasePager;

public class VideoTabDetailPager extends MenuDetailBasePager {
    private final String pindaolistItem;
    private RefreshLayout video_refreshlayout;
    //private ImageButton ib_videotabdetailrefresh;
    private ListView lv_videotabdetailpager;
    private String url;
    private List<WangyiVideo_Bean.VAP4BFE3UEntity> videolist;
    private MyVediolistAdapter mylistAdapter;
    private WangyiVideo_Bean.VAP4BFE3UEntity videolistitem;
    private List<WangyiVideoxinwen_Bean.VAV3H6JSNEntity> videolistxinwen;
    private List<WangyiVideomengwu_Bean.VAP4BFR16Entity> videolistmengwu;
    private List<WangyiVideoheikeji_Bean.VBF8F2PKFEntity> videolistheikeji;
    private List<WangyiVideoerciyuan_Bean.VBF8F1PSAEntity> videolisterciyuan;
    private List<WangyiVideozhangzishi_Bean.VBF8F3SGLEntity> videolistzhangzishi;
    private WangyiVideoxinwen_Bean.VAV3H6JSNEntity videolistitemxinwen;
    private WangyiVideomengwu_Bean.VAP4BFR16Entity videolistitemmengwu;
    private WangyiVideoheikeji_Bean.VBF8F2PKFEntity videolistitemheikeji;
    private WangyiVideoerciyuan_Bean.VBF8F1PSAEntity videolistitemerciyuan;
    private WangyiVideozhangzishi_Bean.VBF8F3SGLEntity videolistitemzhangzishi;
    private int page1=0;
    private String onrefreshurl;
    private int page2=10;


    public VideoTabDetailPager(Context context, String pindaolistItem) {
        super(context);
        LogUtil.e("调用了VideoTabDetailPager"+pindaolistItem);
        this.pindaolistItem=pindaolistItem;
    }

    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.videotabdetailpager,null);
        x.view().inject(this,view);

        video_refreshlayout=view.findViewById(R.id.video_refreshLayout);
        //ib_videotabdetailrefresh=view.findViewById(R.id.ib_videotabdetailrefresh);
        lv_videotabdetailpager=view.findViewById(R.id.lv_videotabdetailpager);

        video_refreshlayout.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        video_refreshlayout.setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));

        video_refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(page1<=0) {
                    page1=0;
                    page2=page1+10;

                onrefreshurl=Myapi.Wangyivideo_Baseurl + pindaolistItem +
                        Myapi.Wangyivideo_midyurl + Integer.toString(page1)
                        +"-"+ Integer.toString(page2)+Myapi.Wangyivideo_posturl;
                        getVideoDataFromNet(onrefreshurl);
                }else {
                    page1=page1-10;
                    page2=page1+10;
                    onrefreshurl=Myapi.Wangyivideo_Baseurl + pindaolistItem +
                            Myapi.Wangyivideo_midyurl + Integer.toString(page1)
                            +"-"+ Integer.toString(page2)+Myapi.Wangyivideo_posturl;
                    getVideoDataFromNet(onrefreshurl);

                }
                LogUtil.e("刷新视频数据成功,现在的页数是"+page1);
                refreshLayout.finishRefresh(2000);

            }
        });

        video_refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page1=page1+10;
                page2=page1+10;
                onrefreshurl=Myapi.Wangyivideo_Baseurl + pindaolistItem +
                        Myapi.Wangyivideo_midyurl + Integer.toString(page1)
                        +"-"+ Integer.toString(page2)+Myapi.Wangyivideo_posturl;
                getVideoDataFromNet(onrefreshurl);
                mylistAdapter.notifyDataSetChanged();
                LogUtil.e("底部加载视频数据成功,这次的页数为："+page1);
                refreshLayout.finishLoadMore(2000);//加载完成

            }
        });


        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url= Myapi.Wangyivideo_Baseurl+pindaolistItem+Myapi.Wangyivideo_midyurl+"0-10"+Myapi.Wangyivideo_posturl;

        LogUtil.e("视频的请求的URL为："+url);
        getVideoDataFromNet(url);
    }

    private void getVideoDataFromNet(String neturl) {

        RequestParams requestParams=new RequestParams(neturl);
        requestParams.setConnectTimeout(10000);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("newtabdetailpager联网成功"+result);
                ProcessData(result,pindaolistItem);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("傻逼,videotabdetailpager没有得到数据"+ex.getMessage());

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




    private void ProcessData(String result, String pindaolistItem) {
        if (pindaolistItem=="VAP4BFE3U"){
        WangyiVideo_Bean wangyivideo_bean=parseJson(result);
        //准备listview对应的数据
        videolist=wangyivideo_bean.getVAP4BFE3U();
        }else if (pindaolistItem=="VAV3H6JSN"){
            WangyiVideoxinwen_Bean wangyiVideoxinwen_bean=pareJsonxinwen(result);
            videolistxinwen=wangyiVideoxinwen_bean.getVAV3H6JSN();
        }else if (pindaolistItem=="VAP4BFR16"){
            WangyiVideomengwu_Bean wangyivideomengwu_bean=parseJsonmengwu(result);
            videolistmengwu=wangyivideomengwu_bean.getVAP4BFR16();
        }else if (pindaolistItem=="VBF8F2PKF"){
            WangyiVideoheikeji_Bean wangyivideoheikeji_bean=parseJsonheikeji(result);
            videolistheikeji=wangyivideoheikeji_bean.getVBF8F2PKF();
        }else if (pindaolistItem=="VBF8F1PSA"){
            WangyiVideoerciyuan_Bean wangyiVideoerciyuan_bean=parseJsonerciyuan(result);
            videolisterciyuan=wangyiVideoerciyuan_bean.getVBF8F1PSA();
        }else if (pindaolistItem=="VBF8F3SGL"){
            WangyiVideozhangzishi_Bean wangyiVideozhangzishi_bean=parseJsonzhangzishi(result);
            videolistzhangzishi=wangyiVideozhangzishi_bean.getVBF8F3SGL();
        }

        mylistAdapter=new MyVediolistAdapter();
        lv_videotabdetailpager.setAdapter(mylistAdapter);
    }

    private WangyiVideo_Bean parseJson(String result) {
        return new Gson().fromJson(result, WangyiVideo_Bean.class);
    }
    private WangyiVideoxinwen_Bean pareJsonxinwen(String result){
        return new Gson().fromJson(result, WangyiVideoxinwen_Bean.class);

    }
    private WangyiVideomengwu_Bean parseJsonmengwu(String result) {
        return new Gson().fromJson(result, WangyiVideomengwu_Bean.class);
    }
    private WangyiVideoheikeji_Bean parseJsonheikeji(String result) {
        return new Gson().fromJson(result, WangyiVideoheikeji_Bean.class);
    }
    private WangyiVideoerciyuan_Bean parseJsonerciyuan(String result) {
        return new Gson().fromJson(result, WangyiVideoerciyuan_Bean.class);
    }
    private WangyiVideozhangzishi_Bean parseJsonzhangzishi(String result) {
        return new Gson().fromJson(result, WangyiVideozhangzishi_Bean.class);
    }

    class MyVediolistAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 10;

        }

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
            ViewHolder viewHolder;
            if (view==null){
            view=View.inflate(context,R.layout.listvideoitem,null);
            viewHolder=new ViewHolder();
            viewHolder.jz_video=view.findViewById(R.id.jz_video);
            viewHolder.video_title=view.findViewById(R.id.video_title);
            viewHolder.video_src=view.findViewById(R.id.video_src);
            view.setTag(viewHolder);
            }
            else{
                viewHolder= (ViewHolder) view.getTag();
            }

            if (pindaolistItem=="VAP4BFE3U"){
            videolistitem=videolist.get(position);
            viewHolder.jz_video.setUp(videolistitem.getMp4_url(),videolistitem.getTitle());
            Glide
                    .with(context)
                    .load(videolistitem.getCover())
                    .into(viewHolder.jz_video.posterImageView);
            viewHolder.video_title.setText(videolistitem.getTitle());
            viewHolder.video_src.setText(videolistitem.getVideosource());
            LogUtil.e("视频的地址是"+videolistitem.getMp4_url());
            }else if(pindaolistItem=="VAV3H6JSN"){
                videolistitemxinwen=videolistxinwen.get(position);
                viewHolder.jz_video.setUp(videolistitemxinwen.getMp4_url(),videolistitemxinwen.getTitle());
                Glide
                        .with(context)
                        .load(videolistitemxinwen.getCover()).
                        into(viewHolder.jz_video.posterImageView);
                viewHolder.video_title.setText(videolistitemxinwen.getTitle());
                viewHolder.video_src.setText(videolistitemxinwen.getVideosource());
                LogUtil.e("视频的地址是"+videolistitemxinwen.getMp4_url());

            }else if(pindaolistItem=="VAP4BFR16"){
                videolistitemmengwu=videolistmengwu.get(position);
                viewHolder.jz_video.setUp(videolistitemmengwu.getMp4_url(),videolistitemmengwu.getTitle());
                Glide
                        .with(context)
                        .load(videolistitemmengwu.getCover())
                        .into(viewHolder.jz_video.posterImageView);
                viewHolder.video_title.setText(videolistitemmengwu.getTitle());
                viewHolder.video_src.setText(videolistitemmengwu.getVideosource());
                LogUtil.e("视频的地址是"+videolistitemmengwu.getMp4_url());

            }else if(pindaolistItem=="VBF8F2PKF"){
                videolistitemheikeji=videolistheikeji.get(position);
                viewHolder.jz_video.setUp(videolistitemheikeji.getMp4_url(),videolistitemheikeji.getTitle());
                Glide
                        .with(context)
                        .load(videolistitemheikeji.getCover())
                        .into(viewHolder.jz_video.posterImageView);
                viewHolder.video_title.setText(videolistitemheikeji.getTitle());
                viewHolder.video_src.setText(videolistitemheikeji.getVideosource());
                LogUtil.e("视频的地址是"+videolistitemheikeji.getMp4_url());

            }else if(pindaolistItem=="VBF8F1PSA"){
                videolistitemerciyuan=videolisterciyuan.get(position);
                viewHolder.jz_video.setUp(videolistitemerciyuan.getMp4_url(),videolistitemerciyuan.getTitle());
                Glide
                        .with(context)
                        .load(videolistitemerciyuan.getCover())
                        .into(viewHolder.jz_video.posterImageView);
                viewHolder.video_title.setText(videolistitemerciyuan.getTitle());
                viewHolder.video_src.setText(videolistitemerciyuan.getVideosource());
                LogUtil.e("视频的地址是"+videolistitemerciyuan.getMp4_url());

            }else if(pindaolistItem=="VBF8F3SGL"){
                videolistitemzhangzishi=videolistzhangzishi.get(position);
                viewHolder.jz_video.setUp(videolistitemzhangzishi.getMp4_url(),videolistitemzhangzishi.getTitle());
                Glide
                        .with(context)
                        .load(videolistitemzhangzishi.getCover())
                        .into(viewHolder.jz_video.posterImageView);
                viewHolder.video_title.setText(videolistitemzhangzishi.getTitle());
                viewHolder.video_src.setText(videolistitemzhangzishi.getVideosource());
                LogUtil.e("视频的地址是"+videolistitemzhangzishi.getMp4_url());
            }

            return view;
        }

    }

    class ViewHolder{
        JzvdStd jz_video;
        TextView video_title;
        TextView video_src;
    }
}
