package com.yieryi.gladtohear.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.ShopingCarAdapter;

public class ProductsListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShopingCarAdapter adapter;
    private LinearLayoutManager manager;
    private TextView shopping_car_tv;
    private BadgeView badgeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        initView();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        badgeView.setTargetView(shopping_car_tv);
        badgeView.setBadgeCount(3);

    }

    private void initView() {
        recyclerView=(RecyclerView)findViewById(R.id.recycle_market);
        adapter=new ShopingCarAdapter(this);
        manager=new LinearLayoutManager(this);
        shopping_car_tv=(TextView)findViewById(R.id.shopping_car_tv);
        badgeView=new BadgeView(this);
    }
}
