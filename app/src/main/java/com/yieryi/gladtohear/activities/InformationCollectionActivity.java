package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.InformationCollctionAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.bean.informationcollaction.Description;
import com.yieryi.gladtohear.bean.informationcollaction.Root;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.constans.CatlogConsts;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.view.SwipeRefreshView;
import java.io.IOException;
import java.util.List;

public class InformationCollectionActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView information_col_recyc;
    private SwipeRefreshView swipeRefreshView;
    private TextView food_drink;
    private TextView mom_baby;
    private TextView day_user_family;
    private TextView cosmetic_skin;
    private int index=0;
    private InformationCollctionAdapter adapter;
    @Override
    public int getLayout() {
        return R.layout.activity_information_collection;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListeners();
    }

    private void setListeners() {
        food_drink.setOnClickListener(this);
        mom_baby.setOnClickListener(this);
        day_user_family.setOnClickListener(this);
        cosmetic_skin.setOnClickListener(this);
    }

    private void initView() {
        food_drink=(TextView)findViewById(R.id.food_drink);
        mom_baby=(TextView)findViewById(R.id.mom_baby);
        day_user_family=(TextView)findViewById(R.id.day_user_family);
        cosmetic_skin=(TextView)findViewById(R.id.cosmetic_skin);

        information_col_recyc=(RecyclerView)findViewById(R.id.information_col_recyc);
        swipeRefreshView=(SwipeRefreshView)findViewById(R.id.swipeRefreshView);
        //设置卷内的颜色
        swipeRefreshView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        // 开始加载数据
        swipeRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //swipeRefreshView.setRefreshing(true);
            }
        }, 100);
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshView.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        attemptAttentionList("0",String.valueOf(index),0,0);
                        OkHttp.asyncPost(BaseConsts.BASE_URL, paremas, new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                showToast("网络出错,请检查网络链接是否正确。");
                            }
                            @Override
                            public void onResponse(Response response) throws IOException {
                                if (response.isSuccessful()){
                                    Gson gson=new Gson();
                                    String json=response.body().string();
                                    Root root=gson.fromJson(json, Root.class);
                                    List<Description> list=root.getData().getList();
                                    adapter=new InformationCollctionAdapter(list);
                                }
                            }
                        });
                    }
                }, 500);
            }
        });
    }

    /**
     * 根据城市id和品牌进行筛选
     */
    private void attemptAttentionList(String city_id,String category_id,int page,int perpage) {
        paremas.put(BaseConsts.APP, CatlogConsts.GetInfomation.params_app);
        paremas.put(BaseConsts.CLASS, CatlogConsts.GetInfomation.params_class);
        paremas.put(BaseConsts.SIGN, CatlogConsts.GetInfomation.params_sign);
        paremas.put("city_id",city_id);
        paremas.put("category_id",category_id);
        paremas.put("page",String.valueOf(page));
        paremas.put("perpage",String.valueOf(perpage));
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("资讯汇总");
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.food_drink:
                food_drink.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                mom_baby.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                day_user_family.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                index=1;
                break;
            case R.id.mom_baby:
                food_drink.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                mom_baby.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                day_user_family.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                index=2;
                break;
            case R.id.day_user_family:
                food_drink.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                mom_baby.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                day_user_family.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                index=3;
                break;
            case R.id.cosmetic_skin:
                food_drink.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                mom_baby.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                day_user_family.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                index=4;
                break;
        }
    }
}
