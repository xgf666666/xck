package com.example.xck.ui.home.mvp.persenter

import com.example.xck.ui.home.mvp.contract.HomeContract
import com.example.xck.ui.home.mvp.model.HomeModel

class HomePersenter(view: HomeContract.View):HomeContract.Persenter(view) {
    override fun createModel(): HomeContract.Model =HomeModel()
}