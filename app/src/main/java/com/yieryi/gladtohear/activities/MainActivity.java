package com.yieryi.gladtohear.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.yieryi.gladtohear.R;
public class MainActivity extends AppCompatActivity {
    //帮你算按钮
    private TextView help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListeners();
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MarketSelActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化界面
     */
    private void initView() {
        help= (TextView) findViewById(R.id.help);
    }

}
