package com.example.zsmnews.jpush.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zsmnews.baselibrary.arouter.InitService;
import com.example.zsmnews.baselibrary.arouter.ServiceConstant;

//import cn.jiguang.demo.baselibrary.arouter.InitService;
//import cn.jiguang.demo.baselibrary.arouter.ServiceConstant;


import cn.jpush.android.api.JPushInterface;

/**
 * Copyright(c) 2020 极光
 * Description
 */
@Route(path = ServiceConstant.SERVICE_PUSH)
public class PushServiceImpl implements InitService {

    private static final String TAG = "PushServiceImpl";


    @Override
    public void init(Context context) {

        Log.i(TAG, "PushServiceImpl init");

        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
    }
}
