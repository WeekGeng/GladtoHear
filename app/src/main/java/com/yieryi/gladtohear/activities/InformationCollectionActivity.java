package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
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
import com.yieryi.gladtohear.fragment.main.informationcollection.CosmeticSkinFragment;
import com.yieryi.gladtohear.fragment.main.informationcollection.FamilyHomeFragment;
import com.yieryi.gladtohear.fragment.main.informationcollection.FoodDrinkFragment;
import com.yieryi.gladtohear.fragment.main.informationcollection.MomBabyFragment;
import com.yieryi.gladtohear.fragment.main.main.FirstFragment;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.view.SwipeRefreshView;
import java.io.IOException;
import java.util.List;

public class InformationCollectionActivity extends BaseActivity implements View.OnClickListener{
    private SwipeRefreshView swipeRefreshView;
    private TextView food_drink;
    private TextView mom_baby;
    private TextView day_user_family;
    private TextView cosmetic_skin;
    private int index=0;
    private Fragment foodDrink,momBaby,familyHome,cosmeticSkin;
    private FragmentManager manager;
    private FragmentTransaction transaction;
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
        foodDrink=new FoodDrinkFragment();
        manager=getSupportFragmentManager();
        setFragmentChose(foodDrink);

        food_drink=(TextView)findViewById(R.id.food_drink);
        mom_baby=(TextView)findViewById(R.id.mom_baby);
        day_user_family=(TextView)findViewById(R.id.day_user_family);
        cosmetic_skin=(TextView)findViewById(R.id.cosmetic_skin);
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
                        switch (index){
                            case 0:
                                foodDrink=new FoodDrinkFragment();
                                setFragmentChose(foodDrink);
                                swipeRefreshView.setRefreshing(false);
                                break;
                            case 1:
                                momBaby=new MomBabyFragment();
                                setFragmentChose(momBaby);
                                swipeRefreshView.setRefreshing(false);
                                break;
                            case 2:
                                familyHome=new FamilyHomeFragment();
                                setFragmentChose(familyHome);
                                swipeRefreshView.setRefreshing(false);
                                break;
                            case 3:
                                cosmeticSkin=new CosmeticSkinFragment();
                                setFragmentChose(cosmeticSkin);
                                swipeRefreshView.setRefreshing(false);
                                break;
                        }
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
        paremas.put("category_id", category_id);
        paremas.put("page", String.valueOf(page));
        paremas.put("perpage", String.valueOf(perpage));
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
    /**
     * 设置Fragment
     * @param fragment
     */
    private void setFragmentChose(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.information_collection_content,fragment);
        transaction.commit();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.food_drink:
                if (index!=0) {
                    OkHttp.cancleInforMationNetWork(new String[]{"FoodDrinkFragment"});
                    food_drink.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                    mom_baby.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    day_user_family.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    index = 0;
                    foodDrink=new FoodDrinkFragment();
                    setFragmentChose(foodDrink);
                }
                break;
            case R.id.mom_baby:
                if (index!=1) {
                    OkHttp.cancleInforMationNetWork(new String[]{"MomBabyFragment"});
                    food_drink.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    mom_baby.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                    day_user_family.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    index = 1;
                    momBaby=new MomBabyFragment();
                    setFragmentChose(momBaby);
                }
                break;
            case R.id.day_user_family:
                if (index!=2) {
                    OkHttp.cancleInforMationNetWork(new String[]{"FamilyHomeFragment"});
                    food_drink.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    mom_baby.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    day_user_family.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                    cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    index = 2;
                    familyHome=new FamilyHomeFragment();
                    setFragmentChose(familyHome);
                }
                break;
            case R.id.cosmetic_skin:
                if (index!=3) {
                    OkHttp.cancleInforMationNetWork(new String[]{"CosmeticSkinFragment"});
                    food_drink.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    mom_baby.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    day_user_family.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    cosmetic_skin.setBackgroundColor(getResources().getColor(R.color.text_half_red));
                    index = 3;
                    cosmeticSkin=new CosmeticSkinFragment();
                    setFragmentChose(cosmeticSkin);
                }
                break;
        }
    }
}
