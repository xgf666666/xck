package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.ImToken
import com.example.xck.bean.Login
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import java.util.Objects

interface LoginContract {
    interface View : BaseMvpView{
        fun login(login: Login)


    }
    interface Model{
        fun login(mobile_phone:String ,password:String): Observable<BaseResponseEntity<Login>>
        fun getImUserToken(authorization:String): Observable<BaseResponseEntity<ImToken>>

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun login(mobile_phone:String ,password:String)
    }
}