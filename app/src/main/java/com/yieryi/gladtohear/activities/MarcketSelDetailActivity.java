package com.yieryi.gladtohear.activities;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.marcket.MarcketSelDetailAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.bean.marcketseldetail.Lists;
import com.yieryi.gladtohear.bean.marcketseldetail.Root;
import com.yieryi.gladtohear.biz.helpcheck.marcket_sel.MarcketDetailBiz;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.constans.CatlogConsts;
import com.yieryi.gladtohear.listener.MarcketSelDetailCallback;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.view.LoadingDialog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarcketSelDetailActivity extends BaseActivity implements RequestListener,MarcketSelDetailCallback{
    private final String TAG=MarcketSelDetailActivity.class.getSimpleName();
    private RecyclerView marcket_sel_detail_recycle;
    private String shop_name;
    private TextView marcket_sel_detail_food_drink_tv,marcket_sel_detail_mom_boby_tv,marcket_sel_detail_family_home_tv,marcket_sel_detail_costum_skin_tv;
    private TextView[] tv_sel;
    private String keyword;
    private LoadingDialog dialog;
    private MarcketDetailBiz biz;
    private List<Lists> lists;
    private int whickSel;
    private RelativeLayout no_data_relative;
    private MarcketSelDetailAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (no_data_relative.getVisibility() == View.VISIBLE) {
                        no_data_relative.setVisibility(View.GONE);
                        marcket_sel_detail_recycle.setVisibility(View.VISIBLE);
                    }
                    LinearLayoutManager manager = new LinearLayoutManager(MarcketSelDetailActivity.this);
                    marcket_sel_detail_recycle.setLayoutManager(manager);
                    marcket_sel_detail_recycle.setAdapter(adapter);
                    break;
                case 1:
                    break;
                case 10:
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                    if (no_data_relative.getVisibility() == View.GONE) {
                        no_data_relative.setVisibility(View.VISIBLE);
                        marcket_sel_detail_recycle.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_marcket_sel_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        shop_name = getIntent().getStringExtra("shop_name");
        keyword = getIntent().getStringExtra("keyword");
        showToast("shop_name="+shop_name+",keyword="+keyword);
        initView();
        setListeners();
        if (isNetworkConnected(this)){
            if (dialog==null) {
                dialog = new LoadingDialog(this, R.style.dialog);
                dialog.show();
                biz = new MarcketDetailBiz();
                biz.getMacketList(keyword,"3","1","50","1",TAG,this);
            }
        }
    }

    private void setListeners() {
        marcket_sel_detail_food_drink_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whickSel != 0) {
                    setBackGround(0,1,2,3);
                    biz = new MarcketDetailBiz();
                    biz.getMacketList(keyword,"3","1","50","1",TAG,MarcketSelDetailActivity.this);
                    whickSel=0;
                }
            }
        });
        marcket_sel_detail_mom_boby_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whickSel!=1){
                    setBackGround(1,0,2,3);
                    biz=new MarcketDetailBiz();
                    biz.getMacketList(keyword,"3","1","50","319",TAG,MarcketSelDetailActivity.this);
                    whickSel=1;
                }
            }
        });
        marcket_sel_detail_family_home_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whickSel!=2){
                    setBackGround(2,0,1,3);
                    biz=new MarcketDetailBiz();
                    biz.getMacketList(keyword,"3","1","50","387",TAG,MarcketSelDetailActivity.this);
                    whickSel=2;
                }
            }
        });
        marcket_sel_detail_costum_skin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whickSel!=3){
                    setBackGround(3,0,1,2);
                    biz=new MarcketDetailBiz();
                    biz.getMacketList(keyword,"3","1","50","491",TAG,MarcketSelDetailActivity.this);
                    whickSel=3;
                }
            }
        });
    }

    /**
     * 设置选中状态
     * @param which
     * @param two
     * @param three
     * @param four
     */
    public void setBackGround(int which,int two,int three,int four){
        tv_sel[which].setBackgroundColor(getResources().getColor(R.color.text_half_red));
        tv_sel[two].setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
        tv_sel[three].setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
        tv_sel[four].setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
    }
    private void initView() {
        no_data_relative = (RelativeLayout) findViewById(R.id.no_data_relative);
        tv_sel = new TextView[4];
        marcket_sel_detail_recycle = (RecyclerView) findViewById(R.id.marcket_sel_detail_recycle);
        marcket_sel_detail_food_drink_tv = (TextView) findViewById(R.id.marcket_sel_detail_food_drink_tv);
        marcket_sel_detail_mom_boby_tv = (TextView) findViewById(R.id.marcket_sel_detail_mom_boby_tv);
        marcket_sel_detail_family_home_tv = (TextView) findViewById(R.id.marcket_sel_detail_family_home_tv);
        marcket_sel_detail_costum_skin_tv = (TextView) findViewById(R.id.marcket_sel_detail_costum_skin_tv);
        tv_sel[0] = marcket_sel_detail_food_drink_tv;
        tv_sel[1] = marcket_sel_detail_mom_boby_tv;
        tv_sel[2] = marcket_sel_detail_family_home_tv;
        tv_sel[3] = marcket_sel_detail_costum_skin_tv;
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle(shop_name);
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson=new Gson();
            try {
                String json=response.body().string();
                json=json.replace(" ","");
                if (!json.contains("[")) {
                    handler.sendEmptyMessage(10);
                    return;
                }
                Root root=gson.fromJson(json, Root.class);
                if (root.getStatus()== OkHttp.NET_STATE){
                    lists=root.getData().getList();
                    adapter = new MarcketSelDetailAdapter(lists, MarcketSelDetailActivity.this,0);
                    handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                handler.sendEmptyMessage(10);
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void callBack(Lists lists) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("lists",lists);
        startActivity(MarcketSelDetailActivity.this,bundle,"bundle", HelpCheckGoodsDetailActivity.class);
    }
}
