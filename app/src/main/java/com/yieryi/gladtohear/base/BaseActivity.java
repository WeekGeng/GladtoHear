package com.yieryi.gladtohear.base;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.broadcast.ExitBroadCast;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.tools.TDevide;
import com.yieryi.gladtohear.tools.ToastControl;
import com.yieryi.gladtohear.tools.log.Log;

/**
 * 基类的activity.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,ToastControl,DialogControl{
    public final String TAG=BaseActivity.class.getSimpleName();
    private ActionBar actionBar;
    private PushAgent mPushAgent;
    private ExitBroadCast exitBroadCast;
    private static String lastToast=null;
    private static long lastToastTime=0;
    private boolean _isVisible=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initParameters();
        initToolBar();
        initExit();
        init(savedInstanceState);
    }
    public ActionBar getCustomBar() {
        return actionBar != null ? actionBar : null;
    }
    /**
     * 调用此方法可以初始化布局文件
     * @return
     */
    public abstract int getLayout();
    /**
     * 初始化参数的方法
     * @param savedInstanceState
     */
    public abstract void init(Bundle savedInstanceState);

    /**
     * 初始化退出广播
     */
    public void initExit() {
        exitBroadCast=new ExitBroadCast(this);
        IntentFilter filter=new IntentFilter(BaseConsts.INTENT_ACTION_EXIT_APP);
        registerReceiver(exitBroadCast, filter);
    }
    /**
     * 初始化固定参数
     */
    private void initParameters() {
        //保存屏幕尺寸信息
        TDevide.saveDisplaySize(this);
        mPushAgent=PushAgent.getInstance(this);
        mPushAgent.onAppStart();
    }
    /**
     * 初始化toolbar
     */
    protected void initToolBar(){
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true); //设置返回键可用
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
            }
        } catch (NullPointerException e) {
            Log.i("Toolbar", "toolbar = null");
        }
    }
    @Override
    public void onClick(View view) {

    }
    public void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    @Override
    public void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, 17);
    }

    @Override
    public void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    @Override
    public void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, 17);
    }

    @Override
    public void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    @Override
    public void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, 17);
    }

    @Override
    public void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, 17, args);
    }

    @Override
    public void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, 17);
    }

    @Override
    public void showToast(int message, int duration, int icon,
                          int gravity) {
        showToast(TApplication.getContext().getString(message), duration, icon, gravity);
    }

    @Override
    public void showToast(int message, int duration, int icon,
                          int gravity, Object... args) {
        showToast(TApplication.getContext().getString(message, args), duration, icon, gravity);
    }

    /**
     * 弹出框toast
     * @param message
     * @param duration
     * @param icon
     * @param gravity
     */
    @Override
    public void showToast(String message, int duration, int icon,
                          int gravity) {
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(lastToast)
                    || Math.abs(time - lastToastTime) > 2000) {
                View view = LayoutInflater.from(TApplication.getContext()).inflate(
                        R.layout.toast_view, null);
                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
                if (icon != 0) {
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setImageResource(icon);
                    (view.findViewById(R.id.icon_iv))
                            .setVisibility(View.VISIBLE);
                }
                Toast toast = new Toast(this);
                toast.setView(view);
                toast.setGravity(Gravity.BOTTOM | gravity, 0, TDevide.dip2px(84, this));
                toast.setDuration(duration);
                toast.show();
                lastToast = message;
                lastToastTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        _isVisible = true;
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        _isVisible=false;
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(exitBroadCast);
        super.onDestroy();
    }
}
