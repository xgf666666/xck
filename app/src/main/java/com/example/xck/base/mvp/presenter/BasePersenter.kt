package com.example.xck.base.mvp.presenter

import com.example.xck.base.mvp.contract.BaseMvpView

interface BasePersenter {
    fun attachView(view: BaseMvpView)
    fun detachView()
}