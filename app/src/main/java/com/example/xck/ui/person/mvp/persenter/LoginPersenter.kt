package com.example.xck.ui.person.mvp.persenter

import com.example.xck.extensions.ui
import com.example.xck.ui.person.mvp.contract.LoginContract
import com.example.xck.ui.person.mvp.model.LoginModel

class LoginPersenter(view: LoginContract.View):LoginContract.Persenter(view) {
    override fun login(mobile_phone: String, password: String) {
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