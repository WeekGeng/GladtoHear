package com.yieryi.gladtohear.activities;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.marcket.MarcketSelDetailAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.bean.marcketseldetail.Lists;
import com.yieryi.gladtohear.bean.marcketseldetail.Root;
import com.yieryi.gladtohear.biz.helpcheck.marcket_sel.searchlist.BrandSearchListBiz;
import com.yieryi.gladtohear.listener.MarcketSelDetailCallback;
import com.yieryi.gladtohear.listener.RequestListener;
import java.io.IOException;
import java.util.List;

public class SearchListActivity extends BaseActivity implements RequestListener,MarcketSelDetailCallback{
    private RecyclerView search_list_recycle;
    private String keyword;
    private boolean catlog;
    private BrandSearchListBiz biz;
    private MarcketSelDetailAdapter adapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LinearLayoutManager manager = new LinearLayoutManager(SearchListActivity.this);
                    search_list_recycle.setLayoutManager(manager);
                    search_list_recycle.setAdapter(adapter);
                    break;
                case 1:
                    showToast("网络无连接");
                    break;
                case 10:
                    break;
            }
        }
    };
    @Override
    public int getLayout() {
        return R.layout.activity_search_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        keyword=getIntent().getStringExtra("keyword");
        catlog = getIntent().getBooleanExtra("catlog", false);
        initView();
    }

    private void initView() {
        search_list_recycle = (RecyclerView) findViewById(R.id.search_list_recycle);
        if (isNetworkConnected(this)) {
            biz=new BrandSearchListBiz();
            if (catlog) {
                biz.getSearchList(keyword,"2","1","50",this);
            }else {
                biz.getSearchList(keyword,"1","1","50",this);
            }
        }else {
            handler.sendEmptyMessage(2);
        }
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        if (catlog) {
            String searchName = getIntent().getStringExtra("searchName");
            action.setTitle(searchName);
        }else {
            action.setTitle(keyword);
        }
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            try {
                String json = response.body().string();
                Log.e("searchlist", json);
                if (!json.contains("[")) {
                    handler.sendEmptyMessage(10);
                    return;
                }
                Gson gson = new Gson();
                Root root = gson.fromJson(json, Root.class);
                List<Lists> lists=root.getData().getList();
                Log.e("size", "size=" + lists.size());
                adapter = new MarcketSelDetailAdapter(lists, this, 1);
                handler.sendEmptyMessage(0);
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void callBack(Lists lists) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("lists", lists);
        startActivity(this,bundle,"bundle", HelpCheckGoodsDetailActivity.class);
    }
}
