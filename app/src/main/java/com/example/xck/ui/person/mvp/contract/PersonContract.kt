package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.Login
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

interface PersonContract {
    interface View : BaseMvpView{
        fun userInfo(userInfo: Login.UserInfoBean)

    }
    interface Model{
        fun getUserInfo(): Observable<BaseResponseEntity<Login.UserInfoBean>>

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun getUserInfo()

    }
}