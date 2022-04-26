package com.example.zsmnews.baselibrary;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

//import cn.jiguang.demo.R;
import com.example.zsmnews.R;

public class ClipUtils {

    private ClipUtils() {
        throw new UnsupportedOperationException("can't create instance");
    }

    public static void copyText(Context context, String text) {
        //1. 复制字符串到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, text));
        if (!TextUtils.isEmpty(text.trim())) {
            ToastHelper.showOk(context.getApplicationContext(), context.getString(R.string.toast_copy_ok));
        }
    }

}