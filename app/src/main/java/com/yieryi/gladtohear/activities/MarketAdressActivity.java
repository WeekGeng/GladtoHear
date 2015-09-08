package com.yieryi.gladtohear.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;

public class MarketAdressActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG=MarketAdressActivity.class.getSimpleName();
    private TextView food_drink,mom_baby,day_user_family,cosmetic_skin;
    private boolean[] sel=new boolean[4];
    private RecyclerView market_address_recycle;
    @Override
    public int getLayout() {
        return R.layout.activity_market_adress;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }
    private void initView() {
        food_drink=(TextView)findViewById(R.id.food_drink);
        mom_baby=(TextView)findViewById(R.id.mom_baby);
        day_user_family=(TextView)findViewById(R.id.day_user_family);
        cosmetic_skin=(TextView)findViewById(R.id.cosmetic_skin);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setHomeButtonEnabled(true);
        action.setTitle("超市门店");
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
                sel[0]=true;
                sel[1]=false;
                sel[2]=false;
                sel[3]=false;
                setSel();
                break;
            case R.id.mom_baby:
                sel[0]=false;
                sel[1]=true;
                sel[2]=false;
                sel[3]=false;
                setSel();
                break;
            case R.id.day_user_family:
                sel[0]=false;
                sel[1]=false;
                sel[2]=true;
                sel[3]=false;
                setSel();
                break;
            case R.id.cosmetic_skin:
                sel[0]=false;
                sel[1]=false;
                sel[2]=false;
                sel[3]=true;
                setSel();
                break;
        }
    }
    public void setSel(){
        food_drink.setSelected(sel[0]);
        mom_baby.setSelected(sel[1]);
        day_user_family.setSelected(sel[2]);
        cosmetic_skin.setSelected(sel[3]);
    }
}
