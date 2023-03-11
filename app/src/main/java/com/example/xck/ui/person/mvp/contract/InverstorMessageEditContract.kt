package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.Capitalist
import com.example.xck.bean.UpLoadFile
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import java.io.File

interface InverstorMessageEditContract {
    interface View : BaseMvpView{
        fun getloadFile(upLoadFile: UpLoadFile)
        fun setCapitalist()
        fun getInverstorDetail(capitalist: Capitalist)
    }
    interface Model{
        fun upload(file: File): Observable<BaseResponseEntity<UpLoadFile>>
        fun setCapitalist(  Authorization:String, capitalist_name:String, contact_name:String,
                            position:String, single_amount:String, avatar:String, introduction:String,
                            cases:String, business_card_img:String,industries:IntArray,stages:IntArray ,
                         location:IntArray,id:Int):Observable<BaseResponseEntity<Any>>
        fun getInverstorDetail(authorization:String,userId:Int):Observable<BaseResponseEntity<Capitalist>>
    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun upload(file:File)
        abstract  fun setCapitalist(  Authorization:String, capitalist_name:String, contact_name:String,
                                             position:String, single_amount:String, avatar:String, introduction:String,
                                             cases:String, business_card_img:String,industries:IntArray,stages:IntArray ,
                                             location:IntArray,id:Int)
        abstract fun getInverstorDetail(authorization:String,userId:Int)

    }
}