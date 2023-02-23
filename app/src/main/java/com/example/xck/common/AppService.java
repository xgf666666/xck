package com.example.xck.common;


import com.example.xck.bean.Capitalist;
import com.example.xck.bean.CodeImage;
import com.example.xck.bean.Doc;
import com.example.xck.bean.Login;
import com.example.xck.bean.Project;
import com.example.xck.bean.Register;
import com.example.xck.bean.Select;
import com.example.xck.bean.UpLoadFile;
import com.example.xck.bean.VerifyPhone;
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface AppService {
    /**
     * 获取图形验证码
     */
    @GET("api/v1/passport/captchaCode")
    Observable<BaseResponseEntity<CodeImage>> getCodeImage();
    /**
     * 获取验证码
     */
    @GET("api/v1/passport/smsCode")
    Observable<BaseResponseEntity<Object>> getCode(@Query("type") String type ,@Query("mobile_phone") String mobile_phone);
    /**
     * 获取验证码
     */
    @GET("api/v1/passport/smsCode")
    Observable<BaseResponseEntity<Object>> getCode(@Header("Authorization")String Authorization,@Query("type") String type ,@Query("mobile_phone") String mobile_phone);
    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("api/v1/passport/login")
    Observable<BaseResponseEntity<Login>> login(@Field("mobile_phone") String mobile_phone, @Field("password") String password);
    /**
     * 用户验手机号码认证
     */
    @FormUrlEncoded
    @POST("api/v1/passport/userVerify")
    Observable<BaseResponseEntity<VerifyPhone>> verifyPhone(@Header("Authorization") String Authorization, @Field("type")String type, @Field("mobile_phone") String mobile_phone, @Field("vercode")String vercode , @Field("key") String key, @Field("smscode") String smscode);
    /**
     * 用户验手机号码认证
     */
    @FormUrlEncoded
    @POST("api/v1/passport/userVerify")
    Observable<BaseResponseEntity<VerifyPhone>> verifyPhone( @Field("type")String type, @Field("mobile_phone") String mobile_phone, @Field("vercode")String vercode , @Field("key") String key, @Field("smscode") String smscode);
    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("api/v1/user/updatePassword")
    Observable<BaseResponseEntity<Object>> modifyPw(@Header("Authorization") String Authorization, @Field("password") String password, @Field("repassword") String repassword, @Field("verify_token") String verify_token);    /**
     * 忘记密码
     */
    @FormUrlEncoded
    @POST("api/v1/user/resetPassword")
    Observable<BaseResponseEntity<Object>> forgetPw( @Field("password") String password, @Field("repassword") String repassword, @Field("verify_token") String verify_token);
    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("api/v1/user/updatePassword")
    Observable<BaseResponseEntity<Object>> modifyPw(@Field("password") String password, @Field("repassword") String repassword, @Field("verify_token") String verify_token);
    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("api/v1/passport/reg")
    Observable<BaseResponseEntity<Register>> register(@Field("mobile_phone") String mobile_phone, @Field("vercode") String vercode, @Field("smscode") String smscode, @Field("key") String key, @Field("password") String password, @Field("repassword") String repassword);
 /**
     * 注册IM
     */
    @FormUrlEncoded
    @POST("api/v1/user/regIm")
    Observable<BaseResponseEntity<Object>> registerIM(@Header("Authorization") String authorization);



    /**
     * 获取用户信息
     */
    @GET("api/v1/user/getUserInfo")
    Observable<BaseResponseEntity<Login.UserInfoBean>> getUserInfo(@Header("Authorization") String Authorization);
    /**
     * 设置项目信息
     */
    @FormUrlEncoded
    @POST("api/v1/user/setProject")
    Observable<BaseResponseEntity<Object>> setProject(@Header("Authorization") String Authorization,@Field("project_name")String project_name,@Field("logo_image")String logo_image,
                                                      @Field("found_time") String found_time,@Field("introduction") String introduction,@Field("wait_finance") String wait_finance,@Field("operation") String operation,
                                                      @Field("advantage") String advantage,@Field("history_financice") String history_financice,@Field("project_file") String project_file,@Field("team_member") String team_member,@Field("industries") int[] industries,
                                                      @Field("stages") int[] stages,@Field("location") int[] location);
    /**
     * 设置机构信息
     */
    @FormUrlEncoded
    @POST("api/v1/user/setCapitalist")
    Observable<BaseResponseEntity<Object>> setCapitalist(@Header("Authorization") String Authorization,@Field("capitalist_name")String capitalist_name,@Field("contact_name")String contact_name,
                                                      @Field("position") String position,@Field("single_amount") String single_amount,@Field("avatar") String avatar,@Field("introduction") String introduction,
                                                      @Field("cases") String cases,@Field("business_card_img") String business_card_img,@Field("industries") int[] industries,
                                                      @Field("stages") int[] stages,@Field("location") int[] location);
    /**
     * 角色认证
     */
    @FormUrlEncoded
    @POST("/api/v1/user/proof")
    Observable<BaseResponseEntity<Object>> roleIdentify(@Header("Authorization") String Authorization,@Field("user_type_select")int user_type_select,@Field("real_name")String real_name,@Field("wechat") String wechat);
    /**
     * 上传文件
     */
    @Multipart
    @POST("api/v1/attach/uploads")
    Observable<BaseResponseEntity<UpLoadFile>> uploads(@Part List<MultipartBody.Part> file);
    /**
     * 获取首页刷选数据
     */
    @GET("api/v1/attach/getAttrList")
    Observable<BaseResponseEntity<List<Select>>> getAttrList();
    /**
     * 项目列表
     */
    @GET("api/v1/project/getProjectList")
    Observable<BaseResponseEntity<List<Project>>> getProjectList(@Header("Authorization") String authorization, @Query("page") int page, @Query("page_size") int page_size);
    /**
     * 机构列表
     */
    @GET("api/v1/capitalist/getCapitalistList")
    Observable<BaseResponseEntity<List<Capitalist>>> getCapitalistList(@Header("Authorization") String authorization);
    /**
     * 项目详情
     */
    @GET("api/v1/project/getProjectInfo")
    Observable<BaseResponseEntity<Project>> getProjectDetail(@Header("Authorization") String authorization,@Query("project_id") int project_id);
    /**
     * 机构详情
     */
    @GET("api/v1/capitalist/getCapitalistInfo")
    Observable<BaseResponseEntity<Capitalist>> getCapitalDetail(@Header("Authorization") String authorization,@Query("capitalist_id") int capitalist_id);
    /**
     * 获取相关文档
     */
    @GET("api/v1/attach/getDoc")
    Observable<BaseResponseEntity<Doc>> getDoc( @Query("type") String type);

}







