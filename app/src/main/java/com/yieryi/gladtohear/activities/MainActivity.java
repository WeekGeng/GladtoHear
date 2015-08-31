package com.yieryi.gladtohear.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.FunctionAdapter;
import com.yieryi.gladtohear.bean.FunctionItem;
import com.yieryi.gladtohear.overridge.MyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FunctionAdapter.OnItemClickListener{
    //帮你算按钮
    private TextView help;
    private List<FunctionItem> functionItems;
    private FunctionItem item;
    private RecyclerView main_function_recyc;
    private FunctionAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private TextView main_local_tv;
    private String provience;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        provience=intent.getStringExtra("provience");
        getDataFunction();
        initView();
        setListeners();
    }

    private void getDataFunction() {
        functionItems=new ArrayList<>();
        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.help_calculate));
        item.setTitle("帮你算");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.help_check));
        item.setTitle("帮你查");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.woman_chat));
        item.setTitle("主妇论坛");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.integralmall));
        item.setTitle("积分商城");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.persional_space));
        item.setTitle("个人空间");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.informationsummary));
        item.setTitle("资讯汇总");
        functionItems.add(item);
    }

    /**
     * 设置监听器
     */
    private void setListeners() {

    }

    /**
     * 初始化界面
     */
    private void initView() {

        main_local_tv=(TextView)findViewById(R.id.main_local_tv);
        main_local_tv.setText(provience);

        main_function_recyc= (RecyclerView) findViewById(R.id.main_function_recyc);
        adapter=new FunctionAdapter(functionItems,this);
        gridLayoutManager=new MyGridLayoutManager(this,3);
        gridLayoutManager.setOrientation(MyGridLayoutManager.VERTICAL);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        main_function_recyc.setLayoutManager(gridLayoutManager);
        main_function_recyc.setAdapter(adapter);
    }

    @Override
    public void onClick(View view, int position) {
        switch (position){
            case 0:
                Intent intent=new Intent(MainActivity.this,MarketSelActivity.class);
                startActivity(intent);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }
}
