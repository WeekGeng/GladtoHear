package com.yieryi.gladtohear.biz.calculate;

import com.yieryi.gladtohear.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/26 0026.
 */
public interface IMarcketSelBiz {
    public void getMacketList(int parentId,int city_id,String tag,final RequestListener listener);
}
