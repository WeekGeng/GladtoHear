package com.yieryi.gladtohear.activities;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.constans.LoginConsts;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.tools.MD5Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    //输入框
    private EditText login_ed_phone,login_ed_pass;
    //登录 注册 忘记密码
    private TextView login_tv_login,login_tv_forget_pass,login_tv_regist;
    // 第三方登录 qq,微信,新浪
    private TextView login_tv_qq,login_tv_weixin,login_tv_sina;

    private Map<String,String> paramas=new HashMap<>();

    String phone_number,password;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListeners();
        initToolBar();
    }

    private void initToolBar() {
            toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("登录");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onResponse(final Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (response.isSuccessful()){
                                            try {
                                                Toast.makeText(LoginActivity.this,"请求结果：="+response.body().string(),Toast.LENGTH_LONG).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
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

                break;
            case R.id.login_tv_weixin:

                break;
            case R.id.login_tv_sina:

                break;
        }
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
}
