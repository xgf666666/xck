package com.example.xck.base.mvp

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.xx.baseutilslibrary.common.XxResourceUtil


/**
 * BaseActivity
 * (。・∀・)ノ
 * Describe：封装AppCompatActivity一级基类
 * Created by 雷小星🍀 on 2017/10/30 15:21.
 */

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mContext: Context
    protected lateinit var mPermissionsManager : PermissionsManager
    abstract fun getViewBinding(): ViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(getViewBinding()!!.root);
//        setContentView(getActivityLayoutId())
        mPermissionsManager= PermissionsManager(this)
        mPermissionsManager.permissions= returnPermissionArr()
        mPermissionsManager.setPermissionCallback(MyOnPermissionsCallback())
        afterSetContentView()

    }



    /**
     * 在设置ContenView之前执行的操作
     * 需要时复写
     */
    fun beforeSetContentView() {}


    /**
     * 在设置ContenView之后执行的操作
     * 需要时复写
     */
    protected open fun afterSetContentView() {
//        window.decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        initData()
        initEvent()

        //初始化返回按钮
        val id = XxResourceUtil.getId(mContext, "iv_back")
        val ibBack = findViewById<ImageView>(id)
        ibBack?.setOnClickListener {
            finish()
        }
    }

    /**
     * 初始化数据状态
     */
    protected abstract fun initData()


    /**
     * 初始化事件
     */
    protected abstract fun initEvent()


    /**
     * 权限申请结果回调
     */
    private inner class MyOnPermissionsCallback : PermissionsManager.OnPermissionsCallback {
        override fun hasPermissions() {
            requestPermissionsSuccess()
        }

        override fun noPermissions() {
            requestPermissionsFailed()
        }
    }

    /**
     * 有权限
     */
    open fun requestPermissionsSuccess() {

    }

    /**
     * 无权限
     */
    open fun requestPermissionsFailed() {

    }

    /**
     * 子类复写此方法返回需要申请的权限
     *
     * @return
     */
    open fun returnPermissionArr(): Array<out String>? {
        return arrayOf<String>()
    }


}
