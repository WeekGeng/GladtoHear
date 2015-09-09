package com.yieryi.gladtohear.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.HelpCheckAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.bean.market_address.Description;
import com.yieryi.gladtohear.bean.market_address.Root;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.constans.CatlogConsts;
import com.yieryi.gladtohear.listener.OnRecycItemClickListener;
import com.yieryi.gladtohear.network.OkHttp;

import java.io.IOException;
import java.util.List;

public class HelpCheckActivity extends BaseActivity implements OnRecycItemClickListener{
    private RecyclerView recyclerView;
    private HelpCheckAdapter adapter;
    @Override
    public int getLayout() {
        return R.layout.activity_help_check;
    }
    @Override
    public void init(Bundle savedInstanceState) {
//        initData();
        initView();

        getMarcket();
    }

    private void getMarcket() {
        paremas.put(BaseConsts.APP, CatlogConsts.GetMarket.params_app);
        paremas.put(BaseConsts.CLASS, CatlogConsts.GetMarket.params_class);
        paremas.put(BaseConsts.SIGN, CatlogConsts.GetMarket.params_sign);
        paremas.put("parent_id",String.valueOf(0));
        OkHttp.asyncPost(BaseConsts.BASE_URL, paremas, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                showToast("请检查网络情况");
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    try {
                        String json = response.body().string();
                        json = json.replace(" ", "");

                        String jsons="{\n" +
                                "  status : 0,\n" +
                                "  error : \"\",\n" +
                                "  data : \n" +
                                "  {\n" +
                                "    list : \n" +
                                "    [\n" +
                                "      {\n" +
                                "        shop_id : \"8\",\n" +
                                "        shop_name : \"农工商\",\n" +
                                "        shop_logo : null,\n" +
                                "        initial : null,\n" +
                                "        city : \"0\",\n" +
                                "        parent_id : \"0\",\n" +
                                "        hid : \"0:0008\",\n" +
                                "        shop_name2 : null,\n" +
                                "        addres : \"\",\n" +
                                "        route : \"\",\n" +
                                "        business_hours : \"\",\n" +
                                "        phone : \"\",\n" +
                                "        baidu_jing : \"\",\n" +
                                "        baidu_wei : \"\",\n" +
                                "        gaode_jing : \"\",\n" +
                                "        gaode_wei : \"\",\n" +
                                "        ctime : \"2015-09-06 11:23:48\"\n" +
                                "      },\n" +
                                "      {\n" +
                                "        shop_id : \"7\",\n" +
                                "        shop_name : \"大润发\",\n" +
                                "        shop_logo : null,\n" +
                                "        initial : null,\n" +
                                "        city : \"0\",\n" +
                                "        parent_id : \"0\",\n" +
                                "        hid : \"0:0007\",\n" +
                                "        shop_name2 : null,\n" +
                                "        addres : \"\",\n" +
                                "        route : \"\",\n" +
                                "        business_hours : \"\",\n" +
                                "        phone : \"\",\n" +
                                "        baidu_jing : \"\",\n" +
                                "        baidu_wei : \"\",\n" +
                                "        gaode_jing : \"\",\n" +
                                "        gaode_wei : \"\",\n" +
                                "        ctime : \"2015-09-06 11:23:40\"\n" +
                                "      },\n" +
                                "      {\n" +
                                "        shop_id : \"6\",\n" +
                                "        shop_name : \"乐购\",\n" +
                                "        shop_logo : null,\n" +
                                "        initial : null,\n" +
                                "        city : \"0\",\n" +
                                "        parent_id : \"0\",\n" +
                                "        hid : \"0:0006\",\n" +
                                "        shop_name2 : null,\n" +
                                "        addres : \"\",\n" +
                                "        route : \"\",\n" +
                                "        business_hours : \"\",\n" +
                                "        phone : \"\",\n" +
                                "        baidu_jing : \"\",\n" +
                                "        baidu_wei : \"\",\n" +
                                "        gaode_jing : \"\",\n" +
                                "        gaode_wei : \"\",\n" +
                                "        ctime : \"2015-09-06 11:23:33\"\n" +
                                "      },\n" +
                                "      {\n" +
                                "        shop_id : \"4\",\n" +
                                "        shop_name : \"家乐福\",\n" +
                                "        shop_logo : null,\n" +
                                "        initial : null,\n" +
                                "        city : \"0\",\n" +
                                "        parent_id : \"0\",\n" +
                                "        hid : \"0:0004\",\n" +
                                "        shop_name2 : null,\n" +
                                "        addres : \"\",\n" +
                                "        route : \"\",\n" +
                                "        business_hours : \"\",\n" +
                                "        phone : \"\",\n" +
                                "        baidu_jing : \"\",\n" +
                                "        baidu_wei : \"\",\n" +
                                "        gaode_jing : \"\",\n" +
                                "        gaode_wei : \"\",\n" +
                                "        ctime : \"2015-09-06 11:23:12\"\n" +
                                "      },\n" +
                                "      {\n" +
                                "        shop_id : \"1\",\n" +
                                "        shop_name : \"易买得\",\n" +
                                "        shop_logo : null,\n" +
                                "        initial : null,\n" +
                                "        city : \"0\",\n" +
                                "        parent_id : \"0\",\n" +
                                "        hid : \"0\",\n" +
                                "        shop_name2 : null,\n" +
                                "        addres : \"\",\n" +
                                "        route : \"\",\n" +
                                "        business_hours : \"\",\n" +
                                "        phone : \"\",\n" +
                                "        baidu_jing : \"\",\n" +
                                "        baidu_wei : \"\",\n" +
                                "        gaode_jing : \"\",\n" +
                                "        gaode_wei : \"\",\n" +
                                "        ctime : \"2015-09-04 08:54:12\"\n" +
                                "      }\n" +
                                "    ]\n" +
                                "  }\n" +
                                "}";
                        Log.e("jsons=", json);
                        final Root root = gson.fromJson(jsons, Root.class);

                        if ("0".equals(String.valueOf(root.getStatus()))) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("超市列表");
                                    List<Description> list=root.getData().getList();
                                    adapter=new HelpCheckAdapter(list,HelpCheckActivity.this);
                                    LinearLayoutManager manager=new LinearLayoutManager(HelpCheckActivity.this);
                                    recyclerView.setLayoutManager(manager);
                                    recyclerView.setAdapter(adapter);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("失败");
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.help_check_recyc);
    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("帮你查");
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
    public void onItemClick(View view, int position) {
        startActivity(HelpCheckActivity.this, MarketAdressActivity.class, "position", String.valueOf(position));
    }
}
