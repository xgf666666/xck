package com.example.xck.ui.message.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.base.mvp.presenter.BasePersenter
import com.example.xck.bean.CallIm
import com.example.xck.bean.Capitalist
import com.example.xck.bean.Project
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

interface MessageContract {
    interface View : BaseMvpView{
        fun getGreetingList(callIm: CallIm)
        fun getInverstorDetail(capitalist: Capitalist,user_id: Int)//机构详情
        fun getProjectDetail(project: Project,user_id: Int)//项目详情
    }
    interface Model{
        fun getGreetingList(): Observable<BaseResponseEntity<CallIm>>
        fun getInverstorDetail(authorization:String,user_id:Int):Observable<BaseResponseEntity<Capitalist>>
        fun getProjectDetail(token:String, user_id:Int):Observable<BaseResponseEntity<Project>>
    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun getGreetingList()
        abstract fun getInverstorDetail(authorization:String,capitalist_id:Int)
        abstract fun getProjectDetail(token:String, user_id:Int)
    }
}