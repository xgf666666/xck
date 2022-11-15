package com.example.xck.ui.person.mvp.persenter

import android.text.TextUtils
import com.example.xck.common.isPhone
import com.example.xck.extensions.ui
import com.example.xck.ui.person.mvp.contract.LoginContract
import com.example.xck.ui.person.mvp.model.LoginModel

class LoginPersenter(view: LoginContract.View):LoginContract.Persenter(view) {
    override fun login(mobile_phone: String, password: String) {
        if (TextUtils.isEmpty(mobile_phone)){
            getView()?.showToast("请输入手机号码")
            getView()?.dismissLoadingDialog()
            return
        }else if (!mobile_phone.isPhone()){
            getView()?.showToast("请输入正确的手机号码")
            getView()?.dismissLoadingDialog()
            return
        }
        getView()?.showLoadingDialog()
        getModel().login(mobile_phone, password).ui({
            getView()?.login(it.data!!)
        },{
            getView()?.dismissLoadingDialog()
            getView()?.showToast(it.message)
        })
    }

    override fun createModel(): LoginContract.Model =LoginModel()
}