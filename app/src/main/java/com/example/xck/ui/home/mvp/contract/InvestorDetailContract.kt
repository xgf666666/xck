package com.example.xck.ui.home.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.CallIm
import com.example.xck.bean.Capitalist
import com.example.xck.bean.UserQuotaNum
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

interface InvestorDetailContract {
    interface View : BaseMvpView{
        fun getInverstorDetail(capitalist: Capitalist)
        fun getUserQuotaNum(userQuotaNum: UserQuotaNum)
        fun getGreetingList(callIm: CallIm)
    }
    interface Model{
        fun getInverstorDetail(authorization:String,capitalist_id:Int):Observable<BaseResponseEntity<Capitalist>>
        fun getUserQuotaNum(authorization:String):Observable<BaseResponseEntity<UserQuotaNum>>
        fun getGreetingList(): Observable<BaseResponseEntity<CallIm>>
    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun getInverstorDetail(authorization:String,capitalist_id:Int)
        abstract fun getUserQuotaNum()
        abstract fun getGreetingList()
    }
}