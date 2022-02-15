package pager.TabDetailPager;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.math.BigDecimal;
import java.util.List;

import Bean.JuheNews_Bean;
import Bean.WangyipicDange_Bean;
import Bean.Wangyipic_Bean;
import Bean.Wangyipic_Myserver_Bean;
import api.Myapi;
import pager.MenuDetailBasePager;
import pager.realpager.RealNewsPager;
import pager.realpager.RealPhotoPager;


public class PhotoTabDetailPager extends MenuDetailBasePager {


    private final String wangyipic_typetitleitem;
    private RefreshLayout photo_refreshLayout;
    private ListView lv_phototabdetailpager;
    private String photo_url;

    private MyphotoListAdapter myphotoListAdapter;
    private List<Wangyipic_Myserver_Bean.ResultEntity.DataEntity> piclist;
    private Wangyipic_Myserver_Bean.ResultEntity.DataEntity piclistitem;
    private int pager=1;


    public PhotoTabDetailPager(Context context, String wangyipic_typetitleitem) {
        super(context);
        LogUtil.e("调用了PhotoTabDetailPager"+wangyipic_typetitleitem);
        this.wangyipic_typetitleitem=wangyipic_typetitleitem;
    }


    @Override
    public View initView() {
        View view=View.inflate(context, com.example.zsmnews.R.layout.phototabdetailpager,null);
        x.view().inject(this,view);

        photo_refreshLayout=view.findViewById(com.example.zsmnews.R.id.photo_refreshLayout);
        lv_phototabdetailpager=view.findViewById(com.example.zsmnews.R.id.lv_phototabdetailpager);

        photo_refreshLayout.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        photo_refreshLayout.setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));

        photo_refreshLayout.setOnRefreshListener(new MyOnRefreshListener());

        photo_refreshLayout.setOnLoadMoreListener(new MyOnLoadMoreListener());

        lv_phototabdetailpager.setOnItemClickListener(new MyPhotoOnItemClickListener());


        return view;
    }

    class MyOnRefreshListener implements OnRefreshListener {

        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            String onrefreshurl;
            if(pager<=1){
                onrefreshurl=Myapi.Wangyipic_myprebaseurl+wangyipic_typetitleitem+Myapi.Wangyipic_mymidbaseurl+"1"+Myapi.Wangyipic_mypostbaseurl;;

            }else {
                pager--;
                String page=Integer.toString(pager);
                onrefreshurl=Myapi.Wangyipic_myprebaseurl+wangyipic_typetitleitem+Myapi.Wangyipic_mymidbaseurl+page+Myapi.Wangyipic_mypostbaseurl;

            }

            getPhotoDataFromNet(onrefreshurl);
            String page2=Integer.toString(pager);
            //Toast.makeText(context,"刷新数据成功",Toast.LENGTH_LONG);
            LogUtil.e("刷新数据成功,现在的页数是"+page2);
            //mylistAdapter.notifyDataSetChanged();
            //lv_newstabdetailpager.smoothScrollToPosition(29);
            refreshLayout.finishRefresh(2000);
        }
    }

    class MyOnLoadMoreListener implements OnLoadMoreListener {


        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            pager++;
            String page=Integer.toString(pager);
            //当前页面小于等于50时，就可以接着底部加载新闻
            if (pager <= 4) {

                String onloadmorehurl = Myapi.Wangyipic_myprebaseurl+wangyipic_typetitleitem+Myapi.Wangyipic_mymidbaseurl+page+Myapi.Wangyipic_mypostbaseurl;
                //initData();
                getPhotoDataFromNet(onloadmorehurl);
                myphotoListAdapter.notifyDataSetChanged();
                LogUtil.e("底部加载数据成功,这次的页数为："+page);
                refreshLayout.finishLoadMore(2000);//加载完成

            }else {
                LogUtil.e("底部加载数据成功,已加载完所有新闻");
                Toast.makeText(context,"已加载完所有新闻",Toast.LENGTH_LONG).show();
            }
        }
    }




    class MyPhotoOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Wangyipic_Myserver_Bean.ResultEntity.DataEntity netpiclistitem = piclist.get(position);
            LogUtil.e("新闻详情页面的内容的地址URL是："+netpiclistitem.getSeturl());
            Intent intent=new Intent(context, RealPhotoPager.class);
            intent.putExtra("realnewsurl",netpiclistitem.getSeturl());
            context.startActivity(intent);
        }
    }

    @Override
    public void initData() {


        super.initData();

        photo_url= Myapi.Wangyipic_myprebaseurl+wangyipic_typetitleitem+Myapi.Wangyipic_mymidbaseurl+"1"+Myapi.Wangyipic_mypostbaseurl;
        LogUtil.e("这个照片的url为："+photo_url);
        getPhotoDataFromNet(photo_url);

    }

    private void getPhotoDataFromNet(String netphoto_url) {
        RequestParams requestParams=new RequestParams(netphoto_url);
        requestParams.setConnectTimeout(10000);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("phototabdetailpager联网成功"+result);
                ProcessPhotoData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("傻逼,phototabdetailpager没有得到数据"+ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("phototabdetailpager取消了"+cex.getMessage());

            }

            @Override
            public void onFinished() {

                LogUtil.e("phototabdetailpager完成了");
            }
        });
    }

    private void ProcessPhotoData(String result) {
        Wangyipic_Myserver_Bean wangyipic_bean=parseJson(result);
        piclist=wangyipic_bean.getResult().getData();


        myphotoListAdapter=new MyphotoListAdapter();
        lv_phototabdetailpager.setAdapter(myphotoListAdapter);


    }
    private Wangyipic_Myserver_Bean parseJson(String result) {

        return new Gson().fromJson(result, Wangyipic_Myserver_Bean.class);
    }

    class MyphotoListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return piclist.size();
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
            ViewHolder viewHolder=new ViewHolder();

            if (view==null){
                view=View.inflate(context, com.example.zsmnews.R.layout.photoitem,null);
                viewHolder.photo_itemimageview=view.findViewById(com.example.zsmnews.R.id.photo_itemimageview);
                viewHolder.photo_title=view.findViewById(com.example.zsmnews.R.id.photo_title);
                viewHolder.photo_date=view.findViewById(com.example.zsmnews.R.id.photo_date);

                view.setTag(viewHolder);

            }else {
                viewHolder= (ViewHolder) view.getTag();
            }


            piclistitem=piclist.get(position);
            String imgurl=piclistitem.getCover();
            x.image().bind(viewHolder.photo_itemimageview,imgurl);
            viewHolder.photo_title.setText(piclistitem.getSetname());
            viewHolder.photo_date.setText(piclistitem.getDatetime());
            return view;
        }


    }

    class ViewHolder{
        ImageView photo_itemimageview;
        TextView photo_title;
        TextView photo_date;
    }


}
