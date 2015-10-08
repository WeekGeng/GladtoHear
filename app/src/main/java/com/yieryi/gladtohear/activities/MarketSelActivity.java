package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.yieryi.gladtohear.adapter.MarketSelAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.bean.market_address.Description;
import com.yieryi.gladtohear.bean.market_address.Root;
import com.yieryi.gladtohear.biz.calculate.MarcketSelBiz;
import com.yieryi.gladtohear.listener.OnRecycItemClickListener;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.view.LoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 把你算超市选择界面
 */
public class MarketSelActivity extends BaseActivity implements RequestListener,OnRecycItemClickListener{
    private final String TAG = MarketSelActivity.class.getSimpleName();
    private RecyclerView recycle_market;
    private String[] markets;
    private String[] otherMarkets;
    private RecyclerView.Adapter adapter;
    private boolean[] isSel;
    private ArrayList<Description> list;
    private TextView tv_next;
    private MarcketSelBiz biz;
    private List<String> record;
    private GridLayoutManager manager;
    private LoadingDialog dialog;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    manager = new GridLayoutManager(MarketSelActivity.this, 2);
                    recycle_market.setLayoutManager(manager);
                    recycle_market.setAdapter(adapter);
                    break;
                case 1:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    showToast("请求出错");
                    break;
            }
        }
    };

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
    private String shop_id="";
    private void setListener() {
        tv_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (record.isEmpty()) {
                    showToast("请先选择超市");
                    return;
                }
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < record.size(); i++) {
                    builder.append(record.get(i)+",");
                }
                shop_id=builder.toString();
                int last=shop_id.lastIndexOf(",");
                shop_id = shop_id.substring(0, last);
                startActivity(MarketSelActivity.this, CommodityActivity.class,"shop_id",shop_id);
            }
        });
    }
    /**
     * 初始化参数
     */
    private void initView() {
        recycle_market= (RecyclerView) findViewById(R.id.recycle_market);
        tv_next=(TextView)findViewById(R.id.tv_next);
        record = new ArrayList<>();
        if (isNetworkConnected(this)) {
            if (dialog==null) {
                dialog = new LoadingDialog(MarketSelActivity.this, R.style.dialog);
                dialog.show();
                biz = new MarcketSelBiz();
                biz.getMacketList(0,0,TAG,this);
            }
        }else {
            showToast("网络无连接");
        }

    }
    @Override
    public void onBackPressed() {
        OkHttp.mOkHttpClient.cancel(TAG);
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                OkHttp.mOkHttpClient.cancel(TAG);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                String jsons = "{\n" +
                        "    \"status\": 0, \n" +
                        "    \"error\": \"\", \n" +
                        "    \"data\": {\n" +
                        "        \"list\": [\n" +
                        "            {\n" +
                        "                \"shop_id\": \"195\", \n" +
                        "                \"shop_name\": \"万宁\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0195\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-21 16:39:32\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"194\", \n" +
                        "                \"shop_name\": \"迪亚天天\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0194\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-21 13:59:02\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"193\", \n" +
                        "                \"shop_name\": \"吉买盛\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0193\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-18 14:11:56\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"192\", \n" +
                        "                \"shop_name\": \"华联超市\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0192\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-17 15:14:56\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"191\", \n" +
                        "                \"shop_name\": \"屈臣氏\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0191\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-16 15:10:15\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"184\", \n" +
                        "                \"shop_name\": \"易买得\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"50\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0184\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-11 16:49:26\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"174\", \n" +
                        "                \"shop_name\": \"欧尚\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0174\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-11 16:41:22\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"169\", \n" +
                        "                \"shop_name\": \"乐天玛特\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0169\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-11 16:31:43\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"168\", \n" +
                        "                \"shop_name\": \"麦德龙\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0168\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-11 15:00:01\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"98\", \n" +
                        "                \"shop_name\": \"卜蜂莲花\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0098\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-10 16:44:06\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"37\", \n" +
                        "                \"shop_name\": \"沃尔玛\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0037\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-10 15:37:27\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"8\", \n" +
                        "                \"shop_name\": \"农工商\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0008\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-06 11:23:48\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"7\", \n" +
                        "                \"shop_name\": \"大润发\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0007\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-06 11:23:40\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"6\", \n" +
                        "                \"shop_name\": \"乐购\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0006\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-06 11:23:33\"\n" +
                        "            }, \n" +
                        "            {\n" +
                        "                \"shop_id\": \"4\", \n" +
                        "                \"shop_name\": \"家乐福\", \n" +
                        "                \"shop_logo\": \"\", \n" +
                        "                \"initial\": \"\", \n" +
                        "                \"city\": \"0\", \n" +
                        "                \"parent_id\": \"0\", \n" +
                        "                \"hid\": \"0:0004\", \n" +
                        "                \"shop_name2\": \"\", \n" +
                        "                \"addres\": \"\", \n" +
                        "                \"route\": \"\", \n" +
                        "                \"business_hours\": \"\", \n" +
                        "                \"phone\": \"\", \n" +
                        "                \"baidu_jing\": \"\", \n" +
                        "                \"baidu_wei\": \"\", \n" +
                        "                \"gaode_jing\": \"\", \n" +
                        "                \"gaode_wei\": \"\", \n" +
                        "                \"ctime\": \"2015-09-06 11:23:12\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}";
                Log.e("jsons=", json);
                final Root root = gson.fromJson(jsons, Root.class);
                if ("0".equals(String.valueOf(root.getStatus()))) {
                    list = root.getData().getList();
                    isSel = new boolean[list.size()];
                    for (int i=0;i<list.size();i++){
                        isSel[i]=false;
                    }
                    adapter = new MarketSelAdapter(list,this,isSel);
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (isSel[position]){
            record.remove(list.get(position).getShop_id());
            isSel[position]=false;
        }else {
            record.add(list.get(position).getShop_id());
            isSel[position] = true;
        }

        for (String x : record) {
            showToast("x="+x);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
}
