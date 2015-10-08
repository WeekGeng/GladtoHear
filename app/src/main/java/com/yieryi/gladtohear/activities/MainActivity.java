package com.yieryi.gladtohear.activities;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.TApplication;
import com.yieryi.gladtohear.fragment.main.Main;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.residemenu.ResideMenu;
import com.yieryi.gladtohear.residemenu.ResideMenuItem;

public class MainActivity extends FragmentActivity{
    //帮你算按钮
    private TextView main_local_tv;
    private String provience;
    private final String TAG=MainActivity.class.getSimpleName();
    private ImageView user_center;
    private ResideMenu resideMenu;
    private ResideMenuItem item_score;
    private ResideMenuItem item_pinglun;
    private ResideMenuItem item_zhanghao;
    private ResideMenuItem item_setting;
    private TextView user_center_login_username;
    private TextView user_center_regist_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Log.e("intent", intent.toString());
        provience=intent.getStringExtra("provience");
        initView();
        setListeners();
        if( savedInstanceState == null )
            changeFragment(new Main());
        if (!isNetworkConnected(this)) {
            OkHttp.cancleMainNetWork(new String[]{TAG});
            Toast.makeText(MainActivity.this, "网络无链接", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置fragment
     * @param targetFragment
     */
    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, targetFragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    /**
     * 检查网络链接状态
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        main_local_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WelActivity.class);
                intent.putExtra("setLocation", true);
                startActivityForResult(intent, 200);
            }
        });
        user_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
        item_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        item_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        item_zhanghao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(TApplication.user_id)) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent,500);
                }else {
                    Intent intent = new Intent(MainActivity.this, MyAccuntActivity.class);
                    startActivityForResult(intent,500);
                }
            }
        });
        item_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        user_center_login_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent,300);
            }
        });
        user_center_regist_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistActivity.class);
                startActivityForResult(intent,400);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode == 200) {
                if (data == null) {
                    Toast.makeText(MainActivity.this, "定位信息获取失败", Toast.LENGTH_SHORT).show();
                    main_local_tv.setText("上海");
                }else {
                    String provience_result = data.getStringExtra("provience");
                    main_local_tv.setText(provience_result);
                }
            }else if (requestCode == 300||requestCode==400||requestCode==500) {
                if ("".equals(TApplication.user_id)) {
                    user_center_login_username.setText("登陆");
                    user_center_regist_score.setText("注册");
                }else {
                    if (TApplication.user != null) {
                        user_center_login_username.setText(TApplication.user.getNickname());
                    } else {
                        user_center_login_username.setText(TApplication.user_name);
                    }
                }
            }
        }
    }

    /**
     * 初始化界面
     */
    private void initView() {
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.mipmap.ditu_right);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);
        item_score = new ResideMenuItem(this, R.mipmap.icon_jifen, "积分");
        item_pinglun = new ResideMenuItem(this, R.mipmap.icon_jifen, "评论");
        item_zhanghao = new ResideMenuItem(this, R.mipmap.icon_jifen, "账号");
        item_setting = new ResideMenuItem(this, R.mipmap.icon_jifen, "设置");
        resideMenu.addMenuItem(item_score, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(item_pinglun, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(item_zhanghao, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(item_setting, ResideMenu.DIRECTION_RIGHT);

        user_center_login_username = (TextView) findViewById(R.id.user_center_login_username);
        user_center_regist_score = (TextView) findViewById(R.id.user_center_regist_score);

        if ("".equals(TApplication.user_id)||TApplication.user_id==null) {
            Log.e("user_id","user_id1="+TApplication.user_id);
            user_center_login_username.setText("登陆");
            user_center_regist_score.setText("注册");
        }else {
            Log.e("user_id", "user_id2=" + TApplication.user_id);
            if (TApplication.user != null) {
                user_center_login_username.setText(TApplication.user.getNickname());
            } else {
                user_center_login_username.setText(TApplication.user_name);
            }
        }
        user_center = (ImageView) findViewById(R.id.user_center);
        main_local_tv = (TextView) findViewById(R.id.main_local_tv);
        main_local_tv.setText(provience);
    }
}
