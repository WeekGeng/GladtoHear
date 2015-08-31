package com.yieryi.gladtohear.base;

import android.app.Application;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yieryi.gladtohear.tools.log.Log;

/**
 * Created by Administrator on 2015/8/27.
 */
public class TApplication extends Application{
    private static final String TAG=TApplication.class.getSimpleName();
    public static TApplication getInstance=null;
    //高德地图定位
    private static String spName="yiErYi_gaoDe_local";
    private static SharedPreferences.Editor edit;
    @Override
    public void onCreate() {
        super.onCreate();
        getInstance=this;
        ImageLoaderConfiguration configuration=ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        createSp(spName);
    }
    public static void createSp(String spName){
        SharedPreferences preferences = getInstance.getSharedPreferences(spName, MODE_PRIVATE);
        edit = preferences.edit();
    }
    public static void put(String key,Object o){
        if (o instanceof Integer){
            edit.putInt(key,(Integer)o);
        }else if (o instanceof Long){
            edit.putLong(key,(Long)o);
        }else if (o instanceof Boolean){
            edit.putBoolean(key, (Boolean)o);
        }else if (o instanceof Float){
            edit.putFloat(key, (Float)o);
        }else if (o instanceof String){
            edit.putString(key, (String)o);
        }else {
            Log.i(TAG,"you may be put a invalid object"+o);
        }
    }
}
