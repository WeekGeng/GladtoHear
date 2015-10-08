package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;

import com.umeng.message.IUmengRegisterCallback;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.base.TApplication;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.tools.sp.SPCache;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {
    private final String TAG= SplashActivity.class.getSimpleName();
    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mPushAgent.enable(mRegist);
        initTask();
    }
    /**
     * 设置toolbar
     * @param action
     * @param isTrue
     */
    @Override
    protected void setToolBar(ActionBar action,boolean isTrue) {

    }

    private IUmengRegisterCallback mRegist=new IUmengRegisterCallback() {
        @Override
        public void onRegistered(String s) {

        }
    };
    private void initTask() {
        final boolean isFirstInto= SPCache.getBoolean(
                BaseConsts.SharePreference.IS_FIRST_INTO, true);
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                if (isFirstInto) {
                    startActivity(SplashActivity.this, GuideActivity.class);
                    finish();
                }else {
                    String provience = SPCache.getString("provience","");
                    TApplication.user_id = SPCache.getString(BaseConsts.SharePreference.USER_ID,"");
                    TApplication.user_name = SPCache.getString(BaseConsts.SharePreference.USER_NAME, "");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(TApplication.user_id)){
                                showToast("登陆成功");
                            }
                        }
                    });
                    if (provience == null || "".equals(provience)) {
                        startActivity(SplashActivity.this, WelActivity.class);
                        finish();
                    } else {
                        startActivity(SplashActivity.this, MainActivity.class, "provience", provience);
                        finish();
                    }
                }
            }
        };
        timer.schedule(task,1500);
    }
}
