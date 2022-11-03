package com.example.xck.ui.person.mvp.persenter

import com.example.xck.ui.person.mvp.contract.ModifyPasswordContract
import com.example.xck.ui.person.mvp.model.ModifyPasswordModel

class ModifyPasswordPersenter(view: ModifyPasswordContract.View):ModifyPasswordContract.Persenter(view) {
    override fun getCodeImage() {
    }

    override fun getCode(type: String, mobile_phone: String) {
    }

    override fun verifyPhone(
        Authorization: String,
        type: String,
        mobile_phone: String,
        smscode: String
    ) {
    }

    override fun createModel(): ModifyPasswordContract.Model =ModifyPasswordModel()
}