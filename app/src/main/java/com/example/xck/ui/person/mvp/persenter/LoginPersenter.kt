package com.example.xck.ui.person.mvp.persenter

import com.example.xck.ui.person.mvp.contract.LoginContract
import com.example.xck.ui.person.mvp.model.LoginModel

class LoginPersenter(view: LoginContract.View):LoginContract.Persenter(view) {
    override fun createModel(): LoginContract.Model =LoginModel()
}