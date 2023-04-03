package com.example.xck.ui.person.mvp.model

import com.example.xck.bean.*
import com.example.xck.common.AppApi
import com.example.xck.ui.person.mvp.contract.RegisterContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable


class RegisterModel :RegisterContract.Model{
    override fun getCodeImage(): Observable<BaseResponseEntity<CodeImage>> =AppApi.Api().codeImage
    override fun getCode(type: String, mobile_phone: String): Observable<BaseResponseEntity<Any>> =AppApi.Api().getCode(type,mobile_phone)
    override fun register(
        mobile_phone: String,
        vercode: String,
        smscode: String,
        key: String,
        password: String,
        repassword: String
    ): Observable<BaseResponseEntity<Login>> =AppApi.Api().register(mobile_phone,vercode,smscode,key,password,repassword)

    override fun login(
        mobile_phone: String,
        password: String
    ): Observable<BaseResponseEntity<Login>> =AppApi.Api().login(mobile_phone, password)

    override fun registerIM(authorization: String): Observable<BaseResponseEntity<Any>> =AppApi.Api().registerIM(authorization)
    override fun getImUserToken(authorization:String): Observable<BaseResponseEntity<ImToken>> =AppApi.Api().getImUserToken(authorization)


}