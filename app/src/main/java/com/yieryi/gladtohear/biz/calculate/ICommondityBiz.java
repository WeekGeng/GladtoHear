package com.yieryi.gladtohear.biz.calculate;

import com.yieryi.gladtohear.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/26 0026.
 */
public interface ICommondityBiz {
    void getCommodityList(int parentId,String shop_id,String tag,final RequestListener listener);
}
