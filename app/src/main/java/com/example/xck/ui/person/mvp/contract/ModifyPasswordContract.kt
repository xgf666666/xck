package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.CodeImage
import com.example.xck.bean.VerifyPhone
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

interface ModifyPasswordContract {
    interface View : BaseMvpView{
        fun getCodeImage(image: CodeImage)
        fun getCode()
        fun verifyPhone(verifyPhone: VerifyPhone)
    }
    interface Model{
//        fun getCodeImage(): Observable<BaseResponseEntity<CodeImage>>
//        fun getCode(type:String ,mobile_phone:String): Observable<BaseResponseEntity<Any>>
//        fun verifyPhone(Authorization:String,type:String,mobile_phone:String,smscode:String):Observable<BaseResponseEntity<VerifyPhone>>
    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun getCodeImage()
        abstract fun getCode(type:String ,mobile_phone:String)
        abstract fun verifyPhone(Authorization:String,type:String,mobile_phone:String,smscode:String)
    }
}