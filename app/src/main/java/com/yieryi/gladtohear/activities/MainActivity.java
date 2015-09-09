package com.yieryi.gladtohear.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.FunctionAdapter;
import com.yieryi.gladtohear.bean.FunctionItem;
import com.yieryi.gladtohear.overridge.MyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FunctionAdapter.OnItemClickListener{
    //帮你算按钮
    private List<FunctionItem> functionItems;
    private FunctionItem item;
    private RecyclerView main_function_recyc;
    private FunctionAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private TextView main_local_tv;
    private String provience;
    private ImageView user_center;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        Intent intent = getIntent();
        Log.e("intent",intent.toString());
        provience=intent.getStringExtra("provience");
        getDataFunction();
        initView();
        setListeners();
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
        item.setUrl(String.valueOf(R.mipmap.woman_chat));
        item.setTitle("主妇论坛");
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
        item.setUrl(String.valueOf(R.mipmap.informationsummary));
        item.setTitle("资讯汇总");
        functionItems.add(item);
    }

    /**
     * 设置监听器
     */
    private void setListeners() {

        user_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserCenterActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化界面
     */
    private void initView() {

        main_local_tv=(TextView)findViewById(R.id.main_local_tv);
        main_local_tv.setText(provience);
        user_center=(ImageView)findViewById(R.id.user_center);
        main_function_recyc= (RecyclerView) findViewById(R.id.main_function_recyc);
        adapter=new FunctionAdapter(functionItems,this);
        gridLayoutManager=new MyGridLayoutManager(this,3);
        gridLayoutManager.setOrientation(MyGridLayoutManager.VERTICAL);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        main_function_recyc.setLayoutManager(gridLayoutManager);
        main_function_recyc.setAdapter(adapter);
    }

    @Override
    public void onClick(View view, int position) {
        switch (position){
            case 0:
                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
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
                checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkbox.isChecked()) {
                            dialog_next.setClickable(true);
                            dialog_next.setBackgroundColor(getResources().getColor(R.color.dialog_color_sle));
                        }else {
                            dialog_next.setClickable(false);
                            dialog_next.setBackgroundColor(getResources().getColor(R.color.dialog_color_unsle));
                        }
                    }
                });
                window.findViewById(R.id.dialog_next).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,MarketSelActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                Intent intent2=new Intent(MainActivity.this,HelpCheckActivity.class);
                startActivity(intent2);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }
}
