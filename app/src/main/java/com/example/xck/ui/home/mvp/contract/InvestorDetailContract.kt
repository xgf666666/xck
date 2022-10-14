package com.example.xck.ui.home.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter

interface InvestorDetailContract {
    interface View : BaseMvpView{

    }
    interface Model{

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){

    }
}