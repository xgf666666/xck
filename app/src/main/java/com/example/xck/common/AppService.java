package com.example.xck.common;


import com.example.xck.bean.Capitalist;
import com.example.xck.bean.CodeImage;
import com.example.xck.bean.Login;
import com.example.xck.bean.Project;
import com.example.xck.bean.Register;
import com.example.xck.bean.Select;
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
     * 登录
     */
    @FormUrlEncoded
    @POST("api/v1/passport/login")
    Observable<BaseResponseEntity<Login>> login(@Field("mobile_phone") String mobile_phone, @Field("password") String password);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("api/v1/passport/reg")
    Observable<BaseResponseEntity<Register>> register(@Field("mobile_phone") String mobile_phone, @Field("vercode") String vercode, @Field("smscode") String smscode, @Field("key") String key, @Field("password") String password, @Field("repassword") String repassword);

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
     * 项目列表
     */
    @GET("api/v1/capitalist/getCapitalistList")
    Observable<BaseResponseEntity<List<Capitalist>>> getCapitalistList(@Header("Authorization") String authorization);
}







