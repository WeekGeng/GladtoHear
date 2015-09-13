package com.yieryi.gladtohear.activities.Jpush;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;

public class JpushActivity extends BaseActivity {
    @Override
    public int getLayout() {
        return R.layout.activity_jpush;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("我是推送消息");
        action.setHomeButtonEnabled(isTrue);
    }
}
