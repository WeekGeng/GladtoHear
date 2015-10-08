package com.yieryi.gladtohear.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.tools.AmapLocationUtils;
import com.yieryi.gladtohear.tools.OnLocalListener;
import com.yieryi.gladtohear.tools.sp.SPCache;

import java.util.Timer;
import java.util.TimerTask;

public class WelActivity extends BaseActivity implements OnLocalListener{
    private TextView tv_location;
    private LocationManagerProxy mLocationManagerProxy;
    private AmapLocationUtils amaplocation;
    private String provience;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  0:
                    if (provience==null||"".equals(provience)){
                        provience="上海";
                    }
                    SPCache.putString("provience", provience);
                    startActivity(WelActivity.this, MainActivity.class, "provience", provience);
                    finish();
                    break;
                case 1:
                    showToast("定位失败");
                    provience=SPCache.getString("provience","");
                    if (provience==null||"".equals(provience)){
                        provience="上海";
                    }
                    SPCache.putString("provience", provience);
                    startActivity(WelActivity.this, MainActivity.class, "provience", provience);
                    finish();
                    break;
            }

        }
    };
    @Override
    public int getLayout() {
        return R.layout.activity_wel;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        boolean setLocation=getIntent().getBooleanExtra("setLocation", false);
//        if (!setLocation){
//            Timer timer_location = new Timer();
//            TimerTask task_location=new TimerTask() {
//                @Override
//                public void run() {
//                    stopLocation();
//                    handler.sendEmptyMessage(1);
//                }
//            };
//            if (provience == null || "".equals(provience)) {
//                timer_location.schedule(task_location, 5000);
//            }
//        }
    }

    @Override
    protected void setToolBar(ActionBar action,boolean isTrue) {
        action.setTitle("切换城市");
        action.setHomeButtonEnabled(isTrue);
    }
    /**
     * 初始化view
     */
    private void initView() {
        tv_location=(TextView)findViewById(R.id.location);
        amaplocation=new AmapLocationUtils(this);
        mLocationManagerProxy=LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, -1, 10, amaplocation);
        mLocationManagerProxy.setGpsEnable(true);
    }

    /**
     * 结束定位
     */
    private void stopLocation() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(amaplocation);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocation();
    }

    /**
     * 定位失败后索要做的事情
     * @param error
     */
    @Override
    public void setError(String error) {
        showMsg(this, error);
        //接下来处理定位失败后要做的事情
    }

    /**
     * 定位成功后索要做的事情
     * @param aMapLocation
     */
    @Override
    public void onSuccess(AMapLocation aMapLocation) {
        provience=aMapLocation.getProvince();
        if (provience==null||"".equals(provience)){
            stopLocation();
            showToast("请检查网络,或者查看GPS");
            return;
        }
        showMsg(this,"当前定位城市为："+provience);
        tv_location.setText(provience);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 1000);
    }

    /**
     * toast事件
     * @param context
     * @param text
     */
    public void showMsg(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    /**
     * 原始跳转
     * @param context 上下文对象
     * @param cla 类对象
     */
    public void startActivity(Context context,Class cla){
        Intent intent=new Intent(context,cla);
        startActivity(intent);
        finish();
    }

    /**
     * 带有action的跳转
     * @param context
     * @param cla
     * @param action
     */
    public void startActivity(Context context,Class cla,String action){
        Intent intent=new Intent(context,cla);
        intent.setAction(action);
        startActivity(intent);
        finish();
    }

    /**
     * 带有bundle的intent
     * @param context
     * @param cla
     * @param bundle
     * @param key bundle的键名
     */
    public void startActivity(Context context,Class cla,Bundle bundle,String key){
        Intent intent=new Intent(context,cla);
        intent.putExtra(key, bundle);
        startActivity(intent);
        finish();
    }

    /**
     * 带有string参数的是intent
     * @param context
     * @param cla
     * @param text
     * @param key
     */
    public void startActivity(Context context,Class cla,String text,String key){
        Intent intent=new Intent(context,cla);
        intent.putExtra(key,text);
        startActivity(intent);
        finish();
    }
}
