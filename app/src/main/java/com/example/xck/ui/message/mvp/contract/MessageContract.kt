package com.example.xck.ui.message.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.base.mvp.presenter.BasePersenter
import com.example.xck.bean.CallIm
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

interface MessageContract {
    interface View : BaseMvpView{
        fun getGreetingList(callIm: CallIm)

    }
    interface Model{
        fun getGreetingList(): Observable<BaseResponseEntity<CallIm>>

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun getGreetingList()
    }
}