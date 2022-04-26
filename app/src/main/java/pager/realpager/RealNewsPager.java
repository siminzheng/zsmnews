package pager.realpager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.zsmnews.R;
import com.example.zsmnews.dataBean.newsshoucang;

import org.litepal.LitePal;
import org.xutils.common.util.LogUtil;

import java.util.List;

public class RealNewsPager extends AppCompatActivity implements View.OnClickListener{

    private ImageButton ib_goback;
    private ImageButton ib_textsize;
    private WebView web_view;
    private ToggleButton shoucanganniu;
    private String realNewsurl;
    private WebSettings websetting;
    private String CachePath;
    private String realNewsTitle;
    private String realNewsDate;
    private String realNewsPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_news_pager);
        //删除之前的收藏
        //LitePal.deleteAll(newsshoucang.class);

        Intent it2=getIntent();
        Bundle bd2=it2.getExtras();
        realNewsurl=bd2.getString("realnewsurl");
        realNewsTitle=bd2.getString("realnewstitle");
        realNewsDate=bd2.getString("realnewsdate");
        realNewsPic=bd2.getString("realnewspic");
        LogUtil.e("传进来给数据库用的url有："+realNewsurl+" "+realNewsTitle+"  "+realNewsDate+" "+realNewsPic);
        //realNewsurl=getIntent().getStringExtra("realnewsurl");




        findView();
        getData();

        List<newsshoucang> nes2list = LitePal.where("url=?", realNewsurl).find(newsshoucang.class);
        if (nes2list.size() > 0){
            shoucanganniu.setChecked(true);
        }

    }

    private void getData() {



        CachePath=this.getApplicationContext().getCacheDir().getAbsolutePath();
        websetting=web_view.getSettings();
        websetting.setJavaScriptEnabled(true);
        websetting.setBlockNetworkImage(false);

        websetting.setAppCacheEnabled(true);
        websetting.setAppCachePath(CachePath);
        websetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        websetting.setAllowFileAccess(true);

        websetting.setDomStorageEnabled(true);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            websetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        web_view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//            websetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }

        // 不让从当前网页跳转到系统的浏览器中
        web_view.setWebViewClient(new WebViewClient());
        web_view.loadUrl(realNewsurl);


    }

    class DemoWebViewClient extends WebViewClient {


    }

    private void findView() {
        ib_goback=(ImageButton) findViewById(R.id.ib_goback);
        ib_textsize = (ImageButton) findViewById(R.id.ib_textsize);
        web_view = (WebView) findViewById(R.id.web_view);
        shoucanganniu = (ToggleButton) findViewById(R.id.shoucanganniu);


        //设置点击事件
        ib_goback.setOnClickListener((View.OnClickListener) this);
        ib_textsize.setOnClickListener((View.OnClickListener) this);
        shoucanganniu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {

                List<newsshoucang> nes2list = LitePal.where("url=?", realNewsurl).find(newsshoucang.class);
                if (nes2list.size() == 0 &&ischecked){

                        newsshoucang nes=new newsshoucang();
                        nes.setUrl(realNewsurl);
                        nes.setTitle(realNewsTitle);
                        nes.setDate(realNewsDate);
                        nes.setPic(realNewsPic);
                        nes.save();
                        Toast.makeText(RealNewsPager.this,"新闻收藏成功",Toast.LENGTH_SHORT).show();

                    }else if (nes2list.size() > 0 && !ischecked){
                        LitePal.deleteAll(newsshoucang.class,"url=?",realNewsurl);
                        Toast.makeText(RealNewsPager.this,"新闻取消收藏成功",Toast.LENGTH_SHORT).show();
                    }


            }
        });
    }


    private int temSize=2;
    private int realSize=temSize;
    private void showChangeTextSizeDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("设置字体大小");
        String[] item = {"超大字体","大字体","正常字体","小字体","超小字体"};
        //默认选择大字体
        builder.setSingleChoiceItems(item, realSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                temSize=which;
            }
        });

        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                realSize=temSize;
                changeTextSize(realSize);
            }
        });

        builder.show();
    }

    private void changeTextSize(int realSize) {
        switch (realSize){
            case 0:
                websetting.setTextZoom(200);
                break;
            case 1:
                websetting.setTextZoom(150);
                break;
            case 2:
                websetting.setTextZoom(100);
                break;
            case 3:
                websetting.setTextZoom(75);
                break;
            case 4:
                websetting.setTextZoom(50);
                break;
        }

    }


    @Override
    public void onClick(View view) {
        if (view==ib_goback){
            finish();
        }
        else if (view==ib_textsize){
            //Toast.makeText(this, "设置文字大小", Toast.LENGTH_SHORT).show();
            showChangeTextSizeDialog();
        }

    }
}