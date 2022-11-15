package com.example.xck.ui.person.mvp.model

import com.example.xck.bean.CodeImage
import com.example.xck.bean.VerifyPhone
import com.example.xck.common.AppApi
import com.example.xck.common.Constants
import com.example.xck.ui.person.mvp.contract.ModifyPasswordContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable


class ModifyPasswordModel :ModifyPasswordContract.Model{
    override fun getCodeImage(): Observable<BaseResponseEntity<CodeImage>> =AppApi.Api().codeImage

    override fun getCode(type: String, mobile_phone: String): Observable<BaseResponseEntity<Any>> =AppApi.Api().getCode(Constants.getToken(),type,mobile_phone)

    override fun verifyPhone(
        Authorization: String,
        type: String,
        mobile_phone: String,vercode:String,key:String,
        smscode: String
    ): Observable<BaseResponseEntity<VerifyPhone>> =AppApi.Api().verifyPhone(Authorization,type, mobile_phone, vercode, key, smscode)

    override fun verifyPhone(
        type: String,
        mobile_phone: String,
        vercode: String,
        key: String,
        smscode: String
    ): Observable<BaseResponseEntity<VerifyPhone>> =AppApi.Api().verifyPhone(type, mobile_phone, vercode, key, smscode)

    override fun modify(
        Authorization: String,
        password: String,
        repassword: String,
        verify_token: String
    ): Observable<BaseResponseEntity<Any>> =AppApi.Api().modifyPw(Authorization, password, repassword, verify_token)
}