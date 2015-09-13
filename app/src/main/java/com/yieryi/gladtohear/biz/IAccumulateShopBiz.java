package com.yieryi.gladtohear.biz;

import com.yieryi.gladtohear.bean.accumulate.Root;
import com.yieryi.gladtohear.listener.RequestListener;

import java.util.List;

/**
 * 积分商城获取接口
 */
public interface IAccumulateShopBiz {
    /**
     * 获取分类
     * @param category 商品类别ID 38
     * @param contion 分类1.exchange_num(兑换次数) 2.ctime(时间) 3.exchange_integral(积分)
     * @param contion 排序  desc降序 asc升序
     * @return
     */
    void getProductsList(int category,int contion,String sort,RequestListener listener);
}
