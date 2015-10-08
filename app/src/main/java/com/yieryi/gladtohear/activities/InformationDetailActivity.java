package com.yieryi.gladtohear.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yieryi.gladtohear.R;
import com.yieryi.gladtohear.adapter.informationcollection.InformationCommentAdapter;
import com.yieryi.gladtohear.base.BaseActivity;
import com.yieryi.gladtohear.base.TApplication;
import com.yieryi.gladtohear.bean.informationcollaction.commentlist.Comment;
import com.yieryi.gladtohear.bean.informationcollaction.commentlist.CommentRoot;
import com.yieryi.gladtohear.bean.informationcollaction.commentlist.User;
import com.yieryi.gladtohear.bean.informationcollaction.detail.Content;
import com.yieryi.gladtohear.bean.informationcollaction.detail.Root;
import com.yieryi.gladtohear.biz.maininformation.InformationDetailBiz;
import com.yieryi.gladtohear.biz.maininformation.commentlist.CommentListBiz;
import com.yieryi.gladtohear.listener.MakeCommentListener;
import com.yieryi.gladtohear.listener.RequestListener;
import com.yieryi.gladtohear.network.OkHttp;
import com.yieryi.gladtohear.overridge.MyGridLayoutManager;
import com.yieryi.gladtohear.view.LoadingDialog;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class InformationDetailActivity extends BaseActivity implements RequestListener,MakeCommentListener{
    private final String TAG = InformationDetailActivity.class.getSimpleName();
    private TextView information_detail_title_tv,information_detail_author_tv,information_detail_time_tv;
    private RecyclerView information_detai_recycle;
    private WebView information_detail_web;
    private TextView information_detail_more_comment_iv;
    private String content_id;
    private LoadingDialog dialog;
    private Content content;
    private InformationDetailBiz informationDetailBiz;
    private CommentListBiz commentListBiz;
    private Comment comment,comment1;
    private InformationCommentAdapter adapter;
    private List<Comment> list=new ArrayList<Comment>();
    private MyGridLayoutManager manager;
    private CommentRoot root;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://处理资讯详情请求
                    information_detail_title_tv.setText(content.getTitle());
                    information_detail_author_tv.setText(content.getAuthor());
                    information_detail_time_tv.setText(content.getCtime());
                    information_detail_web.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
                    information_detail_web.loadData(content.getContentbody(), "text/html;charset=UTF-8", null);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
                case 1:
                    break;
                case 2:
                    manager = new MyGridLayoutManager(InformationDetailActivity.this,1);
                    information_detai_recycle.setLayoutManager(manager);
                    information_detai_recycle.setAdapter(adapter);
                    break;
                case 10:
                    information_detail_more_comment_iv.setText("没有更多评论");
                    information_detail_more_comment_iv.setClickable(false);
                    break;
            }
        }
    };
    private List<Comment> commentList;

    @Override
    public int getLayout() {
        return R.layout.activity_information_detail;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        content_id=getIntent().getStringExtra("content_id");
        Log.e("content_id", content_id);
        initView();
        setListener();
        if (isNetworkConnected(this)){
            if (!TextUtils.isEmpty(content_id)) {
                if (dialog == null) {
                    dialog = new LoadingDialog(this, R.style.dialog);
                    dialog.show();
                    informationDetailBiz = new InformationDetailBiz();
                    commentListBiz=new CommentListBiz();
                    if (TApplication.user_id == null){
                        informationDetailBiz.getDetailInformation(content_id,"0",JPushInterface.getRegistrationID(this),TAG,this);
                        commentListBiz.getCommentList(content_id,"0",1,TAG,this);
                    }else {
                        informationDetailBiz.getDetailInformation(content_id,TApplication.user_id, JPushInterface.getRegistrationID(this),TAG,this);
                        commentListBiz.getCommentList("4368", TApplication.user_id, 1, TAG, this);
                    }
                }
            }
        }else {
            showToast("网络无连接");
        }
    }

    private void setListener() {
        information_detail_more_comment_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(InformationDetailActivity.this,CommentListActivity.class,"content_id",content_id);
            }
        });
    }

    private void initView() {
        information_detai_recycle = (RecyclerView) findViewById(R.id.information_detai_recycle);
        information_detail_web = (WebView) findViewById(R.id.information_detail_web);
        information_detail_more_comment_iv = (TextView) findViewById(R.id.information_detail_more_comment_iv);
        information_detail_title_tv = (TextView) findViewById(R.id.information_detail_title_tv);
        information_detail_author_tv = (TextView) findViewById(R.id.information_detail_time_tv);
        information_detail_time_tv = (TextView) findViewById(R.id.information_detail_time_tv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_information_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.share:

                break;
            case R.id.collect:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setHomeButtonEnabled(isTrue);
        action.setTitle("资讯详情");
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {
                String json = response.body().string();
                json=json.replace(" ","");
                Log.e("information_detail", json);
                Root root = gson.fromJson(json, Root.class);
                if (root.getStatus() == OkHttp.NET_STATE) {
                    content = root.getData().getContent();
                    handler.sendEmptyMessage(0);
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
    public void onMakeCommentResponse(Response response) {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            try {

                String json = response.body().string();
                json=json.replace(" ","");
                Log.e("commentlist",json);
                root=gson.fromJson(json, CommentRoot.class);
                if (root.getStatus()==OkHttp.NET_STATE) {
                    commentList = root.getData().getComment();
                    if (commentList == null) {
                        handler.sendEmptyMessage(10);
                    } else {
                        if (commentList.size() == 1) {
                            list.add(commentList.get(0));
                        } else {
                            list.add(commentList.get(0));
                            list.add(commentList.get(1));
                        }
                        adapter = new InformationCommentAdapter(list, false);
                        handler.sendEmptyMessage(2);
                    }
                }else {

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
