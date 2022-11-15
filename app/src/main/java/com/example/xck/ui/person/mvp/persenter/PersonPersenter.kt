package com.example.xck.ui.person.mvp.persenter

import com.example.xck.extensions.ui
import com.example.xck.ui.person.mvp.contract.PersonContract
import com.example.xck.ui.person.mvp.model.PersonModel

class PersonPersenter(view: PersonContract.View):PersonContract.Persenter(view) {
    override fun getUserInfo() {
        getModel().getUserInfo().ui({
            getView()?.userInfo(it.data!!)
        },{
            getView()?.showToast(it.message)
        })
    }

    override fun createModel(): PersonContract.Model =PersonModel()
}