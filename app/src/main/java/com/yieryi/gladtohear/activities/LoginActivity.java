package com.yieryi.gladtohear.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.constans.LoginConsts;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.tools.MD5Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    //输入框
    private EditText login_ed_phone,login_ed_pass;
    //登录 注册 忘记密码
    private TextView login_tv_login,login_tv_forget_pass,login_tv_regist;
    // 第三方登录 qq,微信,新浪
    private TextView login_tv_qq,login_tv_weixin,login_tv_sina;

    private Map<String,String> paramas=new HashMap<>();

    String phone_number,password;
    private Toolbar toolbar;
    private final String TAG=LoginActivity.class.getSimpleName();

    private Tencent mTencent ;
    private String type;
    private String token,openId,expires;
    private String username;
    private MyIUListener listener;
    private Handler handlerInstance=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handleLogin(openId,username);
        }
    };
    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListeners();
    }

    @Override
    protected void setToolBar(ActionBar action,boolean isTrue) {
        action.setTitle("登录");
        action.setHomeButtonEnabled(isTrue);
    }
    private void setListeners() {
        login_tv_login.setOnClickListener(this);
        login_tv_forget_pass.setOnClickListener(this);
        login_tv_regist.setOnClickListener(this);
        login_tv_qq.setOnClickListener(this);
        login_tv_weixin.setOnClickListener(this);
        login_tv_sina.setOnClickListener(this);
    }

    private void initView() {
        login_ed_phone=(EditText)findViewById(R.id.login_ed_phone);
        login_ed_pass=(EditText)findViewById(R.id.login_ed_pass);
        login_tv_login=(TextView)findViewById(R.id.login_tv_login);
        login_tv_forget_pass=(TextView)findViewById(R.id.login_tv_forget_pass);
        login_tv_regist=(TextView)findViewById(R.id.login_tv_regist);
        login_tv_qq=(TextView)findViewById(R.id.login_tv_qq);
        login_tv_weixin=(TextView)findViewById(R.id.login_tv_weixin);
        login_tv_sina=(TextView)findViewById(R.id.login_tv_sina);
        login_tv_forget_pass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        login_tv_regist.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTencent = Tencent.createInstance(LoginConsts.Account.QQLogin.QQ_APP_KEY, this);
        listener=new MyIUListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_tv_login:
                switch (foxMessage()){
                    case 0:
                        paramas.put(BaseConsts.APP, LoginConsts.Account.APP_ADDRESS);
                        paramas.put(BaseConsts.CLASS, LoginConsts.Account.Login.params_class);
                        paramas.put(BaseConsts.username, phone_number);
                        paramas.put(BaseConsts.password, password);
                        paramas.put("device_no", "android");
                        String sign = "";
                        if (paramas.size() != 0) {
                            sign = MD5Utils.MD5(paramas.get(BaseConsts.APP) + paramas.get(BaseConsts.CLASS) + BaseConsts.APP_KEY);
                        }
                        paramas.put(BaseConsts.SIGN, sign);
                        OkHttp.asyncPost(BaseConsts.BASE_URL, paramas, new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this,"服务器异常",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            @Override
                            public void onResponse(final Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Gson gson=new Gson();
                                        if (response.isSuccessful()){
                                            try {
                                                String state=response.body().string();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            showToast("登录成功");
                                            startActivity(LoginActivity.this,MainActivity.class);
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    case 1:
                        Toast.makeText(LoginActivity.this,"请检查手机号格式。",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(LoginActivity.this,"密码格式错误。",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(LoginActivity.this,"账号或密码有误。",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(LoginActivity.this,"账号或密码有误。",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(LoginActivity.this,"手机号不能为空。",Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case R.id.login_tv_forget_pass:

                break;
            case R.id.login_tv_regist:

                break;
            case R.id.login_tv_qq:
                mTencent.login(this, "all",listener);
                break;
            case R.id.login_tv_weixin:

                break;
            case R.id.login_tv_sina:

                break;
        }
    }

    /**
     * 登录监听接口
     */
    class MyIUListener implements IUiListener {

        JSONObject json;
        @Override
        public void onComplete(Object o) {
            if (null == o) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                json=new JSONObject(o.toString());
                openId = ((JSONObject) o).getString("openid");
                token = json.getString(Constants.PARAM_ACCESS_TOKEN);
                Log.e("login_info",token);
                expires = json.getString(Constants.PARAM_EXPIRES_IN);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
            UserInfo info = new UserInfo(LoginActivity.this, mTencent.getQQToken());
            info.getUserInfo(new IUiListener() {
                @Override
                public void onError(UiError o) {
                    Toast.makeText(LoginActivity.this, "获取用户信息失败：" + o.errorDetail, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onComplete(Object o) {
                    try {
                        username = new JSONObject(o.toString()).getString("nickname");
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = type;
                        handlerInstance.sendMessage(msg);
                        Toast.makeText(LoginActivity.this,"username"+username,Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancel() {
                    Toast.makeText(LoginActivity.this, "授权取消",  Toast.LENGTH_SHORT).show();
                }
            });

        }
        @Override
        public void onError(UiError o) {
            Toast.makeText(LoginActivity.this, "授权错误：" + o.errorDetail, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消",  Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data,new MyIUListener());
    }

    /**
     * 登录时的判断
     * @return
     */
    private int foxMessage() {
        int format=0;
        phone_number=login_ed_phone.getText().toString().trim();
        password=login_ed_pass.getText().toString().trim();
        if (TextUtils.isEmpty(phone_number)){
            format=LoginConsts.PHONE_EMPTY;
            return format;
        }
        if (TextUtils.isEmpty(password)){
            format=LoginConsts.PASS_EMPTY;
            return format;
        }
//        if (!isMobileNo(phone_number)){
//            format= LoginConsts.PHONE_FORMAT_ERROR;
//            return format;
//        }
        return format;
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
     * 第三方平台登录
     *
     * @param uid
     * @param userName
     */
    private void handleLogin(String uid, String userName) {
        Map<String, String> params = new HashMap<>();
        params.put("openId", uid);
        params.put("nick", userName);
        OkHttp.asyncPost(BaseConsts.BASE_URL, params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}
