package com.example.xck.ui.person.mvp.persenter

import com.example.xck.ui.person.mvp.contract.RoleIdentifyContract
import com.example.xck.ui.person.mvp.model.RoleIdentifyModel

class RoleIdentifyPersenter(view: RoleIdentifyContract.View):RoleIdentifyContract.Persenter(view) {
    override fun createModel(): RoleIdentifyContract.Model =RoleIdentifyModel()
}