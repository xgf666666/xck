package com.example.xck.ui.person.mvp.contract

import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.*
import com.example.xck.common.AppApi
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

interface RegisterContract {
    interface View : BaseMvpView{
        fun getCodeImage(image: CodeImage)
        fun getCode()
        fun register(register: Login)
        fun registerIM()
        fun login(login: Login)
    }
    interface Model{
        fun getCodeImage(): Observable<BaseResponseEntity<CodeImage>>
        fun getCode(type:String ,mobile_phone:String): Observable<BaseResponseEntity<Any>>
        fun register(mobile_phone:String ,vercode:String,smscode:String ,key:String ,password:String ,repassword:String): Observable<BaseResponseEntity<Login>>
        fun login(mobile_phone:String ,password:String): Observable<BaseResponseEntity<Login>>
        fun registerIM(authorization:String): Observable<BaseResponseEntity<Any>>
        fun getImUserToken(authorization:String): Observable<BaseResponseEntity<ImToken>>
    }
    abstract class Persenter(view:View):BaseMvpPresenter<Model,View>(view){
       abstract fun getCodeImage()
       abstract fun getCode(type:String ,mobile_phone:String)
       abstract fun register(mobile_phone:String ,vercode:String,smscode:String ,key:String ,password:String ,repassword:String)
       abstract fun registerIM(authorization:String)
    }
}