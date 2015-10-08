package com.yieryi.gladtohear.activities;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.informationcollection.InformationCommentAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.base.TApplication;
import com.yieryi.gladtohear.bean.informationcollaction.commentlist.Comment;
import com.yieryi.gladtohear.bean.informationcollaction.commentlist.CommentRoot;
import com.yieryi.gladtohear.biz.maininformation.commentlist.CommentListBiz;
import com.yieryi.gladtohear.listener.JazzyRecyclerViewScrollListener;
import com.yieryi.gladtohear.listener.MakeCommentListener;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.tools.recycleviewanimation.effects.SlideInEffect;
import com.yieryi.gladtohear.view.LoadingDialog;
import java.io.IOException;
import java.util.List;
public class CommentListActivity extends BaseActivity implements MakeCommentListener{
    private final String TAG = CommentListActivity.class.getSimpleName();
    private RecyclerView comment_recycleview;
    private LoadingDialog dialog;
    private CommentListBiz commentListBiz;
    private CommentRoot root;
    private List<Comment> commentList;
    private InformationCommentAdapter adapter;
    private LinearLayoutManager manager;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://处理资讯详情请求
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    manager = new LinearLayoutManager(CommentListActivity.this);
                    comment_recycleview.setLayoutManager(manager);
                    comment_recycleview.setAdapter(adapter);
                    JazzyRecyclerViewScrollListener scroll=new JazzyRecyclerViewScrollListener();
                    comment_recycleview.addOnScrollListener(scroll);
                    scroll.setTransitionEffect(new SlideInEffect());
                    break;
                case 1:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    showToast("请求失败");
                    break;
            }
        }
    };
    @Override
    public int getLayout() {
        return R.layout.activity_comment_list;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        String content_id =getIntent().getStringExtra("content_id");
        commentListBiz=new CommentListBiz();
        if (isNetworkConnected(this)) {
            if (dialog==null) {
                dialog = new LoadingDialog(this, R.style.dialog);
            }
            if (!TextUtils.isEmpty(TApplication.user_id)) {
                dialog.show();
                commentListBiz.getCommentList("4368",TApplication.user_id,1,TAG,this);
            }else {
                dialog.show();
                commentListBiz.getCommentList("4368","0",1,TAG,this);
            }
        }else {
            showToast("网络无连接");
        }
    }

    private void initView() {
        comment_recycleview = (RecyclerView) findViewById(R.id.comment_list_recycleview);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("评价详情");
        action.setHomeButtonEnabled(isTrue);
    }
    @Override
    public void onMakeCommentResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {

                String json = response.body().string();
                json=json.replace(" ","");
                Log.e("commentlist", json);
                root=gson.fromJson(json, CommentRoot.class);
                if (root.getStatus()== OkHttp.NET_STATE) {
                    commentList = root.getData().getComment();
                    adapter = new InformationCommentAdapter(commentList, true);
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
    public void onMakeCommentFailue(Request request, IOException e) {

    }
}
