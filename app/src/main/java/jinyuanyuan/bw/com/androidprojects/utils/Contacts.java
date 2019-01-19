package jinyuanyuan.bw.com.androidprojects.utils;

/* 管理接口 */
public class Contacts {
    //总接口前缀
    public static final String BASE_URL = "http://172.17.8.100/";
    // 用户相关接口  http://172.17.8.100/small/user/v1/register
    //http://172.17.8.100/small/commodity/v1/bannerShow
    //mobile.bwstudent.com
    //1.注册
    public static final String REGISTER = "small/user/v1/register";

    //2.登录
    public static final String LOGIN = "small/user/v1/login";

    //6.根据用户ID查询用户信息
    public static final String GET_USER_BY_ID = "small/user/verify/v1/getUserById";

    //7.收货地址列表
    public static final String RECEIVE_ADDRESS_LIST = "small/user/verify/v1/receiveAddressList";

    //8.新增收获地址
    public static final String ADD_RECEIVEADDRESS = "small/user/verify/v1/addReceiveAddress";

    //9.设置默认收获地址
    public static final String SET_DEFAULT_RECEIVE_ADDRESS = "small/user/verify/v1/setDefaultReceiveAddress";

    //10.修改收获信息
    public static final String CHANGE_RECEIVE_ADDRESS = "small/user/verify/v1/changeReceiveAddress";

    //11.查询用户钱包
    public static final String FIND_USER_WALLET = "small/user/verify/v1/findUserWallet";

    //12.banner展示列表
    public static final String XBANNER = "small/commodity/v1/bannerShow";

    //13.首页商品信息列表
    public static final String COMMODITYLIST_URL = "small/commodity/v1/commodityList";

    //14.圈子列表
    public static final String FINDCIRCLELIST_URL = "small/circle/v1/findCircleList";

    //15.圈子点赞
    public static final String ADD_CICLE_GREAT_URL = "small/circle/verify/v1/addCircleGreat";

    //16.取消点赞
    public static final String CACEL_CIRCLE_GREAT_URL = "small/circle/verify/v1/cancelCircleGreat";

    //17.根据关键词查询商品信息
    public static final String FIND_COMMODITY_BY_KEYWORD_URL = "small/commodity/v1/findCommodityByKeyword";

    //18.查询购物车
    public static final String  FIND_SHOPINGCART= "small/order/verify/v1/findShoppingCart";

    //19 修改昵称
    public static final String MODIFY_USER_NICK_URL = "small/user/verify/v1/modifyUserNick";

    //20 商品详情
    public static final String FIND_COMMODITY_DETAILSBYID = "small/commodity/v1/findCommodityDetailsById";

    //21 同步购物车
    public static final String SYNC_SHOPPING_CART = "small/order/verify/v1/syncShoppingCart";
    //22 我的足迹
    public static final String BROWSELIST_URL = "small/commodity/verify/v1/browseList";
    //23 修改密码
    public static final String MODIFY_USERPWD_URL= "small/user/verify/v1/modifyUserPwd";
    //
    public static final String CREATEORDER =  "small/order/verify/v1/createOrder";
}
