package com.yieryi.gladtohear.fragment.main;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.activities.AccumulatedShopActivity;
import com.yieryi.gladtohear.activities.HelpCheckActivity;
import com.yieryi.gladtohear.activities.InformationCollectionActivity;
import com.yieryi.gladtohear.activities.MainActivity;
import com.yieryi.gladtohear.activities.MarketSelActivity;
import com.yieryi.gladtohear.activities.UserSpaceActivity;
import com.yieryi.gladtohear.adapter.FunctionAdapter;
import com.yieryi.gladtohear.bean.FunctionItem;
import com.yieryi.gladtohear.fragment.main.main.FirstFragment;
import com.yieryi.gladtohear.fragment.main.main.SecondFragment;
import com.yieryi.gladtohear.fragment.main.main.ThirdFragment;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.overridge.MyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class Main extends Fragment implements FunctionAdapter.OnItemClickListener{
    private List<FunctionItem> functionItems;
    private FunctionItem item;
    private TextView main_new_brand_tv;
    private TextView main_release_brand_tv;
    private TextView main_story_brand_tv;
    private RecyclerView main_function_recyc;
    private FunctionAdapter adapter;
    private MyGridLayoutManager gridLayoutManager;
    private FragmentManager manager;
    private FirstFragment firstFragment;
    private FragmentTransaction transaction;
    private final String TAG=Main.class.getSimpleName();
    private int whichSel;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFunction();

    }
    private void getDataFunction() {
        functionItems=new ArrayList<>();
        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.help_calculate));
        item.setTitle("帮你算");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.help_check));
        item.setTitle("帮你查");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.informationsummary));
        item.setTitle("资讯汇总");
        functionItems.add(item);
        
        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.integralmall));
        item.setTitle("积分商城");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.persional_space));
        item.setTitle("个人空间");
        functionItems.add(item);

        item=new FunctionItem();
        item.setUrl(String.valueOf(R.mipmap.woman_chat));
        item.setTitle("主妇论坛");
        functionItems.add(item);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        initView(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        main_new_brand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whichSel!=0) {
                    if (((MainActivity)getActivity()).isNetworkConnected(getActivity())) {
                        OkHttp.cancleMainNetWork(new String[]{"FirstFragment"});
                        firstFragment = new FirstFragment();
                        setFragmentChose(firstFragment);
                    }else {
                        Toast.makeText(getActivity(),"网络无连接",Toast.LENGTH_LONG).show();
                    }
                    main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
                    main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    main_release_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_release_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    main_story_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_story_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whichSel = 0;
                }
            }
        });
        main_release_brand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whichSel!=1) {
                    if (((MainActivity)getActivity()).isNetworkConnected(getActivity())){
                        OkHttp.cancleMainNetWork(new String[]{"SecondFragment"});
                        secondFragment = new SecondFragment();
                        setFragmentChose(secondFragment);
                    }else {
                        Toast.makeText(getActivity(),"网络无连接",Toast.LENGTH_LONG).show();
                    }
                    main_release_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
                    main_release_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    main_story_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_story_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whichSel=1;
                }
            }
        });
        main_story_brand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whichSel!=2) {
                    if (((MainActivity)getActivity()).isNetworkConnected(getActivity())){
                        OkHttp.cancleMainNetWork(new String[]{"ThirdFragment"});
                        thirdFragment = new ThirdFragment();
                        setFragmentChose(thirdFragment);
                    }else {
                        Toast.makeText(getActivity(),"网络无连接",Toast.LENGTH_LONG).show();
                    }
                    main_story_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
                    main_story_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));

                    main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));

                    main_release_brand_tv.setTextColor(getResources().getColor(R.color.color_black));
                    main_release_brand_tv.setBackgroundColor(getResources().getColor(R.color.color_white));
                    whichSel=2;
                }
            }
        });
    }

    /**
     * 初始化界面
     */
    private void initView(View view) {
        
            main_new_brand_tv = (TextView) view.findViewById(R.id.main_new_brand_tv);
            main_release_brand_tv = (TextView) view.findViewById(R.id.main_release_brand_tv);
            main_story_brand_tv = (TextView) view.findViewById(R.id.main_story_brand_tv);
            main_function_recyc = (RecyclerView) view.findViewById(R.id.main_function_recyc);
            adapter = new FunctionAdapter(functionItems, this);

            gridLayoutManager = new MyGridLayoutManager(getActivity(),3);
            gridLayoutManager.setOrientation(MyGridLayoutManager.VERTICAL);
            gridLayoutManager.setSmoothScrollbarEnabled(true);
            main_function_recyc.setLayoutManager(gridLayoutManager);

            main_function_recyc.setAdapter(adapter);
            manager = getChildFragmentManager();
            if (((MainActivity)getActivity()).isNetworkConnected(view.getContext())) {
                firstFragment = new FirstFragment();
                setFragmentChose(firstFragment);
            }
            main_new_brand_tv.setTextColor(getResources().getColor(R.color.color_white));
            main_new_brand_tv.setBackgroundColor(getResources().getColor(R.color.text_little_half_red));
    }

    /**
     * 设置Fragment
     * @param fragment
     */
    private void setFragmentChose(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.main_content,fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view, int position) {
        switch (position){
            case 0:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                alertDialog();
                break;
            case 1:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent1=new Intent(getActivity(),HelpCheckActivity.class);
                startActivity(intent1);
                break;
            case 2:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent5=new Intent(getActivity(),InformationCollectionActivity.class);
                startActivity(intent5);
                break;
            case 3:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent3=new Intent(getActivity(),AccumulatedShopActivity.class);
                startActivity(intent3);
                break;
            case 4:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Intent intent4=new Intent(getActivity(),UserSpaceActivity.class);
                startActivity(intent4);
                break;
            case 5:
                OkHttp.cancleMainNetWork(new String[]{TAG});
                Toast.makeText(getActivity(), "此功能暂时未开放,敬请期待.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 帮你算dialog
     */
    private void alertDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.activity_prompt);
        window.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final TextView dialog_next=(TextView)window.findViewById(R.id.dialog_next);
        final CheckBox checkbox= (CheckBox) window.findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dialog_next.setClickable(true);
                    dialog_next.setBackgroundColor(getResources().getColor(R.color.dialog_color_sle));
                    dialog_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(getActivity(),MarketSelActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                } else {
                    dialog_next.setClickable(false);
                    dialog_next.setBackgroundColor(getResources().getColor(R.color.dialog_color_unsle));
                }
            }
        });
    }
}
