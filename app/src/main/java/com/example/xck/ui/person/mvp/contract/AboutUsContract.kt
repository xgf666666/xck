package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.Doc
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

/**
 *   author ： xiaogf
 *   time    ： 2022/11/18
 *   describe    ：
 */
interface AboutUsContract {
    interface View:BaseMvpView{
        fun getDoc(doc: Doc)

    }
    interface Model{
        fun getDoc(type:String):Observable<BaseResponseEntity<Doc>>
    }
   abstract class Presenter(view:View) :BaseMvpPresenter<Model,View>(view){
       abstract fun getDoc(type: String)
   }

}