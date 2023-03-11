package com.example.xck.ui.home.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.base.mvp.presenter.BasePersenter
import com.example.xck.bean.Banner
import com.example.xck.bean.Capitalist
import com.example.xck.bean.Project
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

interface HomeContract {
    interface View : BaseMvpView{
        fun getProject(projects:List<Project>)
        fun getCapitalist(projects:List<Capitalist>)
        fun getBanner(banner:Banner)

    }
    interface Model{
        fun getProjects(token:String,page:Int,page_size:Int): Observable<BaseResponseEntity<List<Project>>>
        fun getCapitalists(token:String): Observable<BaseResponseEntity<List<Capitalist>>>
        fun getBanner(type:String): Observable<BaseResponseEntity<Banner>>

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun getProjects(token:String,page:Int,page_size:Int)
        abstract fun getCapitalists(token:String)
        abstract fun getBanner(type:String)

    }
}