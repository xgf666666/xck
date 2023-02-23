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
        fun modify()
    }
    interface Model{
        fun getCodeImage(): Observable<BaseResponseEntity<CodeImage>>
        fun getCode(type:String ,mobile_phone:String): Observable<BaseResponseEntity<Any>>
        fun verifyPhone(Authorization:String,type:String,mobile_phone:String,vercode:String,key:String,smscode:String):Observable<BaseResponseEntity<VerifyPhone>>
        fun verifyPhone(type:String,mobile_phone:String,vercode:String,key:String,smscode:String):Observable<BaseResponseEntity<VerifyPhone>>
        fun modify(Authorization:String,password:String,repassword:String,verify_token:String):Observable<BaseResponseEntity<Any>>
        fun forgetPW(password:String,repassword:String,verify_token:String):Observable<BaseResponseEntity<Any>>
    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
        abstract fun getCodeImage()
        abstract fun getCode(type:String ,mobile_phone:String)
        abstract fun verifyPhone(Authorization:String,type:String,mobile_phone:String,vercode:String,key:String,smscode:String)
        abstract fun modify(Authorization:String,password:String,repassword:String,verify_token:String)
        abstract fun forgetPW(password:String,repassword:String,verify_token:String)

    }
}