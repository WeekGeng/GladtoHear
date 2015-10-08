package com.yieryi.gladtohear.activities;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;

public class ChatListActivity extends BaseActivity {
    private RecyclerView chat_recycle;
    @Override
    public int getLayout() {
        return R.layout.activity_chat_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {

    }
}
