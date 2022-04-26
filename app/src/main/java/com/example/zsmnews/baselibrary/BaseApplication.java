package com.example.zsmnews.baselibrary;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;

//import cn.jiguang.demo.baselibrary.arouter.ServiceConstant;

import com.example.zsmnews.baselibrary.arouter.ServiceConstant;

/**
 * Copyright(c) 2020 极光
 * Description
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
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
