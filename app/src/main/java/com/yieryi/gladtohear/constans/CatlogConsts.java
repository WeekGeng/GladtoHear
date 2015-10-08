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
     * 获取超市搜索列表
     */
    public class GetMarketSearchList{
        public static final String params_app="shop";
        public static final String params_class="searchlist";
        public static final String params_sign="bb28a82b860ca10c420934463d267666";
    }
    /**
     * 获取分类
     */
    public class GetCommodity{
        public static final String params_app="shop";
        public static final String params_class="getcategory";
        public static final String params_sign="b595c72c94df9ed88d9a530cf17d9d47";
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
     * 积分商城详细
     */
    public class AccumulationDetail{
        public static final String params_app = "product";
        public static final String params_class = "getproductsbyid";
        public static final String params_sign = "0719e21500a5ebd4bdd32b4455fd330a";
    }

    /**
     * mains 品牌资讯列表
     */
    public class Brand{
        public static final String params_app = "news";
        public static final String params_class = "getnewslist";
        public static final String params_sign = "1b84c7f0ad96051add15393009b77ae0";
    }
    /**
     * mains 品牌资讯列表详情
     */
    public class InformationDetail{
        public static final String params_app = "news";
        public static final String params_class = "getnewscontent";
        public static final String params_sign = "64dd7e40f27f959880b57415f1c3d8a5";
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
     * user space lsit_record
     */
    public class UserSpaceListRecord{
        public static final String params_app = "shop";
        public static final String params_class = "getorders";
        public static final String params_sign = "42e555d2dbba6b9da67924e7fa871fdf";
    }
    /**
     * user space exchange_record
     */
    public class UserSpaceExchangeRecord{
        public static final String params_app = "product";
        public static final String params_class = "getexchange";
        public static final String params_sign = "ef8187b36dce1d53deb7bd8bcef9d250";
    }
    /**
     * user space exchange_add_record
     */
    public class UserSpaceAddExchangeRecord{
        public static final String params_app = "product";
        public static final String params_class = "addexchange";
        public static final String params_sign = "3c200e636334989a6ebc8c54a524dac7";
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
    /**
     * 评论列表
     */
    public class CommentList{
        public static final String params_app = "comment";
        public static final String params_class = "getcommentlist";
        public static final String params_sign = "e9d1ce83c2c6b1ecc74f0b0212145f96";
    }
}
