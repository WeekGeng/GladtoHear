package com.yieryi.gladtohear.biz.maininformation;

import com.yieryi.gladtohear.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public interface IInfomationDetailBiz {
    void getDetailInformation(String content_id,String userid,String device_type,String tag,RequestListener listener);
}
