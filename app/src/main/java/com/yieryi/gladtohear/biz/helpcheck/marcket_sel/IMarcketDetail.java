package com.yieryi.gladtohear.biz.helpcheck.marcket_sel;

import com.yieryi.gladtohear.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/29 0029.
 */
public interface IMarcketDetail {
    void getMacketList(String keyword,String search_type,String page,String local,String pid,String tag,RequestListener listener);
}
