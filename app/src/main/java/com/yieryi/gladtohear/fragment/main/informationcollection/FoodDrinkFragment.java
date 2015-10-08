package com.yieryi.gladtohear.fragment.main.informationcollection;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.activities.InformationCollectionActivity;
import com.yieryi.gladtohear.adapter.informationcollection.InformationCollctionAdapter;
import com.yieryi.gladtohear.bean.informationcollaction.Description;
import com.yieryi.gladtohear.bean.informationcollaction.Root;
import com.yieryi.gladtohear.biz.informationcollection.InformationCollectionBiz;
import com.yieryi.gladtohear.listener.JazzyRecyclerViewScrollListener;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.tools.recycleviewanimation.effects.SlideInEffect;
import com.yieryi.gladtohear.view.LoadingDialog;

import java.io.IOException;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;

/**
 * Created by Administrator on 2015/9/18 0018.
 */
public class FoodDrinkFragment extends Fragment implements RequestListener{
    private final String TAG=FoodDrinkFragment.class.getSimpleName();
    private InformationCollectionBiz biz;
    private RecyclerView information_collection_recycle;
    private List<Description> list;
    private InformationCollctionAdapter adapter;
    private LoadingDialog dialog;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    dialog.dismiss();
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                    information_collection_recycle.setLayoutManager(manager);
                    information_collection_recycle.setAdapter(adapter);
                    information_collection_recycle.setItemAnimator(new FlipInBottomXAnimator());
                    information_collection_recycle.getItemAnimator().setAddDuration(1000);
                    information_collection_recycle.getItemAnimator().setRemoveDuration(1000);
                    information_collection_recycle.getItemAnimator().setMoveDuration(1000);
                    information_collection_recycle.getItemAnimator().setChangeDuration(1000);
                    JazzyRecyclerViewScrollListener scroll=new JazzyRecyclerViewScrollListener();
                    information_collection_recycle.addOnScrollListener(scroll);
                    scroll.setTransitionEffect(new SlideInEffect());
                    break;
                case 1:
                    ((InformationCollectionActivity)getActivity()).showToast("数据请求失败");
                    break;
                case 2:
                    Log.e("error", "网络请求失败");
                    break;
            }

        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (dialog==null){
            dialog = new LoadingDialog(getActivity(),R.style.dialog);
            biz=new InformationCollectionBiz();
            biz.getInformation(0,1,1,10,TAG,this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.information_collection_fragment,container,false);
        information_collection_recycle= (RecyclerView)view.findViewById(R.id.information_collection_recycle);
        information_collection_recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()){
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                Log.e("first", json);
                final Root root = gson.fromJson(json, Root.class);
                if ("0".equals(String.valueOf(root.getStatus()))) {
                    list = root.getData().getList();
                    Log.e("size",list.size()+"");
                    adapter=new InformationCollctionAdapter(list);
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
        handler.sendEmptyMessage(2);
    }
}
