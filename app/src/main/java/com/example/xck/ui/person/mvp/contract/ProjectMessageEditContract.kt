package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.Project
import com.example.xck.bean.UpLoadFile
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import java.io.File

interface ProjectMessageEditContract {
    interface View : BaseMvpView{
        fun getloadFile(upLoadFile: UpLoadFile)
        fun setProject()
        fun getProjectDetail(project: Project)
    }
    interface Model{
        fun upload(file:File):Observable<BaseResponseEntity<UpLoadFile>>
        fun setProject(  Authorization:String, project_name:String, logo_image:String,
         found_time:String, introduction:String,  wait_finance:String, operation:String,
         advantage:String, history_financice:String,  project_file:String, team_member:String,industries:IntArray,stages:IntArray,
                         location:IntArray,id: Int):Observable<BaseResponseEntity<Any>>
        fun getProjectDetail(token:String, user_id:Int):Observable<BaseResponseEntity<Project>>
    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun upload(file:File)
        abstract fun setProject(Authorization:String, project_name:String, logo_image:String,
                                found_time:String, introduction:String, wait_finance:String, operation:String,
                                advantage:String, history_financice:String, project_file:String, team_member:String,
                                industries: IntArray,
                                stages:IntArray, location:IntArray,id: Int);
        abstract fun getProjectDetail(token:String, user_id:Int)

    }
}