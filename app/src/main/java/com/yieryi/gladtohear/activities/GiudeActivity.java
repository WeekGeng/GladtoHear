package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.GuideFragmentAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.fragment.main.guide.FragmentGuideOne;
import com.yieryi.gladtohear.fragment.main.guide.FragmentGuideThree;
import com.yieryi.gladtohear.fragment.main.guide.FragmentGuideTwo;

import java.util.ArrayList;
import java.util.List;
public class GiudeActivity extends BaseActivity {
    private final String  TAG=GiudeActivity.class.getSimpleName();
    private List<Fragment> list=new ArrayList<>();
    private Fragment guideOne,guideTwo,guideThree;
    private ViewPager guide_vp;
    private GuideFragmentAdapter adapter;
    @Override
    public int getLayout() {
        return R.layout.activity_giude;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initView();
    }

    private void initView() {
        guide_vp= (ViewPager) findViewById(R.id.guide_vp);
        adapter=new GuideFragmentAdapter(getSupportFragmentManager(),list);
        guide_vp.setAdapter(adapter);
    }

    private void initData() {
        guideThree=new FragmentGuideThree();
        guideOne=new FragmentGuideOne();
        guideTwo=new FragmentGuideTwo();
        list.add(guideOne);
        list.add(guideTwo);
        list.add(guideThree);
    }

    @Override
    protected void setToolBar(ActionBar action,boolean isTrue) {

    }
}
