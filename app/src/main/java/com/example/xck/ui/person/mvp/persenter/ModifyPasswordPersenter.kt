package com.example.xck.ui.person.mvp.persenter

import com.example.xck.ui.person.mvp.contract.ModifyPasswordContract
import com.example.xck.ui.person.mvp.model.ModifyPasswordModel

class ModifyPasswordPersenter(view: ModifyPasswordContract.View):ModifyPasswordContract.Persenter(view) {
    override fun createModel(): ModifyPasswordContract.Model =ModifyPasswordModel()
}