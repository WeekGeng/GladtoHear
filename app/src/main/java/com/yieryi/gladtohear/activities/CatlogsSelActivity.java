package com.yieryi.gladtohear.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.ExpandableAdapter;
import com.yieryi.gladtohear.adapter.catlogs.ChildItem;
import com.yieryi.gladtohear.adapter.catlogs.GroupItem;
import com.yieryi.gladtohear.view.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class CatlogsSelActivity extends AppCompatActivity implements ExpandableAdapter.CallBack,ExpandableListView.OnChildClickListener{
    private Toolbar toolbar;
    private TextView tv_next;
    private List<GroupItem> groupItemList;
    private GroupItem groupItem;
    private ChildItem childItem;
    private List<ChildItem> childItemList;
    private ExpandableAdapter adapter;
    private AnimatedExpandableListView expandableListView;
    private boolean isSel;
    private List<String> record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catlogs_sel);
        initView();
        initToolbar();
        setData();
        setListener();
    }

    private void setData() {
        String[] catlogs=getResources().getStringArray(R.array.Catalogs);
        String[] food_drink_catalog=getResources().getStringArray(R.array.food_drink_catalog);
        String[] daily_necessities_catalog=getResources().getStringArray(R.array.daily_necessities_catalog);
        String[] mami_baby_catalog=getResources().getStringArray(R.array.mami_baby_catalog);
        String[] fresh_food_catalog=getResources().getStringArray(R.array.fresh_food_catalog);
        String[] aesthetic_nursing_catalog=getResources().getStringArray(R.array.aesthetic_nursing_catalog);
        String[] washing_nursing_catalog=getResources().getStringArray(R.array.washing_nursing_catalog);
        String[] baby_toys_catalog=getResources().getStringArray(R.array.baby_toys_catalog);
        groupItemList=new ArrayList<>();
        /**
         * 1
         */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<food_drink_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(food_drink_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[0]);
        groupItemList.add(groupItem);
/**
 * 2
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<daily_necessities_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(daily_necessities_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[1]);
        groupItemList.add(groupItem);
/**
 * 3
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<mami_baby_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(mami_baby_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[2]);
        groupItemList.add(groupItem);
/**
 * 4
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<fresh_food_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(fresh_food_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[3]);
        groupItemList.add(groupItem);
/**
 * 5
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<aesthetic_nursing_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(aesthetic_nursing_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[4]);
        groupItemList.add(groupItem);
/**
 * 6
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<washing_nursing_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(washing_nursing_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[5]);
        groupItemList.add(groupItem);
/**
 * 7
 */
        groupItem=new GroupItem();
        childItemList=new ArrayList<>();
        for (int i=0;i<baby_toys_catalog.length;i++){
            childItem=new ChildItem();
            childItem.setCatlogName(baby_toys_catalog[i]);
            childItem.setIsSel(false);
            childItemList.add(childItem);
        }
        groupItem.setList(childItemList);
        groupItem.setTitle(catlogs[6]);
        groupItemList.add(groupItem);

        adapter=new ExpandableAdapter(this,groupItemList,this);
        expandableListView.setAdapter(adapter);
    }
    private void setListener() {
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CatlogsSelActivity.this,DetailCatlogsActivity.class);
                startActivity(intent);
            }
        });
        expandableListView.setOnChildClickListener(this);
    }
    private void initView() {
        tv_next=(TextView)findViewById(R.id.catlogs_tv_next);
        expandableListView=(AnimatedExpandableListView)findViewById(R.id.catlogs_expandle_lv);
        record=new ArrayList<>();
    }
    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("商品类别选择");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void getView(View view, int gPosition,int cPosition) {
        if (groupItemList.get(gPosition).getList().get(cPosition).isSel()){
            record.add(groupItemList.get(gPosition).getList().get(cPosition).getCatlogName());
        }else {
            record.remove(groupItemList.get(gPosition).getList().get(cPosition).getCatlogName());
        }
        for (String s:record){
            Log.i("record", s);
        }
    }
    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int group, int child, long l) {
        if (groupItemList.get(group).getList().get(child).isSel()){
            ((ImageView)view.findViewById(R.id.child_item_iv)).setImageResource(R.mipmap.market_unsel);
            groupItemList.get(group).getList().get(child).setIsSel(false);
        }else {
            ((ImageView)view.findViewById(R.id.child_item_iv)).setImageResource(R.mipmap.marcket_sel);
            groupItemList.get(group).getList().get(child).setIsSel(true);
        }
        adapter.notifyDataSetChanged();
        getView(view,group,child);
        return true;
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
