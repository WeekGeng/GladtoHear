package com.yieryi.gladtohear.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.base.TApplication;
import com.yieryi.gladtohear.bean.regist.code.Root;
import com.yieryi.gladtohear.bean.regist.code.regist.Roots;
import com.yieryi.gladtohear.biz.regist.GetCodeBiz;
import com.yieryi.gladtohear.biz.regist.regist.RegistBiz;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.listener.RegistResponse;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.tools.sp.SPCache;
import com.yieryi.gladtohear.view.LoadingDialog;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistActivity extends BaseActivity implements RequestListener,RegistResponse,View.OnClickListener{
    private final String TAG=RegistActivity.class.getSimpleName();
    private EditText ed_username;
    private EditText ed_password;
    private EditText ed_password_again;
    private EditText ed_phone_number;
    private EditText ed_code;
    private TextView tv_getcode;
    private TextView tv_regist;
    private String username;
    private String password;
    private LoadingDialog dialog;
    @Override
    public int getLayout() {
        return R.layout.activity_regist;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListener();
    }

    private void setListener() {
        tv_getcode.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
    }

    /**
     * 检查手机号码输入状态
     * @param phone_number
     * @return
     */
    private boolean checkPhone(String phone_number) {
        if (TextUtils.isEmpty(phone_number)){
            showToast("手机号码不能为空");
            return false;
        }
        if (!isMobileNo(phone_number)){
            showToast("请检查手机格式是否正确");
            return false;
        }
        return true;
    }
    /**
     * 检查手机号是否正确
     * @param phone
     * @return
     */
    public static boolean isMobileNo(String phone) {
        String match = "^((13|15|18|17|14)\\d{9})|147\\d{8}$";
        Pattern pattern = Pattern.compile(match);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 初始化view
     */
    private void initView() {
        ed_username = (EditText) findViewById(R.id.regist_username_ed);
        ed_password = (EditText) findViewById(R.id.regist_password_ed);
        ed_password_again = (EditText) findViewById(R.id.regist_again_password_ed);
        ed_phone_number = (EditText) findViewById(R.id.regist_photo_number_ed);
        ed_code = (EditText) findViewById(R.id.regist_code_ed);
        tv_getcode=(TextView)findViewById(R.id.regist_getcode_tv);
        tv_regist=(TextView)findViewById(R.id.regist_regist_tv);
        if (dialog==null) {
            dialog = new LoadingDialog(this, R.style.dialog);
        }
    }

    /**
     * 设置toolbar
     * @param action
     * @param isTrue
     */
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("注册");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_regist, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.regist_login:
                startActivity(RegistActivity.this,LoginActivity.class);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 注册的返回
     * @param response
     */
    @Override
    public void onResponseSuccess(Response response) {
        if (response.isSuccessful()){
            Gson  gson=new Gson();
            try {
                String json = response.body().string();
                Roots roots = gson.fromJson(json, Roots.class);
                if (roots.getStatus()==OkHttp.NET_STATE){
                    TApplication.token = roots.getData().getPassword();
                    // 保存SP
                    SPCache.putString(BaseConsts.SharePreference.USER_TOKEN,roots.getData().getPassword());
                    SPCache.putString(BaseConsts.SharePreference.USER_AUTHORITY,roots.getData().getUsername());
                    finish();
                }else{
                    showToast("注册失败，服务器在维护，请稍后尝试。");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailueRequess(Request request, IOException e) {
        showToast("网络链接异常");
    }

    /**
     * 验证码的返回
     * @param response
     */
    @Override
    public void onResponse(Response response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        if (response.isSuccessful()){
            Gson gson=new Gson();
            try {
                String json=response.body().string();
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus()!= OkHttp.NET_STATE){
                    showToast("验证码获取失败");
                }else {
                    showToast("验证码已发送,请注意查收");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {
        showToast("网络链接异常");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regist_getcode_tv:
                String phone_number = ed_phone_number.getText().toString().trim();
                if (!checkPhone(phone_number)) return;
                GetCodeBiz biz=new GetCodeBiz();
                biz.getCode(phone_number,RegistActivity.this);
                break;
            case R.id.regist_regist_tv:
                username=ed_username.getText().toString().trim();
                password=ed_password.getText().toString().trim();
                final String again_pass=ed_password_again.getText().toString().trim();
                final String tel_num=ed_phone_number.getText().toString().trim();
                final String code=ed_code.getText().toString().trim();
                if (checkUserState(username,password,again_pass,tel_num,code)){
                    dialog.show();
                    RegistBiz registBiz=new RegistBiz();
                    registBiz.regist(username,password,again_pass,tel_num,code,this);
                }
                break;
        }
    }

    /**
     * 确定所有的输入是否完整
     * @param username
     * @param password
     * @param again_pass
     * @param tel_num
     * @param code
     * @return
     */
    private boolean checkUserState(String username, String password, String again_pass, String tel_num, String code) {
        if (TextUtils.isEmpty(username)){
            showToast("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(password)){
            showToast("密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(again_pass)){
            showToast("密码不能为空");
            return false;
        }
        if (!again_pass.equals(password)){
            showToast("密码不一致请检查");
            return false;
        }
        if (TextUtils.isEmpty(username)){
            showToast("用户名不能为空");
            return false;
        }
        if (!checkPhone(tel_num)){
            return false;
        }
        if (TextUtils.isEmpty(code)){
            showToast("验证码不能为空");
            return false;
        }
        return true;
    }
}
