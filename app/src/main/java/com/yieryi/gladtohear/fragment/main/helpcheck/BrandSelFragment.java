package com.yieryi.gladtohear.fragment.main.helpcheck;

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
import android.widget.Toast;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.catlogs.GoodsListAdapter;
import com.yieryi.gladtohear.bean.helpcheck.brandsel.Root;
import com.yieryi.gladtohear.bean.helpcheck.brandsel.Type;
import com.yieryi.gladtohear.biz.helpcheck.marcket_sel.brand_sel.BrandSelBiz;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.view.LoadingDialog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *品牌选择
 */
public class BrandSelFragment extends Fragment implements RequestListener,GoodsListAdapter.OnItemClickListener{
    private final String TAG = BrandSelFragment.class.getSimpleName();
    private List<Type> list;
    private List<String> titles;
    private RecyclerView brand_hot_search_recycle;
    private LoadingDialog dialog;
    private BrandSelBiz biz;
    private GoodsListAdapter adapter;
    private GridLayoutManager manager;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    dialog.dismiss();
                    manager = new GridLayoutManager(getActivity(), 3);
                    brand_hot_search_recycle.setLayoutManager(manager);
                    brand_hot_search_recycle.setAdapter(adapter);
                    break;
                case 1:
                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (dialog==null){
            dialog=new LoadingDialog(getActivity(),R.style.dialog);
            biz = new BrandSelBiz();
            biz.getHotSearch(1,TAG,this);
            dialog.show();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brand_sel_fragment, container, false);
        brand_hot_search_recycle = (RecyclerView) view.findViewById(R.id.brand_hot_search_recycle);
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
                    list=root.getData().getTypes();
                    titles = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        titles.add(list.get(i).getSmall_category());
                    }
                    adapter = new GoodsListAdapter(titles,this);
                    handler.sendEmptyMessage(0);
                }else {
                    handler.sendEmptyMessage(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    @Override
    public void onClick(View view) {

    }
}
