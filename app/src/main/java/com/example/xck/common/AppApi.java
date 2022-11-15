package com.example.xck.common;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xx.baseutilslibrary.network.gson.XxGsonConverterFactory;
import com.xx.baseutilslibrary.network.retrofit.Retrofit2Manager;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AppApi
 * 沉迷学习不能自拔
 * Describe：
 * Created by 雷小星🍀 on 2017/12/7 9:27.
 */

public class AppApi {

    private static AppService api;
    private static String mApiSign;

    public static AppService Api() {
        if (api == null) {
            OkHttpClient okHttpClient = Retrofit2Manager.Companion
                    .getInstance()
                    .getOkHttpClient()
                    .newBuilder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(chain -> {
                        Request oldRequest = chain.request();

                        HttpUrl httpUrl = oldRequest.url()
                                .newBuilder()
                                .build();

                        Request newRequest = oldRequest.newBuilder()
                                /*.addHeader("language", "zh_cn")*/
                                /*.addHeader("apisign", getApiSignHeader())*/
                                .url(httpUrl)
                                .build();
                        return chain.proceed(newRequest);
                    })
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))//添加网络日志
                    .build();


            Retrofit2Manager.Companion
                    .getInstance()
                    .setOkHttpClient(okHttpClient);

            //设置新的RetrofitXxGsonConverterFactory
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Retrofit2Manager.Companion.getInstance().getApiConfigProvider().getApiBaseUrl())
                    .client(okHttpClient)
                    .addConverterFactory(XxGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            Retrofit2Manager.Companion.getInstance().setRetrofit(retrofit);

            api = Retrofit2Manager.Companion
                    .getInstance()
                    .createApi(AppService.class);
        }
        return api;
    }

   /* public static String getApiSignHeader() {
        if (Constants.getToken() != null) {
            return Constants.getToken().getSign_api();
        }
        return "123";
    }*/

    public static void setApiSignHeader(String apiSign) {
        mApiSign = apiSign;
    }

    /**
     * 置空,使下次请求时重新获取配置
     */
    public static void resetApi() {
        api = null;
    }

}
