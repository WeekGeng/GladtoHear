package com.yieryi.gladtohear.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.base.TApplication;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.tools.sp.SPCache;

public class MyAccuntActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_check_out_login;

    @Override
    public int getLayout() {
        return R.layout.activity_my_accunt;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        tv_check_out_login = (TextView) findViewById(R.id.tv_check_out_login);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("我的账号");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_check_out_login:
                TApplication.user_id="";
                TApplication.user_name=null;
                SPCache.putString(BaseConsts.SharePreference.USER_ID, "");
                SPCache.putString(BaseConsts.SharePreference.USER_NAME, "");
                SPCache.putString(BaseConsts.SharePreference.USER_SCORE, "");
                SPCache.putString(BaseConsts.SharePreference.USER_LITTLE_IMAGE, "");
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
