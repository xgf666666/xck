package com.example.xck.ui.person.mvp.persenter

import com.example.xck.ui.person.mvp.contract.InverstorMessageEditContract
import com.example.xck.ui.person.mvp.model.InverstorMessageEditModel

class InverstorMessageEditPersenter(view: InverstorMessageEditContract.View):InverstorMessageEditContract.Persenter(view) {
    override fun createModel(): InverstorMessageEditContract.Model =InverstorMessageEditModel()
}