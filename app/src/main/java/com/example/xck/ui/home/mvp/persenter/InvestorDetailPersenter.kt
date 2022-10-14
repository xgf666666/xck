package com.example.xck.ui.home.mvp.persenter

import com.example.xck.ui.home.mvp.contract.InvestorDetailContract
import com.example.xck.ui.home.mvp.model.InvestorDetailModel

class InvestorDetailPersenter(view: InvestorDetailContract.View):InvestorDetailContract.Persenter(view) {
    override fun createModel(): InvestorDetailContract.Model =InvestorDetailModel()
}