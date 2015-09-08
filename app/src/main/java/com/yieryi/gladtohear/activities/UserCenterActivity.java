package com.yieryi.gladtohear.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;

public class UserCenterActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_user_center;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("个人中心");
        action.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
