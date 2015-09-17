package com.yieryi.gladtohear.constans;

/**
 * Created by Administrator on 2015/9/9.
 */
public class CatlogConsts {

    /**
     * 获取超市
     */
    public class GetMarket{
        public static final String params_app="shop";
        public static final String params_class="getaddress";
        public static final String params_sign="e4bd9c59021dc095736ef5d44d202e70";
    }
    /**
     * 根据四个名称获得资讯汇总
     */
    public class GetInfomation{
        public static final String params_app="news";
        public static final String params_class="getnewsbycategory";
        public static final String params_sign="4e4e0cf81c2c8692f67456d80befa82d";
    }

    /**
     * 我的订阅
     */
    public class MyReader {
        public static final String params_app = "cas";
        public static final String params_class = "getusertaglist";
        public static final String params_sign = "26f53261ff04d4dda4359260205cf0d1";
    }
    /**
     * 积分商城
     */
    public class Accumulation{
        public static final String params_app = "product";
        public static final String params_class = "getproductsbystatus";
        public static final String params_sign = "a8f9422aabcc6c995f5e55db3185b5d3";
    }

    /**
     * main 品牌资讯列表
     */
    public class Brand{
        public static final String params_app = "news";
        public static final String params_class = "getnewslist";
        public static final String params_sign = "1b84c7f0ad96051add15393009b77ae0";
    }
}
