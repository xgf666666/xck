package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.UpLoadFile
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import java.io.File

interface ProjectMessageEditContract {
    interface View : BaseMvpView{
        fun getloadFile(upLoadFile: UpLoadFile)
        fun setProject()

    }
    interface Model{
        fun upload(file:File):Observable<BaseResponseEntity<UpLoadFile>>
        fun setProject(  Authorization:String, project_name:String, logo_image:String,
         found_time:String, introduction:String,  wait_finance:String, operation:String,
         advantage:String, history_financice:String,  project_file:String, team_member:String,industries:IntArray,stages:IntArray,
                         location:IntArray):Observable<BaseResponseEntity<Any>>
    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun upload(file:File)
        abstract fun setProject(Authorization:String, project_name:String, logo_image:String,
                                found_time:String, introduction:String, wait_finance:String, operation:String,
                                advantage:String, history_financice:String, project_file:String, team_member:String,
                                industries: IntArray,
                                stages:IntArray, location:IntArray);

    }
}