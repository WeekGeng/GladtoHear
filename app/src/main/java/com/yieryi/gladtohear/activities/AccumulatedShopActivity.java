package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.accumulate.AccumulateShopAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.bean.accumulate.Product;
import com.yieryi.gladtohear.bean.accumulate.Root;
import com.yieryi.gladtohear.biz.accumulate.AccumulateShopBiz;
import com.yieryi.gladtohear.listener.OnRecycItemClickListener;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.view.SwipeRefreshView;
import java.io.IOException;
import java.util.List;
public class AccumulatedShopActivity extends BaseActivity implements RequestListener,OnRecycItemClickListener,View.OnClickListener{
    private static  final String TAG=AccumulatedShopActivity.class.getSimpleName();
    private RecyclerView accumulate_shop_recycle;
    private SwipeRefreshView accumulate_shop_refresh;
    private TextView accumulated_shop_prefect_tv;
    private TextView accumulated_shop_prefect_time_tv;
    private TextView accumulated_shop_prefect_order_tv;
    private AccumulateShopAdapter adapter;
    private AccumulateShopBiz biz;
    @Override
    public int getLayout() {
        return R.layout.activity_accumulated_shop;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        steListeners();
        setData();
    }

    private void setData() {
        biz=new AccumulateShopBiz();
        biz.getProductsList(38,1,"desc",this);
    }

    private void steListeners() {
        accumulated_shop_prefect_tv.setOnClickListener(this);
        accumulated_shop_prefect_time_tv.setOnClickListener(this);
        accumulated_shop_prefect_order_tv.setOnClickListener(this);
    }

    private void initView() {
        accumulate_shop_recycle=(RecyclerView)findViewById(R.id.accumulate_shop_recycle);
        accumulate_shop_refresh=(SwipeRefreshView)findViewById(R.id.accumulate_shop_refresh);
        accumulated_shop_prefect_tv=(TextView)findViewById(R.id.accumulated_shop_prefect_tv);
        accumulated_shop_prefect_time_tv=(TextView)findViewById(R.id.accumulated_shop_prefect_time_tv);
        accumulated_shop_prefect_order_tv=(TextView)findViewById(R.id.accumulated_shop_prefect_order_tv);
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
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson=new Gson();
            try {
                String json = response.body().string();
                Root root=gson.fromJson(json,Root.class);
                if (root.getStatus()==0){
                    List<Product> list=root.getData().getProducts();
                    Log.e("producu", list.get(0).toString());
                    adapter=new AccumulateShopAdapter(list);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            accumulate_shop_recycle.setAdapter(adapter);
                            GridLayoutManager manager=new GridLayoutManager(AccumulatedShopActivity.this,2);

                            manager.setOrientation(GridLayoutManager.VERTICAL);
                            accumulate_shop_recycle.setLayoutManager(manager);
                        }
                    });
                }else {
                    showToast("请求出错");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast("网络异常，请检查网络链接。");
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(AccumulatedShopActivity.this,AccumulateShopDetailActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.accumulated_shop_prefect_tv:
                biz.getProductsList(38,1,"desc",this);
                accumulated_shop_prefect_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                accumulated_shop_prefect_time_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                accumulated_shop_prefect_order_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                break;
            case R.id.accumulated_shop_prefect_time_tv:
                biz.getProductsList(38,2,"desc",this);
                accumulated_shop_prefect_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                accumulated_shop_prefect_time_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                accumulated_shop_prefect_order_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                break;
            case R.id.accumulated_shop_prefect_order_tv:
                biz.getProductsList(38,3,"desc",this);
                accumulated_shop_prefect_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                accumulated_shop_prefect_time_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                accumulated_shop_prefect_order_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                break;
        }
    }
}
