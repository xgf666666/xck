package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.Login
import com.example.xck.bean.UpLoadFile
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import java.io.File

interface PersonContract {
    interface View : BaseMvpView{
        fun userInfo(userInfo: Login.UserInfoBean)
    }
    interface Model{
        fun getUserInfo(): Observable<BaseResponseEntity<Login.UserInfoBean>>
        fun upload(file:File):Observable<BaseResponseEntity<UpLoadFile>>
        fun setMessageImage(avatar:String ):Observable<BaseResponseEntity<Any>>

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun getUserInfo()
        abstract fun upload(file: File)
        abstract fun setMessageImage(avatar:String)
    }
}