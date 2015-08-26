package com.yieryi.gladtohear.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.MarketSelAdapter;
import java.util.ArrayList;
import java.util.List;

public class MarketSelActivity extends AppCompatActivity implements MarketSelAdapter.OnItemClickListener{
    private RecyclerView recycle_market;
    private String[] markets;
    private String[] otherMarkets;
    private RecyclerView.Adapter adapter;
    boolean isSel;
    private List<String> list;
    private TextView tv_next;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_sel);
        initView();
        initToolbar();
        setData();
        setListener();
    }

    private void initToolbar() {
        try {
            toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("超市选择");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {

        }
    }

    private void setListener() {
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String market:list) {
                    Log.i("market",market+"aaaaaaaa");
                }
                if (list.isEmpty()) {
                    Toast.makeText(MarketSelActivity.this, "请先选择超市", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MarketSelActivity.this, CatlogsSelActivity.class);
                startActivity(intent);
            }
        });
    }
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

    private void initView() {
        recycle_market= (RecyclerView) findViewById(R.id.recycle_market);
        tv_next=(TextView)findViewById(R.id.tv_next);
        list=new ArrayList<>();
    }
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
