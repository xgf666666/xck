package com.example.xck.ui.home.mvp.persenter

import com.example.xck.common.Constants
import com.example.xck.extensions.ui
import com.example.xck.ui.home.mvp.contract.InvestorDetailContract
import com.example.xck.ui.home.mvp.model.InvestorDetailModel

class InvestorDetailPersenter(view: InvestorDetailContract.View):InvestorDetailContract.Persenter(view) {
    override fun getInverstorDetail(authorization: String, capitalist_id: Int) {
        getModel().getInverstorDetail(authorization, capitalist_id).ui({
            getView()?.getInverstorDetail(it.data!!)
        },{
            getView()?.showToast(it.message)
        })
    }

    override fun getUserQuotaNum() {
       getModel().getUserQuotaNum(Constants.getToken())
    }

    override fun createModel(): InvestorDetailContract.Model =InvestorDetailModel()
}