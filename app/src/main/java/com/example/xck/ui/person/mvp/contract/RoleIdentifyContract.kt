package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter

interface RoleIdentifyContract {
    interface View : BaseMvpView{

    }
    interface Model{

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){

    }
}