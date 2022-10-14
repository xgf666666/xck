package com.example.xck.ui.message.mvp.persenter

import com.example.xck.ui.message.mvp.contract.MessageContract
import com.example.xck.ui.message.mvp.model.MeesageModel

class MessagePersenter(view: MessageContract.View):MessageContract.Persenter(view) {
    override fun createModel(): MessageContract.Model =MeesageModel()
}