package com.yieryi.gladtohear.fragment.main.main;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.yieryi.gladtohear.activities.InformationDetailActivity;
import com.yieryi.gladtohear.adapter.main.MainFragmentAdapter;
import com.yieryi.gladtohear.base.TApplication;
import com.yieryi.gladtohear.bean.main_brand.News;
import com.yieryi.gladtohear.bean.main_brand.Root;
import com.yieryi.gladtohear.biz.helpcheck.marcket_sel.main_new.NewBrandBiz;
import com.yieryi.gladtohear.listener.AccumulateShopItemClickListener;
import com.yieryi.gladtohear.listener.JazzyRecyclerViewScrollListener;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.overridge.MyGridLayoutManager;
import com.yieryi.gladtohear.tools.recycleviewanimation.effects.SlideInEffect;
import com.yieryi.gladtohear.view.LoadingDialog;

import java.io.IOException;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;

/**
 * Created by Administrator on 2015/8/27.
 */
public class FirstFragment extends Fragment implements RequestListener,AccumulateShopItemClickListener{
    private final String TAG=FirstFragment.class.getSimpleName();
    private RecyclerView main_first_fragment_recycle;
    private NewBrandBiz biz;
    private List<News> list;
    private MainFragmentAdapter adapter;
    private MyGridLayoutManager gridLayoutManager;
    private LoadingDialog dialog;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    dialog.dismiss();
                    gridLayoutManager = new MyGridLayoutManager(getActivity(), 1);
                    gridLayoutManager.setSmoothScrollbarEnabled(true);
                    main_first_fragment_recycle.setLayoutManager(gridLayoutManager);
                    main_first_fragment_recycle.setAdapter(adapter);

                    main_first_fragment_recycle.setItemAnimator(new FlipInBottomXAnimator());
                    main_first_fragment_recycle.getItemAnimator().setAddDuration(1000);
                    main_first_fragment_recycle.getItemAnimator().setRemoveDuration(1000);
                    main_first_fragment_recycle.getItemAnimator().setMoveDuration(1000);
                    main_first_fragment_recycle.getItemAnimator().setChangeDuration(1000);
                    break;
                case 1:
                    Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (dialog==null){
            dialog=new LoadingDialog(getActivity(),R.style.dialog);
            dialog.show();
            biz=new NewBrandBiz();
            biz.getMacketList("品牌新品",TAG, this);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_first_fragment, container, false);
        main_first_fragment_recycle=(RecyclerView)view.findViewById(R.id.main_first_fragment_recycle);
        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                json = json.replace(" ","");
                final Root root = gson.fromJson(json, Root.class);
                if ("0".equals(String.valueOf(root.getStatus()))) {
                    list = root.getData().getNews();
                    adapter=new MainFragmentAdapter(list,this);
                    handler.sendEmptyMessage(0);
                } else {
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
    public void onItemClick(View view, String content_id) {
        Intent intent=new Intent(getActivity(), InformationDetailActivity.class);
        intent.putExtra("content_id", content_id);
        startActivity(intent);
    }
}
