package com.xx.baseutilslibrary.network.retrofit


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.xx.baseutilslibrary.network.exception.NullProviderException
import com.xx.baseutilslibrary.network.gson.XxGsonConverterFactory
import com.xx.baseutilslibrary.network.provider.JApiConfigProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * MusicDownloadManager
 * (๑• . •๑)
 * 类描述:
 * Created by 雷小星🍀 on 2017/6/21 11:12
 */

class Retrofit2Manager {
    /**
     * 获取Retrofit2
     *
     * @return Retrofit
     */
    /**
     * 设置自己的 Retrofit
     *
     * @param retrofit
     */
    var retrofit: Retrofit? = null
        get() {
            if (apiConfigProvider == null)
                throw NullProviderException("ApiConfigProvider没有被设置")

            if (field == null) {
                field = Retrofit.Builder()
                        .baseUrl(apiConfigProvider!!.apiBaseUrl)
                        .client(okHttpClient)
                        .addConverterFactory(XxGsonConverterFactory.create()!!)
                        .addConverterFactory(GsonConverterFactory.create()!!)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }
            return field
        }
    /**
     * 设置自己的 okHttpClient
     *
     * @param okHttpClient
     */

    var okHttpClient: OkHttpClient? = null
        get() {
            if (field == null) {
                this.okHttpClient = OkHttpClient.Builder()
                        .readTimeout(20, TimeUnit.SECONDS) //读取时超时时间
                        .writeTimeout(20, TimeUnit.SECONDS) //写入时超时时间
                        .connectTimeout(25, TimeUnit.SECONDS) //链接超时时间
                        //.cache(new Cache(new File(ApiConfig.Cache_dir), 1024 * 4))//设置缓存为4M
                        .build()
            }
            return field
        }
    /**
     * 获取Api配置提供者
     *
     * @return Api配置提供者
     */
    var apiConfigProvider: JApiConfigProvider? = null

    /**
     * 创建Api对象
     *
     * @param service Api接口
     * @param <T>     接口
     * @return Api对象
    </T> */
    @Throws(NullProviderException::class)
    fun <T> createApi(service: Class<T>): T {
        return retrofit?.create(service)!!
    }

    companion object {

        private var retrofit2Manager: Retrofit2Manager? = null

        /**
         * 获取单例
         * 使用双重校验
         *
         * @return 单例
         */
        val instance: Retrofit2Manager
            get() {
                if (retrofit2Manager == null) {
                    synchronized(Retrofit2Manager::class.java) {
                        if (retrofit2Manager == null) {
                            retrofit2Manager = Retrofit2Manager()
                        }
                    }
                }
                return retrofit2Manager!!
            }
    }

}
