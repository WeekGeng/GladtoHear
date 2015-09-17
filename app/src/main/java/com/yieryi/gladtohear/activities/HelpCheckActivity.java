package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.fragment.main.helpcheck.BrandSelFragment;
import com.yieryi.gladtohear.fragment.main.helpcheck.CatlogSelFragment;
import com.yieryi.gladtohear.fragment.main.helpcheck.MacketSelFragment;
import com.yieryi.gladtohear.listener.OnRecycItemClickListener;
public class HelpCheckActivity extends BaseActivity implements OnRecycItemClickListener,View.OnClickListener{
    private MacketSelFragment macketSelFragment;
    private BrandSelFragment brandSelFragment;
    private CatlogSelFragment catlogSelFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private TextView help_check_macket_sel_tv,help_check_brand_sel_tv,help_check_catlog_sel_tv;
    private int tagSel;

    @Override
    public int getLayout() {
        return R.layout.activity_help_check;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        setListeners();
    }

    private void setListeners() {
        help_check_macket_sel_tv.setOnClickListener(this);
        help_check_brand_sel_tv.setOnClickListener(this);
        help_check_catlog_sel_tv.setOnClickListener(this);
    }

    private void initView() {
        macketSelFragment=new MacketSelFragment();
        manager=getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.help_check_content,macketSelFragment);
        transaction.commit();
        help_check_macket_sel_tv=(TextView)findViewById(R.id.help_check_macket_sel_tv);
        help_check_brand_sel_tv=(TextView)findViewById(R.id.help_check_brand_sel_tv);
        help_check_catlog_sel_tv=(TextView)findViewById(R.id.help_check_catlog_sel_tv);
        help_check_macket_sel_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
        help_check_macket_sel_tv.setTextColor(getResources().getColor(R.color.color_white));
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

    /**
     * 设置Fragment
     * @param fragment
     */
    private void setFragmentChose(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.help_check_content,fragment);
        transaction.commit();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.help_check_macket_sel_tv:
                if (tagSel!=0){
                    macketSelFragment=new MacketSelFragment();
                    setFragmentChose(macketSelFragment);
                    help_check_macket_sel_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    help_check_macket_sel_tv.setTextColor(getResources().getColor(R.color.color_white));

                    help_check_brand_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_brand_sel_tv.setTextColor(getResources().getColor(R.color.color_black));

                    help_check_catlog_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_catlog_sel_tv.setTextColor(getResources().getColor(R.color.color_black));
                    tagSel=0;
                }
                break;
            case R.id.help_check_brand_sel_tv:
                if (tagSel!=1){
                    brandSelFragment=new BrandSelFragment();
                    setFragmentChose(brandSelFragment);

                    help_check_brand_sel_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    help_check_brand_sel_tv.setTextColor(getResources().getColor(R.color.color_white));

                    help_check_macket_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_macket_sel_tv.setTextColor(getResources().getColor(R.color.color_black));

                    help_check_catlog_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_catlog_sel_tv.setTextColor(getResources().getColor(R.color.color_black));
                    tagSel=1;
                }
                break;
            case  R.id.help_check_catlog_sel_tv:
                if (tagSel!=2){
                    catlogSelFragment=new CatlogSelFragment();
                    setFragmentChose(catlogSelFragment);

                    help_check_catlog_sel_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
                    help_check_catlog_sel_tv.setTextColor(getResources().getColor(R.color.color_white));

                    help_check_macket_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_macket_sel_tv.setTextColor(getResources().getColor(R.color.color_black));

                    help_check_brand_sel_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    help_check_brand_sel_tv.setTextColor(getResources().getColor(R.color.color_black));
                    tagSel=2;
                }
                break;
        }
    }
}
