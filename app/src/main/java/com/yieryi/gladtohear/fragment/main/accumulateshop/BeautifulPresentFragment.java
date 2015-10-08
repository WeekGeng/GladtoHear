package com.yieryi.gladtohear.fragment.main.accumulateshop;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.activities.AccumulateShopDetailActivity;
import com.yieryi.gladtohear.activities.AccumulatedShopActivity;
import com.yieryi.gladtohear.adapter.accumulate.AccumulateShopAdapter;
import com.yieryi.gladtohear.bean.accumulate.Product;
import com.yieryi.gladtohear.bean.accumulate.Root;
import com.yieryi.gladtohear.biz.accumulate.AccumulateShopBiz;
import com.yieryi.gladtohear.listener.AccumulateShopItemClickListener;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.view.LoadingDialog;
import java.io.IOException;
import java.util.List;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
public class BeautifulPresentFragment extends Fragment implements RequestListener,AccumulateShopItemClickListener{
    private final String TAG = BeautifulPresentFragment.class.getSimpleName();
    private RecyclerView accumulate_recycle;
    private AccumulateShopBiz biz;
    private LoadingDialog dialog;
    private GridLayoutManager gridLayoutManager;
    private AccumulateShopAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    dialog.dismiss();
                    gridLayoutManager = new GridLayoutManager(getActivity(),2);
                    accumulate_recycle.setLayoutManager(gridLayoutManager);
                    accumulate_recycle.setAdapter(adapter);
                    accumulate_recycle.setItemAnimator(new FlipInBottomXAnimator());
                    accumulate_recycle.getItemAnimator().setAddDuration(1000);
                    accumulate_recycle.getItemAnimator().setRemoveDuration(1000);
                    accumulate_recycle.getItemAnimator().setMoveDuration(1000);
                    accumulate_recycle.getItemAnimator().setChangeDuration(1000);
                    break;
                case 1:
                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
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
        if (dialog==null) {
            dialog = new LoadingDialog(getActivity(), R.style.dialog);
            biz=new AccumulateShopBiz();
            biz.getProductsList(38,1,"desc",TAG,this);
            dialog.show();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accumulate_fragment, container, false);
        accumulate_recycle = (RecyclerView)view.findViewById(R.id.accumulate_recycle);
        return view;
    }
    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                json=json.replaceAll(" ", "");
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus() == 0) {
                    List<Product> list = root.getData().getProducts();
                    adapter = new AccumulateShopAdapter(list,this);
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
    public void onItemClick(View view,String content_id) {
        ((AccumulatedShopActivity) getActivity()).startActivity(getActivity(), AccumulateShopDetailActivity.class, "content_id",content_id);
    }
}
