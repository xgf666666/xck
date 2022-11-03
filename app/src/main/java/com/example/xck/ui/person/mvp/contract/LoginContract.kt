package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.Login
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

interface LoginContract {
    interface View : BaseMvpView{
        fun login(login: Login)

    }
    interface Model{
        fun login(mobile_phone:String ,password:String): Observable<BaseResponseEntity<Login>>

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun login(mobile_phone:String ,password:String)
    }
}