package com.example.xck.ui.message.mvp.persenter

import com.blankj.utilcode.util.ToastUtils
import com.example.xck.extensions.ui
import com.example.xck.ui.message.mvp.contract.MessageContract
import com.example.xck.ui.message.mvp.model.MeesageModel

class MessagePersenter(view: MessageContract.View):MessageContract.Persenter(view) {
    override fun getGreetingList() {
        getModel().getGreetingList().ui({
            getView()?.getGreetingList(it.data!!)
        },{
            ToastUtils.showShort(it.message)
        })
    }

    override fun createModel(): MessageContract.Model =MeesageModel()
}