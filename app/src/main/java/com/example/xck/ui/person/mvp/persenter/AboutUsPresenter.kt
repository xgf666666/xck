package com.example.xck.ui.person.mvp.persenter

import com.blankj.utilcode.util.ToastUtils
import com.example.xck.extensions.ui
import com.example.xck.ui.person.mvp.contract.AboutUsContract
import com.example.xck.ui.person.mvp.model.AboutUsModel

/**
 *   author ： xiaogf
 *   time    ： 2022/11/18
 *   describe    ：
 */
class AboutUsPresenter(view:AboutUsContract.View) :AboutUsContract.Presenter(view) {
    override fun getDoc(type: String) {
        getView()?.showLoadingDialog()
        getModel().getDoc(type).ui({
            getView()?.dismissLoadingDialog()
            getView()?.getDoc(it.data!!)
        },{
            getView()?.dismissLoadingDialog()
            ToastUtils.showShort(it.message)
        })
    }

    override fun createModel(): AboutUsContract.Model =AboutUsModel()
}