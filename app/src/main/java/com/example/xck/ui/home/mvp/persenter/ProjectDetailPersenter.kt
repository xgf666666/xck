package com.example.xck.ui.home.mvp.persenter

import com.example.xck.ui.home.mvp.contract.ProjectDetailContract
import com.example.xck.ui.home.mvp.model.ProjectDetailModel

class ProjectDetailPersenter(view: ProjectDetailContract.View):ProjectDetailContract.Persenter(view) {
    override fun createModel(): ProjectDetailContract.Model =ProjectDetailModel()
}