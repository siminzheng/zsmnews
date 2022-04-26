package com.example.zsmnews.jpush;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mmkv.MMKV;

//import cn.jiguang.demo.R;
//import cn.jiguang.demo.baselibrary.ClipUtils;
//import cn.jiguang.demo.baselibrary.ScreenUtils;
//import cn.jiguang.demo.baselibrary.ToastHelper;

import com.example.zsmnews.R;
import com.example.zsmnews.baselibrary.ScreenUtils;
import com.example.zsmnews.baselibrary.ClipUtils;
import com.example.zsmnews.baselibrary.ToastHelper;
import cn.jpush.android.api.JPushInterface;

/**
 * Copyright(c) 2020 极光
 * Description
 */
public class PushActivity extends AppCompatActivity implements View.OnClickListener {


    private ToggleButton toggle;
    private TextView tvOnline;
    private TextView tvAppKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jpush_demo_activity_push);
        ScreenUtils.setStatusBarTransparent(getWindow());
        initView();
        registerReceiver(reciver, new IntentFilter("com.jiguang.demo.message"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }

    private void initView() {
        findViewById(R.id.tv_notify).setOnClickListener(this);
        findViewById(R.id.tv_adv).setOnClickListener(this);
        findViewById(R.id.iv_info).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        toggle = findViewById(R.id.toggle);
        toggle.setOnClickListener(this);
        boolean checked = MMKV.defaultMMKV().getBoolean("PushOnline", true);
        toggle.setChecked(checked);

        tvOnline = findViewById(R.id.tv_online);
        updateStatu();
        tvAppKey = findViewById(R.id.tv_appKey_desc);

        String registrationID = JPushInterface.getRegistrationID(getApplicationContext());
        tvAppKey.setText(registrationID);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            onBackPressed();
        } else if (id == R.id.toggle) {
            updateStatu();
            ToastHelper.showOk(getApplicationContext(), getString(R.string.toast_modify_ok));
        } else if (id == R.id.iv_info) {
            ClipUtils.copyText(v.getContext(), tvAppKey.getText().toString());
        } else if (id == R.id.tv_notify) {

            if (toggle.isChecked()) {
                if (JPushInterface.isNotificationEnabled(this)==1) {
                    ExampleUtil.buildLocalNotification(this.getApplicationContext(), getString(R.string.app_name), "这是一条测试消息。");
                } else {
                    pushPermissionDialog();
                }
            } else {
                ToastHelper.showOther(getApplicationContext(), "不接收通知，不发送本地通知请求");
            }
        } else if (id == R.id.tv_adv) {
            startActivity(new Intent(this, com.example.zsmnews.jpush.AdvActivity.class));
        }
    }

    private void pushPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dv = View.inflate(this, R.layout.d_dialog_two_button, null);
        TextView tvOk = (TextView) dv.findViewById(R.id.btn_ok);
        TextView tvCancel = (TextView) dv.findViewById(R.id.btn_cancel);
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(dv);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JPushInterface.goToAppNotificationSettings(PushActivity.this);
                dialog.dismiss();
            }
        });
    }

    private final BroadcastReceiver reciver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dv = View.inflate(context, R.layout.d_dialog_msg, null);
            TextView tvOk = (TextView) dv.findViewById(R.id.btn_ok);
            TextView msg = (TextView) dv.findViewById(R.id.msg);
            msg.setText(intent.getStringExtra("msg"));
            final Dialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setContentView(dv);
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    };

    private void updateStatu(){
        boolean checked = toggle.isChecked();
        tvOnline.setText(checked ? "接收" : "不接收");
        if(checked){
            JPushInterface.resumePush(this);
        }else{
            JPushInterface.stopPush(this);
        }
        MMKV.defaultMMKV().putBoolean("PushOnline", checked);
    }
}
