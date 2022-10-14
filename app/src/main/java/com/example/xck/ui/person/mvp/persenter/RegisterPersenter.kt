package com.example.xck.ui.person.mvp.persenter

import com.example.xck.ui.person.mvp.contract.RegisterContract
import com.example.xck.ui.person.mvp.model.RegisterModel

class RegisterPersenter(view: RegisterContract.View):RegisterContract.Persenter(view) {
    override fun createModel(): RegisterContract.Model =RegisterModel()
}