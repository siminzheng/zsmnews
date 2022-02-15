package com.example.zsmnews;

import android.app.Application;

import org.xutils.x;

public class zsmApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
