package com.yieryi.gladtohear.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;

public class HelpCheckGoodsDetailActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_help_check_goods_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("资讯详情");
        action.setHomeButtonEnabled(isTrue);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
