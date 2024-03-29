package com.example.xck

import android.app.Activity
import android.app.Application
import com.example.xck.common.Constants
import com.example.xck.common.HxHelper
import com.example.xck.utils.CrashHandle
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.xx.baseutilslibrary.network.provider.JApiConfigProvider
import com.xx.baseutilslibrary.network.retrofit.Retrofit2Manager

/**
 *   author ： xiaogf
 *   time    ： 2022/10/14
 *   describe    ：
 */
class App :Application(){
    var activitys=ArrayList<Activity>()
    override fun onCreate() {
        super.onCreate()
        Retrofit2Manager.instance.apiConfigProvider = object : JApiConfigProvider {
            override fun getBaseUrl(): String =Constants.BASE_URL
        }
        CrashHandle.getInstance().init(baseContext);
        initIM()
    }
    private fun initIM(){
       HxHelper.getInstance().init(this)

    }
    fun addActivity(activity: Activity){
        if (!activitys.contains(activity)){
            activitys.add(activity)
        }
    }
    fun cleanActivity(){
        if (activitys.size!=0)
            for (i in 0 ..activitys.size-1){
                activitys[i].finish()
            }
    }
    fun deleteActivity(activity: Activity){
        if (activitys.contains(activity))
            activitys.remove(activity)
    }
}