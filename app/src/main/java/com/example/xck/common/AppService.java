package com.example.xck.common;



public interface AppService {
   /* *//**
     * 首页轮播
     *//*
    @POST("Theme/getImages")
    Observable<BaseResponseEntity<List<FirstImage>>> getFirstImage();

    *//**
     * 首页一级分类
     *//*
    @POST("Theme/getCategorys")
    Observable<BaseResponseEntity<List<Category>>> getCategory();

    *//**
     * 首页商品列表
     *//*
    @FormUrlEncoded
    @POST("Theme/goods_list")
    Observable<BaseResponseEntity<Goods>> getGoodList(@Field("cat_id") int cat_id, @Field("page") int page, @Field("sort") String sort);

    *//**
     * 商品详情
     *//*
    @FormUrlEncoded
    @POST("goods/goods_detail")
    Observable<BaseResponseEntity<GoodsDetail>> getGoodsDetail(@Header("token") String token, @Field("goods_sn") String goods_sn);

    *//**
     * 标签说明
     *//*
    @FormUrlEncoded
    @POST("goods/getLabel")
    Observable<BaseResponseEntity<Label>> getLabel(@Field("label_id") String label_id);

    *//**
     * 获取产品图片
     *//*
    @FormUrlEncoded
    @POST("goods/goods_images")
    Observable<BaseResponseEntity<ImageDetail>> getImageDetail(@Field("goods_id") String goods_id);

    *//**
     * 搜索
     *//*
    @FormUrlEncoded
    @POST("search/search")
    Observable<BaseResponseEntity<SearchResult>> getResult(@Field("keyword") String keyword);

    *//**
     * 搜索页面
     *//*
    @POST("search/search_index")
    Observable<BaseResponseEntity<Search>> setSearch();

    *//**
     * 发送验证码
     *//*
    @FormUrlEncoded
    @POST("user_api/sendSMS")
    Observable<BaseResponseEntity<Object>> getCode(@Field("phone") String phone);

    *//**
     * 用户注册
     *//*
    @FormUrlEncoded
    @POST("user_api/register")
    Observable<BaseResponseEntity<Object>> register(@Field("phone") String phone, @Field("phone_code") String phone_code, @Field("pwd") String pwd, @Field("pwds") String pwds, @Field("code") String code);

    *//**
     * 密码登录
     *//*
    @FormUrlEncoded
    @POST("user_api/login")
    Observable<BaseResponseEntity<Login>> pwLogin(@Field("phone") String phone, @Field("password") String password);

    *//**
     * 判断是否设置交易密码
     *//*
    @POST("user_center/check_trade_password")
    Observable<BaseResponseEntity<IsSettingPayPW>> isSettingPayPW(@Header("token") String token);

    *//**
     * 微信登录
     *//*
    @FormUrlEncoded
    @POST("user_api/wx_login")
    Observable<BaseResponseEntity<Login>> threeLogin(@Field("openid") String openid);

    *//**
     * 绑定手机号
     *//*
    @FormUrlEncoded
    @POST("user_api/bind_phone")
    Observable<BaseResponseEntity<Login>> bindPhone(@Field("openid") String openid, @Field("phone") String phone, @Field("phone_code") String phone_code, @Field("code") String code, @Field("nickname") String nickname, @Field("head_img") String head_img, @Field("is_bind") String is_bind);

    *//**
     * 快捷登录
     *//*
    @FormUrlEncoded
    @POST("user_api/sms_login")
    Observable<BaseResponseEntity<Login>> codeLogin(@Field("phone") String phone, @Field("phone_code") String phone_code);

    *//**
     * 获取个人信息
     *//*
    @POST("user_center/getUserInfo")
    Observable<BaseResponseEntity<PersonInfo>> personInfo(@Header("token") String token);

    *//**
     * 修改昵称
     *//*
    @FormUrlEncoded
    @POST("User_center/set_nickname")
    Observable<BaseResponseEntity<LiveSign>> setNickName(@Header("token") String token, @Field("nickname") String nickname);

    *//**
     * 设置登录密码
     *//*
    @FormUrlEncoded
    @POST("user_center/setPassword")
    Observable<BaseResponseEntity<Object>> setPW(@Header("token") String token, @Field("phone_code") String phone_code, @Field("password") String password);

    *//**
     * 设置交易密码
     *//*
    @FormUrlEncoded
    @POST("User_center/setTradePassword")
    Observable<BaseResponseEntity<Object>> setPayPW(@Header("token") String token, @Field("phone_code") String phone_code, @Field("password") String password);

    *//**
     * 修改手机号码
     *//*
    @FormUrlEncoded
    @POST("User_center/change_phone")
    Observable<BaseResponseEntity<Object>> setPhone(@Header("token") String token, @Field("phone_code") String phone_code, @Field("phone") String phone);

    *//**
     * 收货地址
     *//*
    @POST("Receive/address_list")
    Observable<BaseResponseEntity<Address>> getAddress(@Header("token") String token);

    *//**
     * 添加收货地址
     *//*
    @FormUrlEncoded
    @POST("Receive/add_address")
    Observable<BaseResponseEntity<Object>> addAddress(@Header("token") String token, @Field("receiver") String receiver, @Field("phone") String phone, @Field("address") String address);

    *//**
     * 编辑收货地址
     *//*
    @FormUrlEncoded
    @POST("Receive/edit_address")
    Observable<BaseResponseEntity<Object>> editAddress(@Header("token") String token, @Field("address_id") String address_id, @Field("receiver") String receiver, @Field("phone") String phone, @Field("address") String address);

    *//**
     * 设置默认地址
     *//*
    @FormUrlEncoded
    @POST("Receive/set_default")
    Observable<BaseResponseEntity<Object>> SettingAddress(@Header("token") String token, @Field("address_id") String address_id);

    *//**
     * 删除地址
     *//*
    @FormUrlEncoded
    @POST("Receive/dele_address")
    Observable<BaseResponseEntity<Object>> DelAddress(@Header("token") String token, @Field("address_id") String address_id);

    *//**
     * 忘记密码
     *//*
    @FormUrlEncoded
    @POST("user_api/forgetPwd")
    Observable<BaseResponseEntity<Object>> forgetPW(@Field("phone") String phone, @Field("phone_code") String phone_code, @Field("pwd") String pwd);

    *//**
     * 我的资产
     *//*
    @POST("user_center/assets")
    Observable<BaseResponseEntity<Assert>> getMyAssert(@Header("token") String token);

    *//**
     * 积分明细
     *//*
    @POST("user_center/user_points")
    Observable<BaseResponseEntity<PointDetail>> getPointDetail(@Header("token") String token);

    *//**
     * 积分详情
     *//*
    @FormUrlEncoded
    @POST("user_center/point_detail")
    Observable<BaseResponseEntity<IntergrationDetail>> getIntergrationDetail(@Header("token") String token, @Field("up_id") String up_id);

    *//**
     * 翡翠柜
     *//*
    @POST("user_center/cabinet")
    Observable<BaseResponseEntity<Emeralds>> getEmeralds(@Header("token") String token);

    *//**
     * 翡翠柜详情
     *//*
    @FormUrlEncoded
    @POST("user_center/cabinet_detail")
    Observable<BaseResponseEntity<EmeraldsDetail>> getEmeraldsDetail(@Header("token") String token, @Field("ct_id") String ct_id);

    *//**
     * 兑换积分
     *//*
    @FormUrlEncoded
    @POST("user_center/exchange")
    Observable<BaseResponseEntity<Object>> exchange(@Header("token") String token, @Field("ct_id") String ct_id);

    *//**
     * 获取转账用户信息
     *//*
    @FormUrlEncoded
    @POST("user_center/user_account")
    Observable<BaseResponseEntity<UserInfo>> getAccountInfo(@Header("token") String token, @Field("phone") String phone);

    *//**
     * 积分转账
     *//*
    @POST("user_center/account_info")
    Observable<BaseResponseEntity<AccountIinfo>> getAccount(@Header("token") String token);

    *//**
     * 转账积分
     *//*
    @FormUrlEncoded
    @POST("user_center/transfer_account")
    Observable<BaseResponseEntity<Object>> push(@Header("token") String token, @Field("to_user") String to_user, @Field("points") String points, @Field("fp_id") String fp_id, @Field("trade_password") String trade_password);

    *//**
     * 修改头像
     *//*
    @FormUrlEncoded
    @POST("User_center/modify_headImg")
    Observable<BaseResponseEntity<HandImage>> setHandImage(@Header("token") String token, @Field("head_img") String head_img);

    *//**
     * 消息列表
     *//*
    @POST("message/msg_list")
    Observable<BaseResponseEntity<MyMessage>> getMessage(@Header("token") String token);

    *//**
     * 消息详情
     *//*
    @FormUrlEncoded
    @POST("message/msg_detail")
    Observable<BaseResponseEntity<MessageDetail>> getMessageDetail(@Header("token") String token, @Field("msg_id") String msg_id);

    *//**
     * 常见问题分类
     *//*
    @POST("common/problem_type")
    Observable<BaseResponseEntity<AskType>> getAskType();

    *//**
     * 常见问题
     *//*
    @FormUrlEncoded
    @POST("common/common_problem")
    Observable<BaseResponseEntity<Ask>> getAsk(@Field("pt_id") String pt_id);

    *//**
     * 常见问题答案
     *//*
    @FormUrlEncoded
    @POST("common/answer")
    Observable<BaseResponseEntity<AskDetail>> getAskDetail(@Field("pt_id") String pt_id);

    *//**
     * 入驻协议
     *//*
    @POST("user_center/enter_protocol")
    Observable<BaseResponseEntity<Note>> getNote(@Header("token") String token);

    *//**
     * 入驻申请记录列表
     *//*
    @POST("user_center/apply_list")
    Observable<BaseResponseEntity<Record>> getRecord(@Header("token") String token);

    *//**
     * 商家申请记录详情
     *//*
    @FormUrlEncoded
    @POST("user_center/apply_record")
    Observable<BaseResponseEntity<RecordDetail>> getRecordDetail(@Header("token") String token, @Field("supplier_id") String supplier_id);

    *//**
     * 入驻申请
     *//*
    @FormUrlEncoded
    @POST("user_center/enter_apply")
    Observable<BaseResponseEntity<Object>> apply(@Header("token") String token, @Field("company_name") String company_name, @Field("email") String email, @Field("phone") String phone, @Field("inviter") String inviter, @Field("license") String license, @Field("attach") String attach, @Field("supplier_name") String supplier_name, @Field("head_img") String head_img, @Field("intro") String intro);

    *//**
     * 意见反馈
     *//*
    @FormUrlEncoded
    @POST("user_center/feedback")
    Observable<BaseResponseEntity<Object>> getFeedBack(@Header("token") String token, @Field("content") String content);

    *//**
     * 上传图片
     *//*
    @FormUrlEncoded
    @POST("Common/imgUp")
    Observable<BaseResponseEntity<Image>> getImage(@Field("upload_img") String upload_img);

    *//**
     * 收藏列表
     *//*
    @POST("user_center/collect_list")
    Observable<BaseResponseEntity<Collection>> getCollection(@Header("token") String token);

    *//**
     * 收藏
     *//*
    @FormUrlEncoded
    @POST("Collect/collect")
    Observable<BaseResponseEntity<Object>> collection(@Header("token") String token, @Field("goods_id") String goods_id);

    *//**
     * 订单列表
     *//*
    @FormUrlEncoded
    @POST("Order/order_list")
    Observable<BaseResponseEntity<Order>> getOrder(@Header("token") String token, @Field("order_status") String order_status);

    *//**
     * 订单详情
     *//*
    @FormUrlEncoded
    @POST("Order/order_detail")
    Observable<BaseResponseEntity<OrderDetail>> getOrderDetail(@Header("token") String token, @Field("order_id") String order_id);

    *//**
     * 确认收货
     *//*
    @FormUrlEncoded
    @POST("Order/confirm_order")
    Observable<BaseResponseEntity<Object>> sureDetail(@Header("token") String token, @Field("order_id") String order_id);

    *//**
     * 取消订单
     *//*
    @FormUrlEncoded
    @POST("Order/cancel_order")
    Observable<BaseResponseEntity<Object>> exitDetail(@Header("token") String token, @Field("order_id") String order_id);

    *//**
     * 申请售后
     *//*
    @FormUrlEncoded
    @POST("Order/returns")
    Observable<BaseResponseEntity<Object>> ApplyCus(@Header("token") String token, @Field("order_id") String order_id, @Field("goods_id") String goods_id, @Field("reason") String reason, @Field("desc") String desc, @Field("refunds") String refunds);

    *//**
     * 售后列表
     *//*
    @POST("Order/returns_list")
    Observable<BaseResponseEntity<Customer>> getCustomer(@Header("token") String token);

    *//**
     * 售后详情
     *//*
    @FormUrlEncoded
    @POST("Order/returns_detail")
    Observable<BaseResponseEntity<CustiomerDetail>> customerDetail(@Header("token") String token, @Field("as_id") String as_id);

    *//**
     * 提交邮寄信息
     *//*
    @FormUrlEncoded
    @POST("Order/send_back")
    Observable<BaseResponseEntity<Object>> sendBack(@Header("token") String token, @Field("as_id") String as_id, @Field("shipping_name") String shipping_name, @Field("shipping_code") String shipping_code);

    *//**
     * 购物袋信息
     *//*
    @POST("shopp/shopp_bag")
    Observable<BaseResponseEntity<Shop>> shopping(@Header("token") String token);

    *//**
     * 删除购物袋商品
     *//*
    @FormUrlEncoded
    @POST("shopp/dele_shopp")
    Observable<BaseResponseEntity<Object>> delShopping(@Header("token") String token, @Field("sb_id") String sb_id);

    *//**
     * 加入购物袋
     *//*
    @FormUrlEncoded
    @POST("Shopp/addShopp")
    Observable<BaseResponseEntity<Object>> addShopping(@Header("token") String token, @Field("goods_id") String goods_id);

    *//**
     * 购物袋提示
     *//*
    @POST("shopp/shopp_tips")
    Observable<BaseResponseEntity<Shop>> updateShopping(@Header("token") String token);

    *//**
     * 我的团队跟二级团队
     *//*
    @FormUrlEncoded
    @POST("user_center/distribute")
    Observable<BaseResponseEntity<Team>> team(@Header("token") String token, @Field("user_id") String user_id);

    *//**
     * 翡翠积分收益
     *//*
    @FormUrlEncoded
    @POST("user_center/profit")
    Observable<BaseResponseEntity<Profit>> profit(@Header("token") String token, @Field("incr_id") String incr_id, @Field("goods_id") String goods_id);

    *//**
     * 提货
     *//*
    @FormUrlEncoded
    @POST("user_center/pick_up")
    Observable<BaseResponseEntity<Pick>> pick(@Header("token") String token, @Field("ct_id") String ct_id);

    *//**
     * 立即下单
     *//*
    @FormUrlEncoded
    @POST("Order/addOrder")
    Observable<BaseResponseEntity<ImOrder>> imOrder(@Header("token") String token, @Field("sb_id") String sb_id, @Field("goods_id") String goods_id);

    *//**
     * 发起支付
     *//*
    @FormUrlEncoded
    @POST("pay/pay")
    Observable<BaseResponseEntity<Pay>> pay(@Header("token") String token, @Field("send") String send, @Field("live") String live, @Field("cabinet") String cabinet, @Field("address_id") String address_id, @Field("pay_type") String pay_type, @Field("trade_password") String trade_password, @Field("sb_id") String sb_id, @Field("incr_type1") String incr_type1, @Field("incr_type2") String incr_type2, @Field("incr_type3") String incr_type3, @Field("freight_pay") String freight_pay);

    *//**
     * 商品详情分享
     *//*
    @FormUrlEncoded
    @POST("common/goods_share")
    Observable<BaseResponseEntity<DetailShare>> detailShare(@Field("goods_sn") String goods_sn);

    *//**
     * 直播列表
     *//*
    @POST("live/live_list")
    Observable<BaseResponseEntity<LiveList>> liveList(@Header("token") String token);

    *//**
     * 直播详情
     *//*
    @FormUrlEncoded
    @POST("live/live_detail")
    Observable<BaseResponseEntity<LiveDetail>> liveDetail(@Header("token") String token, @Field("live_id") String live_id);

    *//**
     * 直播信息
     *//*
    @FormUrlEncoded
    @POST("common/live_info")
    Observable<BaseResponseEntity<LiveMessage>> liveMessage(@Field("live_id") String live_id);

    *//**
     * 直播间商品
     *//*

    @FormUrlEncoded
    @POST("live/live_goods")
    Observable<BaseResponseEntity<LiveGoods>> liveGoods(@Field("live_id") String live_id);

    *//**
     * 预约直播
     *//*
    @FormUrlEncoded
    @POST("user_center/order_live")
    Observable<BaseResponseEntity<Object>> liveYuyue(@Header("token") String token, @Field("live_id") String live_id);

    *//**
     * 直播分享
     *//*
    @FormUrlEncoded
    @POST("common/live_share")
    Observable<BaseResponseEntity<LiveShare>> liveShare(@Field("live_id") String live_id);

    *//**
     * 我的分享
     *//*
    @POST("user_center/share")
    Observable<BaseResponseEntity<Share>> myShare(@Header("token") String token);

    *//**
     * 关注商家
     *//*
    @FormUrlEncoded
    @POST("user_center/follow_supplier")
    Observable<BaseResponseEntity<Object>> follow(@Header("token") String token, @Field("supplier_id") String supplier_id);

    *//**
     * 判断是否已关注
     *//*
    @FormUrlEncoded
    @POST("live/check_follow")
    Observable<BaseResponseEntity<Follow>> isFollow(@Header("token") String token, @Field("supplier_id") String supplier_id);

    *//**
     * 我的预约
     *//*
    @POST("user_center/appointment")
    Observable<BaseResponseEntity<MyAppointment>> myAppointment(@Header("token") String token);

    *//**
     * 取消预约
     *//*
    @FormUrlEncoded
    @POST("user_center/cancel_appointment")
    Observable<BaseResponseEntity<List<Object>>> exitAppointment(@Header("token") String token, @Field("live_id") String live_id);

    *//**
     * 客服联系电话
     *//*
    @POST("common/service_phone")
    Observable<BaseResponseEntity<ServicePhone>> getPhone();

    *//**
     * 关于我们
     *//*
    @POST("common/about_us")
    Observable<BaseResponseEntity<AboutMe>> aboutMe();

    *//**
     * 分类搜索
     *//*
    @FormUrlEncoded
    @POST("search/cateSearch")
    Observable<BaseResponseEntity<TwoSearch>> cateSearch(@Field("cat_id") String cat_id);

    *//**
     * 二级分类搜索
     *//*
    @FormUrlEncoded
    @POST("search/goods_list")
    Observable<BaseResponseEntity<Goods>> twoSearch(@Field("cat_id") String cat_id, @Field("page") String page, @Field("sort") String sort);

    *//**
     * 下单信息
     *//*
    @FormUrlEncoded
    @POST("order/order_info")
    Observable<BaseResponseEntity<OrderInfo>> getOrderInfo(@Header("token") String token, @Field("send") String send, @Field("live") String live, @Field("cabinet") String cabinet, @Field("incr_type1") String incr_type1, @Field("incr_type2") String incr_type2, @Field("incr_type3") String incr_type3, @Field("freight_pay") String freight_pay);

    *//**
     * 浏览足迹
     *//*
    @FormUrlEncoded
    @POST("goods/browse_goods")
    Observable<BaseResponseEntity<Object>> getZuji(@Header("token") String token, @Field("goods_id") String goods_id);

    *//**
     * 足迹列表
     *//*
    @POST("user_center/foot_print")
    Observable<BaseResponseEntity<Collection>> getZujiList(@Header("token") String token);

    *//**
     * 提货支付
     *//*
    @FormUrlEncoded
    @POST("pick_pay/pick_pay")
    Observable<BaseResponseEntity<Pay>> getPick(@Header("token") String token, @Field("pay_type") String pay_type, @Field("address_id") String address_id, @Field("cabinet_id") String cabinet_id, @Field("shipping_pay") String shipping_pay);

    *//**
     * 冻积分记录
     *//*
    @POST("user_center/frozen_points_info")
    Observable<BaseResponseEntity<IgRecord>> igRecord(@Header("token") String token);

    *//**
     * 关注列表
     *//*
    @POST("user_center/follow_list")
    Observable<BaseResponseEntity<Attention>> getAttention(@Header("token") String token);

    *//**
     * 他人代付
     *//*
    @FormUrlEncoded
    @POST("other_pay/other_pay")
    Observable<BaseResponseEntity<Object>> daifu(@Header("token") String token, @Field("sb_id") String sb_id, @Field("send") String send, @Field("freight_pay") String freight_pay, @Field("live") String live, @Field("cabinet") String cabinet, @Field("incr_type1") String incr_type1, @Field("incr_type2") String incr_type2, @Field("incr_type3") String incr_type3, @Field("address_id") String address_id, @Field("phone") String phone, @Field("pay_msg") String pay_msg);

    *//**
     * 代付支付
     *//*
    @FormUrlEncoded
    @POST("other_pay/others_paid")
    Observable<BaseResponseEntity<Pay>> otherPay(@Header("token") String token, @Field("pay_type") String pay_type, @Field("sb_id") String sb_id, @Field("send") String send, @Field("live") String live, @Field("cabinet") String cabinet, @Field("freight_pay") String freight_pay, @Field("incr_type1") String incr_type1, @Field("incr_type2") String incr_type2, @Field("incr_type3") String incr_type3, @Field("address_id") String address_id, @Field("userId") String userId, @Field("trade_password") String trade_password,@Field("msg_id")String msg_id);

    *//**
     * 历史代付
     *//*
    @POST("other_pay/history_pay")
    Observable<BaseResponseEntity<Daifu>> getHistory(@Header("token") String token);

    *//**
     * 积分柜
     *//*
    @POST("user_center/increment")
    Observable<BaseResponseEntity<Emeralds>> getjifen(@Header("token") String token);

    *//**
     * 积分柜详情
     *//*
    @FormUrlEncoded
    @POST("user_center/increment_detail")
    Observable<BaseResponseEntity<JifenDetail>> getjifenDetail(@Header("token") String token, @Field("incr_id") String incr_id);

    *//**
     * 积分兑换（积分柜）
     *//*
    @FormUrlEncoded
    @POST("user_center/exchange_points")
    Observable<BaseResponseEntity<Object>> getjifenEx(@Header("token") String token, @Field("incr_id") String incr_id);


    *//**
     * 购买方式
     *//*
    @FormUrlEncoded
    @POST("order/buy_type")
    Observable<BaseResponseEntity<BuyTyle>> buyTyle(@Header("token") String token, @Field("type") String type);

    *//**
     * 支付方式
     *//*
    @FormUrlEncoded
    @POST("order/pay_type")
    Observable<BaseResponseEntity<PayTyle>> payTyle(@Header("token") String token, @Field("type") String type);
    *//**
     * 拒绝代付
     *//*
    @FormUrlEncoded
    @POST("message/reject_pay")
    Observable<BaseResponseEntity<Object>> refecePay(@Header("token") String token, @Field("msg_id") String msg_id);*/
}







