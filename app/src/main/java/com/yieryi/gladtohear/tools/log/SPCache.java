package com.yieryi.gladtohear.tools.log;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/8/31.
 */
public class SPCache {
    private static final String TAG=SPCache.class.getSimpleName();
    private SharedPreferences sp;
    private static SPCache INSTANCE;
    private SPCache(Context context,String spName){
        sp=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
    }
    //单例模式构建文件
    public static SPCache init(Context context, String prefFileName) {
        if (INSTANCE == null) {
            synchronized (SPCache.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SPCache(context, prefFileName);
                }
            }
        }
        return INSTANCE;
    }
}
