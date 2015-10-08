package com.yieryi.gladtohear.activities;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.jauker.widget.BadgeView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.helpcheck.shop_information.ShopInformationAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.base.TApplication;
import com.yieryi.gladtohear.bean.helpcheck.detail.Detail;
import com.yieryi.gladtohear.bean.helpcheck.detail.DetailRoot;
import com.yieryi.gladtohear.bean.marcketseldetail.Lists;
import com.yieryi.gladtohear.biz.helpcheck.marcket_sel.detail.DetailAddressBiz;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.fab.FabTransformation;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.view.CircleBadgeView;
import com.yieryi.gladtohear.view.CircleImageView;
import java.io.IOException;
import java.util.List;
import java.util.Map;
public class HelpCheckGoodsDetailActivity extends BaseActivity implements RequestListener{
    //传递过来的详细信息
    private RecyclerView help_address_recycleview;
    private List<Detail> list;
    private Map<String,String> map;
    private GridLayoutManager manager;
    private ShopInformationAdapter adapter;
    private FloatingActionButton floatActionButton;
    private TextView detail_content_tv;
    private TextView prompt;
    private TextView detail_tv_title,detail_apply_people,detail_market_name,detail_start_end_time,detail_now_money,detail_old_money,detail_reference_money;
    private ImageView detail_iv_content,detail_market_pic;
    private TextView reference_tv;
    private CircleImageView left_shop_add_remove;
    private DetailAddressBiz biz;
    private View v;
    private CircleBadgeView badgeView;
    private Lists lists;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    manager = new GridLayoutManager(HelpCheckGoodsDetailActivity.this, 2);
                    help_address_recycleview.setLayoutManager(manager);
                    help_address_recycleview.setAdapter(adapter);
                    break;
                case 5:
                    badgeView.setText(TApplication.shop_lists.size() + "");
                    break;
                case 10:
                    break;
            }
        }
    };
    @Override
    public int getLayout() {
        return R.layout.activity_help_check_goods_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        lists = (Lists) bundle.getSerializable("lists");
        initView();
        setData();
        setListeners();
    }

    private void setListeners() {
        left_shop_add_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TApplication.user_id != null) {
                    if (TApplication.isSel) {
                        if (TApplication.shop_lists.size() == 1) {
                            TApplication.shop_lists.remove(lists);
                            TApplication.isSel = false;
                        } else {
                            for (int i = 0; i < TApplication.shop_lists.size(); i++) {
                                if (TApplication.shop_lists.get(i).getName().equals(lists.getName())) {
                                    TApplication.shop_lists.remove(i);
                                    TApplication.isSel = false;
                                }
                            }
                        }
                    } else {
                        if (TApplication.shop_lists.size() == 0) {
                            TApplication.shop_lists.add(lists);
                            TApplication.isSel = true;
                        }else {
                            for (int i = 0; i < TApplication.shop_lists.size(); i++) {
                                if (!TApplication.shop_lists.get(i).getName().equals(lists.getName())) {
                                    TApplication.shop_lists.add(lists);
                                    TApplication.isSel = true;
                                }
                            }
                        }
                    }
                    showToast(""+TApplication.shop_lists.size());
                    handler.sendEmptyMessage(5);
                }
            }
        });
    }

    private void setData() {
        detail_tv_title.setText(lists.getName());
        detail_market_name.setText(lists.getShop_name());
        detail_apply_people.setText(lists.getCrowd());
        String startTime = lists.getStart_time();
        String endTime=lists.getEnd_time();
        startTime = startTime.substring(0, 10);
        endTime = endTime.substring(0, 10);
        detail_start_end_time.setText(startTime+"~"+endTime);
        detail_now_money.setText("￥" + lists.getF_price());
        detail_old_money.setText("￥" + lists.getO_price());
        detail_old_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        detail_reference_money.setText(lists.getOne_shop());
        detail_content_tv.setText(lists.getInfo());
        ImageLoader.getInstance().displayImage(BaseConsts.BASE_IMAGE_URL + lists.getTitlepic(), detail_iv_content, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                ((ImageView) view).setImageResource(R.mipmap.loading);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                ((ImageView) view).setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        ImageLoader.getInstance().displayImage(BaseConsts.BASE_IMAGE_URL + lists.getPic(), detail_market_pic, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                ((ImageView) view).setImageResource(R.mipmap.loading_a);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                ((ImageView) view).setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });

    }

    private void initView() {
        detail_tv_title = (TextView) findViewById(R.id.detail_tv_title);
        detail_apply_people = (TextView) findViewById(R.id.detail_apply_people);
        detail_market_name = (TextView) findViewById(R.id.detail_market_name);
        detail_start_end_time = (TextView) findViewById(R.id.detail_start_end_time);
        detail_now_money = (TextView) findViewById(R.id.detail_now_money);
        detail_old_money = (TextView) findViewById(R.id.detail_old_money);
        detail_reference_money = (TextView) findViewById(R.id.detail_reference_money);
        detail_iv_content = (ImageView) findViewById(R.id.detail_iv_content);
        detail_market_pic = (ImageView) findViewById(R.id.detail_market_pic);
        left_shop_add_remove = (CircleImageView) findViewById(R.id.left_shop_add_remove);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                badgeView = new CircleBadgeView(HelpCheckGoodsDetailActivity.this,findViewById(R.id.goods_detail_shopping_cart));
                v = findViewById(R.id.goods_detail_shopping_cart);
                badgeView.setText(TApplication.shop_lists.size()+"");
                badgeView.setBackgroundColor(Color.RED);//设置背景颜色
                badgeView.setGravity(Gravity.CENTER);
                badgeView.setBadgePosition(CircleBadgeView.POSITION_TOP_LEFT);
                badgeView.show();
            }
        }, 500);
        if (TApplication.user_id != null) {
            if (TApplication.shop_lists.size()!=0) {
            }else {

            }
        }
        for (Lists shop_list:TApplication.shop_lists) {
            if (lists.getName().equals(shop_list.getName())) {
                left_shop_add_remove.setImageResource(android.R.drawable.ic_menu_delete);

            }else {
                left_shop_add_remove.setImageResource(android.R.drawable.ic_menu_add);

            }
        }
        reference_tv = (TextView) findViewById(R.id.reference_tv);
        prompt = (TextView) findViewById(R.id.prompt);
        detail_content_tv = (TextView) findViewById(R.id.detail_content_tv);
        help_address_recycleview = (RecyclerView) findViewById(R.id.address_recycle);
        if (isNetworkConnected(this)) {
            biz = new DetailAddressBiz();
            biz.getDetailAddress(lists.getShop_id(), this);
        }
        floatActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);
        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floatActionButton.getVisibility() == View.VISIBLE) {
                    if (isNetworkConnected(HelpCheckGoodsDetailActivity.this)) {
                        FabTransformation.with(floatActionButton).transformTo(help_address_recycleview);
                        floatActionButton.setVisibility(View.VISIBLE);
                        prompt.setVisibility(View.VISIBLE);
                        prompt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (floatActionButton.getVisibility() != View.VISIBLE) {
                                    FabTransformation.with(floatActionButton).transformFrom(help_address_recycleview);
                                    prompt.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else {
                        FabTransformation.with(floatActionButton).transformTo(reference_tv);
                        floatActionButton.setVisibility(View.VISIBLE);
                        prompt.setVisibility(View.VISIBLE);
                        prompt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (floatActionButton.getVisibility() != View.VISIBLE) {
                                    FabTransformation.with(floatActionButton).transformFrom(reference_tv);
                                    prompt.setVisibility(View.GONE);
                                }
                            }
                        });
                    }

                }
            }
        });

        detail_content_tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        if (floatActionButton.getVisibility() != View.VISIBLE) {
            FabTransformation.with(floatActionButton).transformFrom(help_address_recycleview);
            prompt.setVisibility(View.GONE);
            return;
        }
        finish();
        super.onBackPressed();
    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("资讯详情");
        action.setHomeButtonEnabled(isTrue);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help_check_goods_detail, menu);
        return super.onCreateOptionsMenu(menu);
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
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            try {
                String json = response.body().string();
                if (!json.contains("[")){
                    return;
                }
                Gson gson=new Gson();
                DetailRoot root = gson.fromJson(json, DetailRoot.class);
                list=root.getData().getList();
                adapter = new ShopInformationAdapter(list);
                handler.sendEmptyMessage(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
}
