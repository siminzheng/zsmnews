package Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class CacheUtils {


    //private static String result;

    public static boolean getBoolean(Context context, String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences("qwe",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }

    public static void putBoolean(Context context,String key,Boolean value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("qwe",Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key,value).commit();
    }

    //缓存联网得到的数据

    public static String getString(Context context,String url){

        SharedPreferences sharedPreferences=context.getSharedPreferences("qwe",Context.MODE_PRIVATE);
        //如果没有，默认获取空字符串""
        return sharedPreferences.getString(url,"");

    }

    public static void putString(Context context,String url,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("qwe",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(url,value).commit();
    }



}
