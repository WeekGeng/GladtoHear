package com.yieryi.gladtohear.biz.helpcheck.marcket_sel.searchlist;

import com.yieryi.gladtohear.listener.RequestListener;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public interface IBrandSearchListBiz {
    void getSearchList(String keyword,String search_type,String page,String local,RequestListener listener);
}
