package com.yieryi.gladtohear.biz.maininformation.commentlist;
import com.yieryi.gladtohear.listener.MakeCommentListener;
public interface ICommentListBiz {
    void getCommentList(String content_id,String userid,int page,String tag,MakeCommentListener listener);
}
