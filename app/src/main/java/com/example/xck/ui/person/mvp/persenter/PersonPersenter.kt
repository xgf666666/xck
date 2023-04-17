package com.example.xck.ui.person.mvp.persenter

import com.blankj.utilcode.util.ToastUtils
import com.example.xck.extensions.ui
import com.example.xck.ui.person.mvp.contract.PersonContract
import com.example.xck.ui.person.mvp.model.PersonModel
import java.io.File

class PersonPersenter(view: PersonContract.View):PersonContract.Persenter(view) {
    override fun getUserInfo() {
        getModel().getUserInfo().ui({
            getView()?.userInfo(it.data!!)
        },{
            getView()?.showToast(it.message)
        })
    }

    override fun upload(file: File) {
        getView()?.showLoadingDialog()
        getModel().upload(file).ui({
           setMessageImage(it.data!!.url)
            ToastUtils.showShort(it.msg)
            getView()?.dismissLoadingDialog()
//            getView()?.showToast("成功")
        }, {
            getView()?.dismissLoadingDialog()
            getView()?.showToast(it.message)
        })
    }

    override fun setMessageImage(avatar: String) {
        getModel().setMessageImage(avatar).ui({
            getUserInfo()
        },{
            getView()?.showToast(it.message)
        })
    }

    override fun createModel(): PersonContract.Model =PersonModel()
}