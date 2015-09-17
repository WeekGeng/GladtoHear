package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.MarketSelAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;
/**
 * 把你算超市选择界面
 */
public class MarketSelActivity extends BaseActivity implements MarketSelAdapter.OnItemClickListener{
    private RecyclerView recycle_market;
    private String[] markets;
    private String[] otherMarkets;
    private RecyclerView.Adapter adapter;
    boolean isSel;
    private List<String> list;
    private TextView tv_next;

    /**
     *初始化加载view
     * @return
     */
    @Override
    public int getLayout() {
        return R.layout.activity_market_sel;
    }

    /**
     * 初始化参数
     * @param savedInstanceState
     */
    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setData();
        setListener();
    }

    /**
     * 设置toolbar
     * @param action
     * @param isTrue
     */
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("超市选择");
        action.setHomeButtonEnabled(isTrue);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String market:list) {
                    Log.i("market",market+"aaaaaaaa");
                }
                if (list.isEmpty()) {
                    showToast("请先选择超市");
                    return;
                }
                startActivity(MarketSelActivity.this, CatlogsSelActivity.class);
            }
        });
    }

    /**
     * 设置参数 请求应该来自网络
     */
    private void setData() {
        markets=getResources().getStringArray(R.array.Market_name);
        if (markets.length%2!=0){
            otherMarkets=new String[markets.length+1];
            otherMarkets[markets.length]="";
            System.arraycopy(markets,0,otherMarkets,0,markets.length);
            adapter=new MarketSelAdapter(otherMarkets,this);
        }else {
            adapter=new MarketSelAdapter(markets,this);
        }
        GridLayoutManager manager=new GridLayoutManager(this,2);
        recycle_market.setLayoutManager(manager);
        recycle_market.setAdapter(adapter);

    }

    /**
     * 初始化参数
     */
    private void initView() {
        recycle_market= (RecyclerView) findViewById(R.id.recycle_market);
        tv_next=(TextView)findViewById(R.id.tv_next);
        list=new ArrayList<>();
    }

    /**
     * recycleview的参数回传
     * @param v
     * @param positon
     */
    @Override
    public void onClick(View v,int positon) {
        if (isSel){
            ((ImageView)v).setImageResource(R.mipmap.market_unsel);
            list.remove(markets[positon]);
            isSel=false;
        }else {
            ((ImageView)v).setImageResource(R.mipmap.marcket_sel);
            list.add(markets[positon]);
            isSel=true;
        }
        for (String market:list) {
            Log.i("market",market);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
