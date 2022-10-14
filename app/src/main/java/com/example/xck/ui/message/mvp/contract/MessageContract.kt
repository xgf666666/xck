package com.example.xck.ui.message.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.base.mvp.presenter.BasePersenter

interface MessageContract {
    interface View : BaseMvpView{

    }
    interface Model{

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){

    }
}