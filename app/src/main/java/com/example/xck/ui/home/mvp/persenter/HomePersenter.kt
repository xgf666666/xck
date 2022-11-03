package com.example.xck.ui.home.mvp.persenter

import com.example.xck.extensions.ui
import com.example.xck.ui.home.mvp.contract.HomeContract
import com.example.xck.ui.home.mvp.model.HomeModel

class HomePersenter(view: HomeContract.View):HomeContract.Persenter(view) {
    override fun getProjects(token: String, page: Int, page_size: Int) {
       getModel().getProjects(token, page, page_size).ui({
           getView()?.getProject(it.data!!)
           getView()?.dismissLoadingDialog()
       },{
           getView()?.showToast(it.message)
           getView()?.dismissLoadingDialog()
       })
    }

    override fun getCapitalists(token: String) {
        getModel().getCapitalists(token).ui({
            getView()?.getCapitalist(it.data!!)
            getView()?.dismissLoadingDialog()
        },{
            getView()?.showToast(it.message)
            getView()?.dismissLoadingDialog()
        })
    }

    override fun createModel(): HomeContract.Model =HomeModel()
}