package com.example.xck.ui.person.mvp.persenter

import com.example.xck.ui.person.mvp.contract.ProjectMessageEditContract
import com.example.xck.ui.person.mvp.model.ProjectMessageEditModel

class ProjectMessageEditPersenter(view: ProjectMessageEditContract.View):ProjectMessageEditContract.Persenter(view) {
    override fun createModel(): ProjectMessageEditContract.Model =ProjectMessageEditModel()
}