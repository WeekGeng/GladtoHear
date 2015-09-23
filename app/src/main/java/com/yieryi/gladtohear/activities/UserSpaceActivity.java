package com.yieryi.gladtohear.activities;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.fragment.main.userspace.ExchangePrizeRecordFragment;
import com.yieryi.gladtohear.fragment.main.userspace.ListRecordFragment;
import com.yieryi.gladtohear.fragment.main.userspace.PrizeExchangeFragment;

public class UserSpaceActivity extends BaseActivity implements View.OnClickListener{
    private TextView user_space_list_record_tv,user_space_exchange_prizes_record_tv,user_space_prizes_exchange_tv;
    private Fragment listRecord,exchangePrize,prizeExchange;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private int whickSel;
    @Override
    public int getLayout() {
        return R.layout.activity_user_space;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListeners();
    }
    private void setListeners() {
        user_space_list_record_tv.setOnClickListener(this);
        user_space_exchange_prizes_record_tv.setOnClickListener(this);
        user_space_prizes_exchange_tv.setOnClickListener(this);
    }
    private void initView() {
        user_space_list_record_tv=(TextView)findViewById(R.id.user_space_list_record_tv);
        user_space_exchange_prizes_record_tv=(TextView)findViewById(R.id.user_space_exchange_prizes_record_tv);
        user_space_prizes_exchange_tv=(TextView)findViewById(R.id.user_space_prizes_exchange_tv);
        listRecord=new ListRecordFragment();
        manager=getSupportFragmentManager();
        setFragmentChose(listRecord);
        user_space_list_record_tv.setTextColor(getResources().getColor(R.color.color_white));
        user_space_list_record_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
    }
    /**
     * 设置Fragment
     * @param fragment
     */
    private void setFragmentChose(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.user_space_content,fragment);
        transaction.commit();
    }
    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("个人空间");
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
            case R.id.user_space_list_record_tv:
                if (whickSel!=0){
                    listRecord=new ListRecordFragment();
                    setFragmentChose(listRecord);
                    user_space_list_record_tv.setTextColor(getResources().getColor(R.color.color_white));
                    user_space_list_record_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    user_space_exchange_prizes_record_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_exchange_prizes_record_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    user_space_prizes_exchange_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_prizes_exchange_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whickSel=0;
                }
                break;
            case R.id.user_space_exchange_prizes_record_tv:
                if (whickSel!=1){
                    exchangePrize=new ExchangePrizeRecordFragment();
                    setFragmentChose(exchangePrize);
                    user_space_exchange_prizes_record_tv.setTextColor(getResources().getColor(R.color.color_white));
                    user_space_exchange_prizes_record_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    user_space_list_record_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_list_record_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    user_space_prizes_exchange_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_prizes_exchange_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whickSel=1;
                }
                break;
            case R.id.user_space_prizes_exchange_tv:
                if (whickSel!=2){
                    prizeExchange=new PrizeExchangeFragment();
                    setFragmentChose(prizeExchange);

                    user_space_prizes_exchange_tv.setTextColor(getResources().getColor(R.color.color_white));
                    user_space_prizes_exchange_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    user_space_exchange_prizes_record_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_exchange_prizes_record_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    user_space_list_record_tv.setTextColor(getResources().getColor(R.color.text_half_red));
                    user_space_list_record_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whickSel=2;
                }
                break;
        }
    }
}
