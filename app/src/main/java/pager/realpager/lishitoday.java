package pager.realpager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zsmnews.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import Bean.Jiqiren_Bean;
import Utils.ContentChuLi;
import api.Myapi;

public class lishitoday extends AppCompatActivity {



    private ImageView jiqiren_iv;
    private EditText jiqiren_tv;
    private Button tijiao;
    private WebView jiqiren_webview;
    private String wenti;
    private String jiqiren_url;
    private String daan;
    private WebSettings websetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lishitoday);
        jiqiren_iv = (ImageView) findViewById(R.id.jiqiren_iv);
        jiqiren_tv = (EditText) findViewById(R.id.jiqiren_tv);
        jiqiren_webview = (WebView) findViewById(R.id.jiqiren_webview);
        tijiao = (Button) findViewById(R.id.tijiao);


        //webview基础设置
        websetting=jiqiren_webview.getSettings();
        websetting.setJavaScriptEnabled(true);
        websetting.setBlockNetworkImage(false);
        websetting.setAppCacheEnabled(true);
        websetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        websetting.setAllowFileAccess(true);
        websetting.setDomStorageEnabled(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            websetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        jiqiren_webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        // 不让从当前网页跳转到系统的浏览器中
        jiqiren_webview.setWebViewClient(new WebViewClient());



        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取问题
                wenti= String.valueOf(jiqiren_tv.getText());
                jiqiren_url= Myapi.JISUSHUJU_JIQIRENBASEURL+wenti;
                LogUtil.e("这次问题的url:"+jiqiren_url);

                getDataFromNet(jiqiren_url);
            }
        });

    }

    private void getDataFromNet(String jiqiren_url) {
        RequestParams requestParams=new RequestParams(jiqiren_url);
        requestParams.setConnectTimeout(10000);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                LogUtil.e("机器人联网成功"+result);
                ProcessData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("机器人联网失败"+ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

                LogUtil.e("机器人联网取消"+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("机器人联网完成");

            }
        });


    }

    private void ProcessData(String result) {

        Jiqiren_Bean jiqiren_bean=parejson(result);
        daan=jiqiren_bean.getResult().getContent();
        daan=ContentChuLi.Chuli(daan);
        jiqiren_webview.loadData(daan,"text/html","utf-8");

    }

    private Jiqiren_Bean parejson(String result) {
        return new Gson().fromJson(result,Jiqiren_Bean.class);

    }


}