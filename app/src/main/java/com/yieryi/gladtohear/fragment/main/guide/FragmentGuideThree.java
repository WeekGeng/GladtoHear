package com.yieryi.gladtohear.fragment.main.guide;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.activities.GuideActivity;
import com.yieryi.gladtohear.activities.WelActivity;
import com.yieryi.gladtohear.constans.BaseConsts;
import com.yieryi.gladtohear.tools.sp.SPCache;

/**
 * Created by Administrator on 2015/9/8.
 */
public class FragmentGuideThree extends Fragment implements View.OnClickListener{
    private TextView guide_three_tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.guide_three,container,false);
        guide_three_tv=(TextView)view.findViewById(R.id.guide_three_tv);
        guide_three_tv.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {
        //标识第一次进入程序
        SPCache.putBoolean(BaseConsts.SharePreference.IS_FIRST_INTO, false);
        ((GuideActivity)getActivity()).startActivity(getActivity(), WelActivity.class);
        getActivity().finish();

    }
}
