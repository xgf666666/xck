package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.UpLoadFile
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import java.io.File

interface InverstorMessageEditContract {
    interface View : BaseMvpView{
        fun getloadFile(upLoadFile: UpLoadFile)

    }
    interface Model{
        fun upload(file: File): Observable<BaseResponseEntity<UpLoadFile>>
        fun setCapitalist(  Authorization:String, capitalist_name:String, contact_name:String,
                            position:String, single_amount:String, avatar:String, introduction:String,
                            cases:String, business_card_img:String,industries:Array<Int>,stages:Array<Int> ,
                         location:Array<Int>):Observable<BaseResponseEntity<Any>>

    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun upload(file:File)
        abstract  fun setCapitalist(  Authorization:String, capitalist_name:String, contact_name:String,
                                             position:String, single_amount:String, avatar:String, introduction:String,
                                             cases:String, business_card_img:String,industries:Array<Int>,stages:Array<Int> ,
                                             location:Array<Int>)

    }
}