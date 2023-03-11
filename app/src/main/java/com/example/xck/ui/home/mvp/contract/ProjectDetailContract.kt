package com.example.xck.ui.home.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.Project
import com.example.xck.bean.UserQuotaNum
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

interface ProjectDetailContract {
    interface View : BaseMvpView{
        fun getProjectDetail(project: Project)
        abstract fun getUserQuotaNum(userQuotaNum: UserQuotaNum)

    }
    interface Model{
        fun getProjectDetail(token:String, project_id:Int, user_id:Int):Observable<BaseResponseEntity<Project>>
        fun getUserQuotaNum(authorization:String):Observable<BaseResponseEntity<UserQuotaNum>>
    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
       abstract fun getProjectDetail(token:String, project_id:Int, user_id:Int)
        abstract fun getUserQuotaNum()
    }
}