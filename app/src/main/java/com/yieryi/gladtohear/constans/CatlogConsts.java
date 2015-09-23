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
    /**
     * user space prize_exchange
     */
    public class PrizeExchangeCode{
        public static final String params_app = "cas";
        public static final String params_class = "code";
        public static final String params_sign = "3cb050df47628de032f63fac8d3eb2bf";
    }
    /**
     * 获取帮你查热门
     */
    public class HelpCheckHot{
        public static final String params_app = "shop";
        public static final String params_class = "hotsearch";
        public static final String params_sign = "a75786dc420a30e8a018b3882e663c5e";
    }
    /**
     * 获取短信验证码
     */
    public class GetMailCode{
        public static final String params_app = "cas";
        public static final String params_class = "code";
        public static final String params_sign = "3cb050df47628de032f63fac8d3eb2bf";
    }
    /**
     * 用户注册
     */
    public class Regist{
        public static final String params_app = "cas";
        public static final String params_class = "in";
        public static final String params_sign = "62fa87ace6f05416fed79e020fb9661c";
    }
}
