package com.yieryi.gladtohear.fragment.main.main;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.activities.HelpCheckActivity;
import com.yieryi.gladtohear.adapter.main.MainFragmentAdapter;
import com.yieryi.gladtohear.bean.main_brand.News;
import com.yieryi.gladtohear.bean.main_brand.Root;
import com.yieryi.gladtohear.biz.helpcheck.marcket_sel.main_new.NewBrandBiz;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.overridge.MyGridLayoutManager;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/8/27.
 */
public class FirstFragment extends Fragment implements RequestListener{
    private RecyclerView main_first_fragment_recycle;
    private NewBrandBiz biz;
    private List<News> list;
    private MainFragmentAdapter adapter;
    private AlertDialog dialog;
    private MyGridLayoutManager gridLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biz=new NewBrandBiz();
        biz.getMacketList("品牌新品", this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_first_fragment, container, false);
        main_first_fragment_recycle=(RecyclerView)view.findViewById(R.id.main_first_fragment_recycle);
        gridLayoutManager=new MyGridLayoutManager(getActivity(),1);
        gridLayoutManager.setOrientation(MyGridLayoutManager.VERTICAL);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        main_first_fragment_recycle.setLayoutManager(gridLayoutManager);
        while(adapter==null){

        }
        main_first_fragment_recycle.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                Log.e("first",json);
                final Root root = gson.fromJson(json, Root.class);
                if ("0".equals(String.valueOf(root.getStatus()))) {
                    list = root.getData().getNews();
                    Log.e("size",list.size()+"");
                    adapter=new MainFragmentAdapter(list);
                    adapter.notifyDataSetChanged();
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((HelpCheckActivity) getActivity()).showToast("请求失败");
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),"请检查网络情况",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
