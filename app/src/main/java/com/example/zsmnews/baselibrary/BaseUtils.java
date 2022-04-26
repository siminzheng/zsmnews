package com.example.zsmnews.baselibrary;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Pattern;

import cn.jiguang.api.JCoreManager;

public class BaseUtils {


    public static final String REGEX = "^[a-z0-9A-Z\\u4E00-\\u9FA5_]+$";
    public static boolean matches(String str) {
        if(TextUtils.isEmpty(str)) return true;
        return str.matches(REGEX);
    }


    public static String APP_KEY = "";
    public static String getAppKey(Context context){
        if (!TextUtils.isEmpty(APP_KEY)) {
            return APP_KEY;
        }
        if (TextUtils.isEmpty(APP_KEY)) {
            try {
                if (context != null) {
                    ApplicationInfo ai = context.getPackageManager()
                            .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    if (ai != null && ai.metaData != null) {
                        Object obj = ai.metaData.get("JPUSH_APPKEY");
                        if (obj == null) {
                            return "";
                        } else {
                            APP_KEY = obj.toString();
                        }
                    }
                }
            } catch (Throwable e) {
            }
        }
        return APP_KEY;
    }


    // 取得版本号
    public static String getVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}
	

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static String getImei(Context context, String imei) {
        String ret = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            ret = telephonyManager.getDeviceId();
        } catch (Throwable e) {
            Log.e(BaseUtils.class.getSimpleName(), e.getMessage());
        }
        if (isReadableASCII(ret)){
            return ret;
        } else {
            return imei;
        }
    }

    private static boolean isReadableASCII(CharSequence string){
        if (TextUtils.isEmpty(string)) return false;
        try {
            Pattern p = Pattern.compile("[\\x20-\\x7E]+");
            return p.matcher(string).matches();
        } catch (Throwable e){
            return true;
        }
    }

    public static String getDeviceId(Context context) {
        Object o = JCoreManager.onEvent(context, null, 8, null, null);
        if(o instanceof String){
            return (String) o;
        }
        return "";
    }
}
