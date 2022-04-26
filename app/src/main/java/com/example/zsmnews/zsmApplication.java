package com.example.zsmnews;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zsmnews.baselibrary.arouter.ServiceConstant;
import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;

import org.litepal.LitePal;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

//import cn.jpush.android.api.JPushInterface;

public class zsmApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);

        //初始化LitePale
        LitePal.initialize(this);

        //初始化极光,
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);

        initRouter();
        initToast();
        initKV();

    }

    private void initKV() {
        MMKV.initialize(this);
    }

    private void initToast() {
        ToastUtils.init(this);
    }

    private void initRouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

        ARouter.getInstance().build(ServiceConstant.SERVICE_SHARE).navigation();
        ARouter.getInstance().build(ServiceConstant.SERVICE_LINK).navigation();
        ARouter.getInstance().build(ServiceConstant.SERVICE_PUSH).navigation();
        ARouter.getInstance().build(ServiceConstant.SERVICE_VERIFY).navigation();
        ARouter.getInstance().build(ServiceConstant.SERVICE_UNION).navigation();
    }
}
