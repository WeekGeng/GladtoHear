package com.yieryi.gladtohear.activities;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.HelpCheckAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.listener.OnRecycItemClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class HelpCheckActivity extends BaseActivity implements OnRecycItemClickListener{
    private RecyclerView recyclerView;
    private HelpCheckAdapter adapter;
    private List<Map<String,String>> list;
    private Map<String,String> map;
    @Override
    public int getLayout() {
        return R.layout.activity_help_check;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initView();
    }
    /**
     * 此处数据网络请求得来 用实体类
     */
    private void initData() {
        list=new ArrayList<>();
        map=new HashMap<>();
        map.put("icon", "http://192.168.100.102:8080/images/darunfa.jpg");
        map.put("name", "大润发超市");
        list.add(map);

        map=new HashMap<>();
        map.put("icon", "http://192.168.100.102:8080/images/jialefu.jpg");
        map.put("name", "家乐福超市");
        list.add(map);
        map=new HashMap<>();
        map.put("icon", "http://192.168.100.102:8080/images/shijihualian.jpg");
        map.put("name", "世纪华联超市");
        list.add(map);
        map=new HashMap<>();
        map.put("icon", "http://192.168.100.102:8080/images/a.png");
        map.put("name", "乐购超市");
        list.add(map);
        map=new HashMap<>();
        map.put("icon","http://192.168.100.102:8080/images/b.png");
        map.put("name", "欧尚超市");
        list.add(map);
        map=new HashMap<>();
        map.put("icon","http://192.168.100.102:8080/images/c.png");
        map.put("name", "沃尔玛超市");
        list.add(map);
        map=new HashMap<>();
        map.put("icon","http://192.168.100.102:8080/images/d.png");
        map.put("name", "易买得超市");
        list.add(map);
        map=new HashMap<>();
        map.put("icon","http://192.168.100.102:8080/images/e.png");
        map.put("name", "全家超市");
        list.add(map);
        map=new HashMap<>();
        map.put("icon","http://192.168.100.102:8080/images/f.png");
        map.put("name", "全家超市");
        list.add(map);
        map=new HashMap<>();
        map.put("icon","http://192.168.100.102:8080/images/g.png");
        map.put("name", "全家超市");
        list.add(map);
        map.put("icon","http://192.168.100.102:8080/images/h.png");
        map.put("name", "全家超市");
        list.add(map);

    }
    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.help_check_recyc);
        adapter=new HelpCheckAdapter(list,this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
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
}
