package com.yieryi.gladtohear.fragment.main.helpcheck;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.activities.HelpCheckActivity;
import com.yieryi.gladtohear.activities.HelpCheckGoodsDetailActivity;
import com.yieryi.gladtohear.activities.SearchListActivity;
import com.yieryi.gladtohear.adapter.catlogs.GoodsListAdapter;
import com.yieryi.gladtohear.adapter.marcket.MarcketSelDetailAdapter;
import com.yieryi.gladtohear.bean.helpcheck.brandsel.Root;
import com.yieryi.gladtohear.bean.helpcheck.brandsel.Type;
import com.yieryi.gladtohear.bean.marcketseldetail.Lists;
import com.yieryi.gladtohear.biz.helpcheck.marcket_sel.brand_sel.BrandSelBiz;
import com.yieryi.gladtohear.listener.MarcketSelDetailCallback;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.overridge.MyGridLayoutManager;
import com.yieryi.gladtohear.view.LoadingDialog;
import java.io.IOException;
import java.util.List;
/**
 * 品类选择
 */
public class CatlogSelFragment extends Fragment implements RequestListener,GoodsListAdapter.OnItemClickListener,MarcketSelDetailCallback,GoodsListAdapter.OnHotItemClickListener{
    private final String TAG = CatlogSelFragment.class.getSimpleName();
    private List<String> titles;
    private RecyclerView brand_hot_search_recycle;
    private LoadingDialog dialog;
    private BrandSelBiz biz;
    private GridLayoutManager manager;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    manager = new MyGridLayoutManager(getActivity(), 3);
                    brand_hot_search_recycle.setLayoutManager(manager);
                    brand_hot_search_recycle.setAdapter(adapter);
                    MyGridLayoutManager linearManager = new MyGridLayoutManager(getActivity(),1);
                    brand_sel_recycle.setLayoutManager(linearManager);
                    brand_sel_recycle.setAdapter(marcketSelDetailAdapter);
                    break;
                case 1:
                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private GoodsListAdapter adapter;
    private List<Type> types;
    private List<Lists> lists;
    private MarcketSelDetailAdapter marcketSelDetailAdapter;
    private RecyclerView brand_sel_recycle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (dialog==null){
            dialog=new LoadingDialog(getActivity(), R.style.dialog);
            biz = new BrandSelBiz();
            biz.getHotSearch(2,TAG,this);
            dialog.show();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brand_sel_fragment, container, false);
        brand_hot_search_recycle = (RecyclerView) view.findViewById(R.id.brand_hot_search_recycle);
        brand_sel_recycle = (RecyclerView) view.findViewById(R.id.brand_sel_recycle);
        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson=new Gson();
            try {
                String json=response.body().string();
                Root root = gson.fromJson(json, Root.class);
                int state=root.getStatus();
                if (OkHttp.NET_STATE==state) {
                    types=root.getData().getTypes();
                    lists=root.getData().getLists();
                    marcketSelDetailAdapter = new MarcketSelDetailAdapter(lists,this,2);
                    adapter = new GoodsListAdapter(types,CatlogSelFragment.this,true);
                    handler.sendEmptyMessage(0);
                }else {
                    handler.sendEmptyMessage(1);
                }
            } catch (IOException e) {
                handler.sendEmptyMessage(1);
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), SearchListActivity.class);
        intent.putExtra("keyword",((TextView) view).getText().toString());
        intent.putExtra("catlog", true);

        startActivity(intent);
    }

    @Override
    public void callBack(Lists lists) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("lists", lists);
        bundle.putString("key","item");
        ((HelpCheckActivity) getActivity()).startActivity(getActivity(),bundle,"bundle", HelpCheckGoodsDetailActivity.class);
    }

    @Override
    public void onHotItemClick(String title, String keyword) {
        Intent intent = new Intent(getActivity(), SearchListActivity.class);
        intent.putExtra("keyword",keyword);
        intent.putExtra("catlog", true);
        intent.putExtra("searchName", title);
        startActivity(intent);
    }
}
