package com.example.xck.ui.home.mvp.persenter

import com.example.xck.common.Constants
import com.example.xck.extensions.ui
import com.example.xck.ui.home.mvp.contract.ProjectDetailContract
import com.example.xck.ui.home.mvp.model.ProjectDetailModel

class ProjectDetailPersenter(view: ProjectDetailContract.View):ProjectDetailContract.Persenter(view) {
    override fun getProjectDetail(token: String, project_id: Int, user_id: Int) {
//        getView()?.showLoadingDialog()
        getModel().getProjectDetail(token, project_id, user_id).ui({
//            getView()?.showLoadingDialog()
        getView()?.getProjectDetail(it.data!!)
        },{
//            getView()?.showLoadingDialog()
            getView()?.showToast(it.message)
        })
    }

    override fun getUserQuotaNum() {
        getModel().getUserQuotaNum(Constants.getToken()).ui({
            getView()?.getUserQuotaNum(it.data!!)
        },{
            getView()?.showToast(it.message)
        })
    }

    override fun getGreetingList() {
        getModel().getGreetingList().ui({
            getView()?.getGreetingList(it.data!!)
        },{

        })
    }


    override fun createModel(): ProjectDetailContract.Model =ProjectDetailModel()
}