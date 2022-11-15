package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

interface RoleIdentifyContract {
    interface View : BaseMvpView{
        fun roleIdentify()

    }
    interface Model{
        fun roleIdentify( Authorization:String, user_type_select:Int, real_name:String, wechat:String):Observable<BaseResponseEntity<Any>>

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract  fun roleIdentify( Authorization:String, user_type_select:Int, real_name:String, wechat:String)
    }
}