package com.yieryi.gladtohear.activities;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.listener.RequestListener;
import java.io.IOException;

public class UserCenterActivity extends BaseActivity implements View.OnClickListener,RequestListener{
    private RecyclerView function;
    private ImageView headImageView;
    @Override
    public int getLayout() {
        return R.layout.activity_user_center;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {

    }

    private void SearchScore() {

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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
}
