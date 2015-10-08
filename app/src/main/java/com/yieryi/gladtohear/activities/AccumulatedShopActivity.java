package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.fragment.main.accumulateshop.BeautifulPresentFragment;
import com.yieryi.gladtohear.fragment.main.accumulateshop.DataAddFragment;
import com.yieryi.gladtohear.fragment.main.accumulateshop.ScoreSortFragment;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.view.SwipeRefreshView;
public class AccumulatedShopActivity extends BaseActivity implements View.OnClickListener{
    private final String TAG=AccumulatedShopActivity.class.getSimpleName();
    private SwipeRefreshView accumulate_shop_refresh;
    private TextView accumulated_shop_prefect_tv;
    private TextView accumulated_shop_prefect_time_tv;
    private TextView accumulated_shop_prefect_order_tv;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment beautifulPresentFragment,dataAddFragment,scoreSortFragment;
    private int whichSel;

    @Override
    public int getLayout() {
        return R.layout.activity_accumulated_shop;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        steListeners();
        if (!isNetworkConnected(this)) {
            OkHttp.cancleAccumulateNetWork(new String[]{TAG});
            showToast("网络无链接");
        }
    }

    private void steListeners() {
        accumulated_shop_prefect_tv.setOnClickListener(this);
        accumulated_shop_prefect_time_tv.setOnClickListener(this);
        accumulated_shop_prefect_order_tv.setOnClickListener(this);
    }

    private void initView() {
        manager = getSupportFragmentManager();
        if (isNetworkConnected(this)){
            beautifulPresentFragment = new BeautifulPresentFragment();
            setFragmentChose(beautifulPresentFragment);
        }
        accumulate_shop_refresh = (SwipeRefreshView) findViewById(R.id.accumulate_shop_refresh);
        accumulated_shop_prefect_tv = (TextView) findViewById(R.id.accumulated_shop_prefect_tv);
        accumulated_shop_prefect_time_tv = (TextView) findViewById(R.id.accumulated_shop_prefect_time_tv);
        accumulated_shop_prefect_order_tv = (TextView) findViewById(R.id.accumulated_shop_prefect_order_tv);

        accumulated_shop_prefect_tv.setTextColor(getResources().getColor(R.color.color_white));
        accumulated_shop_prefect_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
    }
    /**
     * 设置Fragment
     * @param fragment
     */
    private void setFragmentChose(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.accumulate_shop_content,fragment);
        transaction.commit();
    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("积分商城");
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
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.accumulated_shop_prefect_tv:

                if (whichSel!=0) {
                    if (isNetworkConnected(AccumulatedShopActivity.this)) {
                        OkHttp.cancleMainNetWork(new String[]{"BeautifulPresentFragment"});
                        beautifulPresentFragment = new BeautifulPresentFragment();
                        setFragmentChose(beautifulPresentFragment);
                    }else {
                        showToast("网络无连接");
                    }
                    accumulated_shop_prefect_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    accumulated_shop_prefect_tv.setTextColor(getResources().getColor(R.color.color_white));

                    accumulated_shop_prefect_time_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    accumulated_shop_prefect_time_tv.setTextColor(getResources().getColor(R.color.text_half_black_color));

                    accumulated_shop_prefect_order_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    accumulated_shop_prefect_order_tv.setTextColor(getResources().getColor(R.color.text_half_black_color));
                    whichSel = 0;
                }
                break;
            case R.id.accumulated_shop_prefect_time_tv:
                if (whichSel!=1) {
                    if (isNetworkConnected(AccumulatedShopActivity.this)) {
                        OkHttp.cancleMainNetWork(new String[]{"DataAddFragment"});
                        dataAddFragment = new DataAddFragment();
                        setFragmentChose(dataAddFragment);
                    }else {
                        showToast("网络无连接");
                    }
                    accumulated_shop_prefect_time_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    accumulated_shop_prefect_time_tv.setTextColor(getResources().getColor(R.color.color_white));

                    accumulated_shop_prefect_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    accumulated_shop_prefect_tv.setTextColor(getResources().getColor(R.color.text_half_black_color));

                    accumulated_shop_prefect_order_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    accumulated_shop_prefect_order_tv.setTextColor(getResources().getColor(R.color.text_half_black_color));
                    whichSel = 1;
                }
                break;
            case R.id.accumulated_shop_prefect_order_tv:
                if (whichSel!=2) {
                    if (isNetworkConnected(AccumulatedShopActivity.this)) {
                        OkHttp.cancleMainNetWork(new String[]{"ScoreSortFragment"});
                        scoreSortFragment = new ScoreSortFragment();
                        setFragmentChose(scoreSortFragment);
                    }else {
                        showToast("网络无连接");
                    }
                    accumulated_shop_prefect_order_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    accumulated_shop_prefect_order_tv.setTextColor(getResources().getColor(R.color.color_white));

                    accumulated_shop_prefect_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    accumulated_shop_prefect_tv.setTextColor(getResources().getColor(R.color.text_half_black_color));

                    accumulated_shop_prefect_time_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    accumulated_shop_prefect_time_tv.setTextColor(getResources().getColor(R.color.text_half_black_color));
                    whichSel = 2;
                }
                break;
        }
    }
}
