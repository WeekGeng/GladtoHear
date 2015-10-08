package com.yieryi.gladtohear.activities;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;

public class ListRecordDetailActivity extends BaseActivity {
    @Override
    public int getLayout() {
        return R.layout.activity_list_record_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("订单详细");
        action.setHomeButtonEnabled(isTrue);
    }
}
