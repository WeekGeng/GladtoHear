package com.yieryi.gladtohear.biz.userspace.listrecord;

import com.yieryi.gladtohear.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public interface IListRecordBiz {
    void getListRecord(String page,String user_id,String device_no,String tag,RequestListener listener);
}
