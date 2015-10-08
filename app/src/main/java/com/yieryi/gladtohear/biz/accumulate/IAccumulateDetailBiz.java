package com.yieryi.gladtohear.biz.accumulate;
import com.yieryi.gladtohear.listener.RequestListener;
public interface IAccumulateDetailBiz {
    void getDetail(String content_id,String tag,RequestListener listener);
}
