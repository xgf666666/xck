package com.example.xck.base.mvp.contract


/**
 * BaseMvpView
 * (。・∀・)ノ
 * Describe：基类View接口,可以添加一些Activity的公用方法
 * Created by 雷小星🍀 on 2017/10/30 15:33.
 */

interface BaseMvpView {

    /**
     * 显示吐司消息
     */
    fun showToast(msg: String?)


    /**
     *  显示加载进度框
     */
    fun showLoadingDialog()

    /**
     * 隐藏加载进度框
     */
    fun dismissLoadingDialog()

}
