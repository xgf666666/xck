package com.example.xck

import android.app.Application
import com.example.xck.common.Constants
import com.xx.baseutilslibrary.network.provider.JApiConfigProvider
import com.xx.baseutilslibrary.network.retrofit.Retrofit2Manager

/**
 *   author ： xiaogf
 *   time    ： 2022/10/14
 *   describe    ：
 */
class App :Application(){
    override fun onCreate() {
        super.onCreate()
        Retrofit2Manager.instance.apiConfigProvider = object : JApiConfigProvider {
            override fun getBaseUrl(): String =Constants.BASE_URL
        }

    }

}