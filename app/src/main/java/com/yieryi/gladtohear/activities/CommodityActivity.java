package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.catlogs.CatlogListAdapter;
import com.yieryi.gladtohear.adapter.commodity.CommodityListAdapter;
import com.yieryi.gladtohear.adapter.commodity.CommoditySelectedAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.bean.commodity.CategoryList;
import com.yieryi.gladtohear.bean.commodity.Root;
import com.yieryi.gladtohear.bean.commodity.SelRecord;
import com.yieryi.gladtohear.bean.commodity.Type;
import com.yieryi.gladtohear.biz.calculate.CommodityBiz;
import com.yieryi.gladtohear.fab.FabTransformation;
import com.yieryi.gladtohear.listener.CommodityRightItemClickListener;
import com.yieryi.gladtohear.listener.CommoditySelectedListener;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.view.LoadingDialog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class CommodityActivity extends BaseActivity implements CommodityRightItemClickListener,RequestListener,CatlogListAdapter.OnItemClickListener,CommoditySelectedListener{
    private final String TAG = CommodityActivity.class.getSimpleName();
    private FloatingActionButton floatButton;
    private RecyclerView catlog_sel_recycle,goods_sel_recycle,goods_selected_recycle;
    private TextView catlog_sel_food_drink_tv,catlog_sel_mom_baby_tv,catlog_sel_family_home_tv,catlog_sel_cosmetic_skin_tv;
    private LinearLayout goods_sel_liner;
    private static int selWhich;
    /**
     * 网络请求接口
     */
    private CommodityBiz biz;
    private LoadingDialog dialog;
    private String shop_id;
    /**
     * 服务端返回总数据
     */
    private List<CategoryList> totalList;
    private CategoryList food_drink_list;
    private CategoryList mom_baby_list;
    private CategoryList family_home_list;
    private CategoryList cosmetic_skin_list;
    private int fistVisiable;
    private List<Type> food_drink_types,mom_baby_types,family_home_types,cosmetic_skin_types;
    private RelativeLayout relativelayout;
    /**
     * adapter设定
     */
    private CatlogListAdapter food_drink_catlogListAdapter,mom_baby_catlogListAdapter,family_home_catlogListAdapter,cosmetic_skin_catlogListAdapter;
    private CommodityListAdapter food_drink_commodityListAdapter,mom_baby_commodityListAdapter,family_home_commodityListAdapter,cosmetic_skin_commodityListAdapter;
    private CommoditySelectedAdapter adapter;
    /**
     * 保存记录
     */
    public List<SelRecord> records = new ArrayList<>();
    private SelRecord record;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    initData();
                    break;
                case 1:
                    showToast("网络链接异常");
                    break;
                case 10:
                    no_data_tv.setVisibility(View.VISIBLE);
                    catlog_sel_recycle.setVisibility(View.GONE);
                    goods_sel_recycle.setVisibility(View.GONE);
                    break;
            }
        }
    };
    /**
     * 遮盖层
     */
    private TextView no_data_tv;

    @Override
    public int getLayout() {
        return R.layout.content_commodity;
    }

    /**
     * w网络请求完毕后的数据设定
     */
    private void initData() {
        if (dialog != null) {
            dialog.dismiss();
        }
        checkData(0);
        try{
            goods_sel_recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    fistVisiable=((LinearLayoutManager)goods_sel_recycle.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    catlog_sel_recycle.smoothScrollToPosition(fistVisiable);
                }
            });
        }catch (Exception e) {

        }
    }
    /**
     * 设置recycleview显示方式
     * @param
     * @param
     */
    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }
    @Override
    public void init(Bundle savedInstanceState) {
        shop_id=getIntent().getStringExtra("shop_id");
        Log.e("shop_id", shop_id);
        initView();
        setListener();
    }

    /**
     * 设置背景色
     */
    private TextView[] tv_sel=new TextView[4];
    public void setBackGround(int which,int two,int three,int fore){
        tv_sel[which].setTextColor(getResources().getColor(R.color.color_white));
        tv_sel[which].setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

        tv_sel[two].setTextColor(getResources().getColor(R.color.theme_color));
        tv_sel[two].setBackgroundColor(getResources().getColor(R.color.color_white));

        tv_sel[three].setTextColor(getResources().getColor(R.color.theme_color));
        tv_sel[three].setBackgroundColor(getResources().getColor(R.color.color_white));

        tv_sel[fore].setTextColor(getResources().getColor(R.color.theme_color));
        tv_sel[fore].setBackgroundColor(getResources().getColor(R.color.color_white));
    }

    private String[] titles = new String[]{"食品饮料","母婴亲子","日用家化","护肤彩妆"};
    public void checkData(int which){
        if (View.VISIBLE==no_data_tv.getVisibility()){
            no_data_tv.setVisibility(View.GONE);
        }
        if (totalList.size()==0) {
            handler.sendEmptyMessage(10);
        }else {
            for (int i = 0; i < totalList.size(); i++) {
                if (titles[which].equals(totalList.get(i).getTitle())) {
                    if (which == 0) {
                        food_drink_list = totalList.get(i);
                        food_drink_types=food_drink_list.getSon();
                        food_drink_commodityListAdapter=new CommodityListAdapter(CommodityActivity.this,food_drink_types,which,CommodityActivity.this);
                        food_drink_catlogListAdapter= new CatlogListAdapter(food_drink_types,CommodityActivity.this);
                        catlog_sel_recycle.setAdapter(food_drink_catlogListAdapter);
                        goods_sel_recycle.setAdapter(food_drink_commodityListAdapter);
                    }else if (which == 1) {
                        mom_baby_list = totalList.get(i);
                        mom_baby_types=mom_baby_list.getSon();
                        mom_baby_commodityListAdapter=new CommodityListAdapter(CommodityActivity.this,mom_baby_types,which,CommodityActivity.this);
                        mom_baby_catlogListAdapter= new CatlogListAdapter(mom_baby_types,CommodityActivity.this);
                        catlog_sel_recycle.setAdapter(mom_baby_catlogListAdapter);
                        goods_sel_recycle.setAdapter(mom_baby_commodityListAdapter);
                    }else if (which == 2) {
                        family_home_list = totalList.get(i);
                        family_home_types=family_home_list.getSon();
                        family_home_commodityListAdapter=new CommodityListAdapter(CommodityActivity.this,family_home_types,which,CommodityActivity.this);
                        family_home_catlogListAdapter= new CatlogListAdapter(family_home_types,CommodityActivity.this);
                        catlog_sel_recycle.setAdapter(family_home_catlogListAdapter);
                        goods_sel_recycle.setAdapter(family_home_commodityListAdapter);
                    }else if (which == 3) {
                        cosmetic_skin_list = totalList.get(i);
                        cosmetic_skin_types=cosmetic_skin_list.getSon();
                        cosmetic_skin_commodityListAdapter=new CommodityListAdapter(CommodityActivity.this,cosmetic_skin_types,which,CommodityActivity.this);
                        cosmetic_skin_catlogListAdapter= new CatlogListAdapter(cosmetic_skin_types,CommodityActivity.this);
                        catlog_sel_recycle.setAdapter(cosmetic_skin_catlogListAdapter);
                        goods_sel_recycle.setAdapter(cosmetic_skin_commodityListAdapter);
                    }
                    if (View.GONE == catlog_sel_recycle.GONE && View.GONE == goods_sel_recycle.GONE) {
                        catlog_sel_recycle.setVisibility(View.VISIBLE);
                        goods_sel_recycle.setVisibility(View.VISIBLE);
                    }
                    Log.e("family_home_list", String.valueOf(family_home_list));
                    return;
                }
            }
            handler.sendEmptyMessage(10);
        }
    }
    private void setListener() {
        catlog_sel_food_drink_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selWhich != 0) {
                    selWhich = 0;
                    setBackGround(0, 1, 2, 3);
                    checkData(selWhich);
                }
            }
        });
        catlog_sel_mom_baby_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selWhich != 1) {
                    selWhich = 1;
                    setBackGround(1, 0, 2, 3);
                    checkData(selWhich);
                }
            }
        });

        catlog_sel_family_home_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selWhich != 2) {
                    selWhich = 2;
                    setBackGround(2, 0, 1, 3);
                    checkData(selWhich);
                }
            }
        });
        catlog_sel_cosmetic_skin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selWhich = 3;
                setBackGround(3, 0, 1, 2);
                checkData(selWhich);
            }
        });
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floatButton.getVisibility() == View.VISIBLE) {
                    FabTransformation.with(floatButton).transformTo(goods_sel_liner);
                }
            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        no_data_tv = (TextView) findViewById(R.id.no_data_tv);
        floatButton = (FloatingActionButton) findViewById(R.id.floatButton);
        catlog_sel_recycle = (RecyclerView) findViewById(R.id.catlog_sel_recycle);
        setLayoutManager(catlog_sel_recycle);
        goods_sel_recycle = (RecyclerView) findViewById(R.id.goods_sel_recycle);
        setLayoutManager(goods_sel_recycle);
        goods_selected_recycle = (RecyclerView) findViewById(R.id.goods_selected_recycle);

        catlog_sel_food_drink_tv = (TextView) findViewById(R.id.catlog_sel_food_drink_tv);

        catlog_sel_food_drink_tv.setTextColor(getResources().getColor(R.color.color_white));
        catlog_sel_food_drink_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

        catlog_sel_mom_baby_tv = (TextView) findViewById(R.id.catlog_sel_mom_baby_tv);

        catlog_sel_family_home_tv = (TextView) findViewById(R.id.catlog_sel_family_home_tv);

        catlog_sel_cosmetic_skin_tv = (TextView) findViewById(R.id.catlog_sel_cosmetic_skin_tv);

        tv_sel[0] = catlog_sel_food_drink_tv;
        tv_sel[1] = catlog_sel_mom_baby_tv;
        tv_sel[2] = catlog_sel_family_home_tv;
        tv_sel[3] = catlog_sel_cosmetic_skin_tv;


        goods_sel_liner = (LinearLayout) findViewById(R.id.goods_sel_liner);
        relativelayout = (RelativeLayout) findViewById(R.id.relativelayout);

        if (isNetworkConnected(this)) {
            if (dialog == null) {
                dialog = new LoadingDialog(this, R.style.dialog);
                dialog.show();
                biz = new CommodityBiz();
                biz.getCommodityList(0, shop_id, TAG, this);
            }
        } else {
            showToast("网络无连接");
        }

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
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("商品选择");
        action.setHomeButtonEnabled(isTrue);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        OkHttp.mOkHttpClient.cancel(TAG);
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                Log.e("commodity",json);
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus() == OkHttp.NET_STATE) {
                    totalList = root.getData().getList();
                    if (totalList.size() == 0) {
                        handler.sendEmptyMessage(10);
                    }
                    handler.sendEmptyMessage(0);
                }else {
                    handler.sendEmptyMessage(1);
                }
            } catch (IOException e) {
                handler.sendEmptyMessage(10);
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
    @Override
    public void onClick(int position) {
        goods_sel_recycle.smoothScrollToPosition(position);


    }

    /**
     * 设置选定数据并且保存记录
     * @param title
     * @param position
     * @param index
     * @param adapter
     * @param categoryList
     */
    public void setDataChange(String title,String hid,int position,int index,int which,RecyclerView.Adapter adapter,CategoryList categoryList) {
        Log.e("categoryList", String.valueOf(categoryList));
        String state = categoryList.getSon().get(index).getSon().get(position).getSelect();
        if ("0".equals(state)) {
            categoryList.getSon().get(index).getSon().get(position).setSelect("1");
            record = new SelRecord();
            record.setTitle(title);
            record.setPosition(position);
            record.setIndex(index);
            record.setHid(hid);
            record.setSelWhich(which);
            records.add(record);
        } else if ("1".equals(state)) {
            categoryList.getSon().get(index).getSon().get(position).setSelect("0");
            for (int i = 0; i < records.size(); i++) {
                SelRecord rel = records.get(i);
                if (title.equals(rel.getTitle())) {
                    records.remove(rel);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
    /**
     * 数据回调接口
     * @param title
     * @param hid
     * @param position
     * @param index
     */
    public void onCommodityRightItemClickListener(String title, String hid, int position, int index,int which) {
        if (selWhich==0){
            setDataChange(title,hid,position,index,which,food_drink_commodityListAdapter,food_drink_list);
        }else if (selWhich==1){
            setDataChange(title,hid,position,index,which,mom_baby_commodityListAdapter,mom_baby_list);
        }else if (selWhich==2){
            setDataChange(title,hid,position,index,which,family_home_commodityListAdapter,family_home_list);
        }else if (selWhich==3){
            setDataChange(title,hid,position,index,which,cosmetic_skin_commodityListAdapter,cosmetic_skin_list);
        }
        adapter = new CommoditySelectedAdapter(records,CommodityActivity.this);
        GridLayoutManager manager = new GridLayoutManager(CommodityActivity.this, 4);
        goods_selected_recycle.setLayoutManager(manager);
        goods_selected_recycle.setAdapter(adapter);
    }
    @Override
    public void onItemLongClick(SelRecord record) {
        int which = record.getSelWhich();
        Log.e("which", "" + which);
        if (which == 0) {
            Log.e("mom_baby_types", "" + food_drink_types.size());
            food_drink_types.get(record.getIndex()).getSon().get(record.getPosition()).setSelect("0");
            food_drink_commodityListAdapter.notifyDataSetChanged();
        } else if (which == 1) {
            Log.e("mom_baby_commodityList1", "" + mom_baby_types.size());
            Log.e("mom_baby_commodityList2", "" + record.getIndex());
            Log.e("mom_baby_commodityList3", "" + record.getPosition());
            mom_baby_types.get(record.getIndex()).getSon().get(record.getPosition()).setSelect("0");
            mom_baby_commodityListAdapter.notifyDataSetChanged();
        } else if (which == 2) {
            Log.e("mom_baby_types2", "" + family_home_types.size());
            family_home_types.get(record.getIndex()).getSon().get(record.getPosition()).setSelect("0");
            family_home_commodityListAdapter.notifyDataSetChanged();
        } else if (which == 3) {
            Log.e("mom_baby_types3", "" + cosmetic_skin_types.size());
            cosmetic_skin_types.get(record.getIndex()).getSon().get(record.getPosition()).setSelect("0");
            cosmetic_skin_commodityListAdapter.notifyDataSetChanged();
        }

        for (int i = 0; i < records.size(); i++) {
            SelRecord rel = records.get(i);
            if (record.getTitle().equals(rel.getTitle())) {
                records.remove(rel);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
