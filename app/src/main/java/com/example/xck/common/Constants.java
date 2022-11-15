package com.example.xck.common;


import com.blankj.utilcode.util.SPUtils;
import com.example.xck.bean.Login;
import com.google.gson.Gson;

/**
 * Constants
 * 沉迷学习不能自拔
 * Describe：
 * Created by 雷小星🍀 on 2018/2/26 10:55.
 */

public class Constants {
    private static final String TAG = "Constants";
    public final static String DOWNLOAD_PATH = "/sdcard/xck";                 //文件下载的路径
    public static final String KEY_SP_TOKEN = "SP_TOKEN";
    public static final String KEY_SP_HEAN = "SP_HEAN";
    public static final String KEY_SP_PERSON = "SP_Person";
    public static final String KEY_SP_CODE = "SP_CODE";
    public static final int INTENT_HOME = 0;
    public static final int INTENT_SHOPPING = 2;
    public static final String KEY_INTENT_MAIN = "INTENT_MAIN";
    public static final String KEY_INTENT_VIP = "VipID";//Vip详情页传递字段
    public static final String KEY_INTENT_MSG_ID = "msg_id";//消息id
    public static final String isExit = "isExit";//直播间页面是否退出
    public static final String KEY_DATA = "data";//Intent数据传输字段
    private static final String KEY_SP_USER = "SP_USER";
    private static final String KEY_SP_LOCATION = "SP_LOCATION";//定位信息
    private static final String KEY_SP_LOGIN = "SP_IS_LOGIN";//是否登录
    private static final String KEY_SP_IS_FIRST = "SP_IS_FIRST";//是否第一次使用当前版本App
    private static final String KEY_SP_HOME = "SP_HOME";//首页数据
    private static final String KEY_SP_IS_FIRST_LOGIN = "SP_IS_FIRST_LOGIN";//是否第一登录
    private static final String KEY_USER_PHONE="SP_USER_PHONE";//手机号
    private static final String KEY_SP_SEARCH="KEY_SP_SEARCH";//搜索记录
    private static final String KEY_UNREAD_MESSAGE = "UNREAD_MESSAGE";//未读消息
    private static final String TISHI_MESSAGE= "TISHI_MESSAGE";//新消息提示
    private static final String PW_HIDE= "PW_HIDE";//密码隐藏和显示
    public static final String UPLOADFILEPATH= "UPLOADFILEPATH";
    //滑动关闭Activity
    public static final int ACTIVITY_FINISH_REQUEST_CODE = 10000;
    public static final int ACTIVITY_FINISH_RESULT_CODE = 10001;

    //接口Code
    public static final String LONG_TOKEN_INVALID = "30001";
    public static final String LONG_TOKEN_INVALID_ = "30002";
    public static final String SHORT_TOKEN_INVALID = "30003";
    public static final String SHORT_TOKEN_INVALID_ = "30004";
    public static final String BASE_URL = "http://api-test.xck6666.com/";
    public final static String SELECT_FILE_SUFFIX = ".PDF;.pdf";
    /*private static UserCenterBean userData;







    public static UserCenterBean getUserData() {
        return userData;
    }

    public static void setUserData(UserCenterBean userData) {
        Constants.userData = userData;
    }




    *//**
     * 用户是否登录
     */
    public static boolean isLogin() {
        return SPUtils.getInstance().getBoolean(KEY_SP_LOGIN);
    }

    /**
     * 登录
     */
    public static void login() {
        SPUtils.getInstance().put(KEY_SP_LOGIN, true);
    }

    /**
         * 密码是否隐藏
     */
    public static boolean isShow() {
        return SPUtils.getInstance().getBoolean(PW_HIDE);
    }
    public static void setShow(boolean isShow) {
        SPUtils.getInstance().put(PW_HIDE, isShow);
    }
    /**
     * 登出
     */
    public static void loginOut() {
        SPUtils.getInstance().put(KEY_SP_LOGIN, false);
    }
    /**
     * 设置新消息提示
     */
    public static void setTishiMessage(Boolean tishi) {
        SPUtils.getInstance().put(TISHI_MESSAGE, tishi);
    }

    /**
     * 登出
     */
    public static Boolean getTishiMessage() {
        return SPUtils.getInstance().getBoolean(TISHI_MESSAGE, false);
    }


    /**
     * 是否是第一次使用
     */
    public static boolean isFirst() {
        return SPUtils.getInstance().getBoolean(KEY_SP_IS_FIRST, true);
    }


    /**
     * 是否有支付密码
     *
     * @return true 有支付密码  要改密码 需要输入旧密码
     * <p>
     * false 没有支付密码    首次设置传验证码
     */
    public static boolean hasPayPassword() {
        return false;
    }

    /**
     * 设置为非首次使用app
     */
    public static void setNotFirst() {
        SPUtils.getInstance().put(KEY_SP_IS_FIRST, false);
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {
        String loginJson = SPUtils.getInstance().getString(KEY_SP_TOKEN);
        return loginJson;
    }
    /**
     * 储存Personal
     *
     * @param
     */
    public static void putPersonal(Login.UserInfoBean loginEntity) {
        SPUtils.getInstance().put(KEY_SP_PERSON, new Gson().toJson(loginEntity));
    }
    /**
     * 储存Personal
     *
     * @param
     */
    public static Login.UserInfoBean getPersonal( ) {
        String per=SPUtils.getInstance().getString(KEY_SP_PERSON);
        return new Gson().fromJson(per, Login.UserInfoBean.class);
    }
    /**
     * 储存token
     *
     * @param
     */
    public static void putToken(String  token) {
        SPUtils.getInstance().put(KEY_SP_TOKEN, token);
    }
    /**
     * 获取isExit
     *
     * @return
     */
    public static int getIsExit() {
        int loginJson = SPUtils.getInstance().getInt(isExit,0);
        return loginJson;
    }
    public static void putIsExit(int  exit) {
        SPUtils.getInstance().put(isExit,exit,true);
    }

    /**
     * 获取头像
     *
     * @return
     */
    public static String getHeadImg() {
        String loginJson = SPUtils.getInstance().getString(KEY_SP_HEAN);
        return loginJson;
    }

    /**
     * 储存头像
     *
     * @param
     */
    public static void putHeadImg(String  img) {
        SPUtils.getInstance().put(KEY_SP_HEAN, img);
    }
    /**
         * 获取im
         *
         * @return
         */
        public static String getImIndent() {
            String loginJson = SPUtils.getInstance().getString("im_identifier");
            return loginJson;
        }

        /**
         * 储存im
         *
         * @param
         *//*
        public static void putImIndent(String  img) {
            SPUtils.getInstance().put("im_identifier", img);
    }
    *//**
     * 获取im_user_sig
     *
     * @return
     *//*
    public static String getImuser() {
        String loginJson = SPUtils.getInstance().getString("im_user_sig");
        return loginJson;
    }
    *//**
     * 添加未读消息数量
     *//*
    public static void addUnReadMessage() {
        SPUtils.getInstance().put(KEY_UNREAD_MESSAGE, getUnReadMessage() + 1);
    }

    *//**
     * 获取未读消息数量
     *//*
    public static int getUnReadMessage() {
        return SPUtils.getInstance().getInt(KEY_UNREAD_MESSAGE, 0);
    }

    *//**
     * 储存im_user_sig
     *
     * @param
     *//*
    public static void putImuser(String  img) {
        SPUtils.getInstance().put("im_user_sig", img);
    }

    *//**
     * 储存Personal
     *
     * @param
     *//*
    public static void putPersonal(PersonInfo.InfoBean loginEntity) {
        SPUtils.getInstance().put(KEY_SP_PERSON, new Gson().toJson(loginEntity));
    }
    *//**
     * 储存Personal
     *
     * @param
     *//*
    public static PersonInfo.InfoBean getPersonal( ) {
        String per=SPUtils.getInstance().getString(KEY_SP_PERSON);
        return new Gson().fromJson(per, PersonInfo.InfoBean.class);
    }


    *//**
     * 获取邀请码
     *
     * @return
     *//*
    public static String getUserId() {
        String code = SPUtils.getInstance().getString(KEY_SP_CODE);
        return code;
    }

    *//**
     * 储存邀请码
     *
     * @param
     *//*
    public static void putUserId(String  code) {
        SPUtils.getInstance().put(KEY_SP_CODE, code);
    }
    *//**
     * 获取搜索记录
     *//*
    public static String getSearch() {
        return SPUtils.getInstance().getString(KEY_SP_SEARCH);
    }
    *//**
     * 储存搜索记录
     *//*
    public static void putSearch(String search) {
        SPUtils.getInstance().put(KEY_SP_SEARCH,search);
    }

    *//**
     * 获取用户手机号
     * @return
     *//*
    public static String getPhone(){
       return SPUtils.getInstance().getString(KEY_USER_PHONE);
    }

    public static void putPhone(String phone){
        SPUtils.getInstance().put(KEY_USER_PHONE,phone);
    }



    *//**
     * 获取是否第一次登录
     *
     * @return
     *//*
    public static boolean isFirstLogin() {
        return SPUtils.getInstance().getBoolean(KEY_SP_IS_FIRST_LOGIN, true);
    }

    *//**
     * 储存是否第一次登录
     *
     * @param isFirstLogin
     *//*
    public static void setIsFirstLogin(boolean isFirstLogin) {
        SPUtils.getInstance().put(KEY_SP_IS_FIRST_LOGIN, isFirstLogin);
    }


    *//**
     * 获取Host
     *
     * @return
     *//*
    public static String getBaseUrl() {
        return Retrofit2Manager.Companion.getInstance().getApiConfigProvider().getDebugHost();
    }

    *//**
     * 存入经纬信息
     *//*
    public static void putLocation(double latitude, double longitude, String city) {
        SPUtils.getInstance().put(Constants.KEY_SP_LOCATION, latitude + "," + longitude + "," + city);
    }

    *//**
     * 获取经纬信息
     *
     * @return
     *//*
    public static String[] getLocation() {
        return SPUtils.getInstance()
                .getString(Constants.KEY_SP_LOCATION)
                .split(",");
    }*/
}
