package com.example.xck.ui.person.mvp.model

import com.example.xck.bean.ImToken
import com.example.xck.bean.Login
import com.example.xck.common.AppApi
import com.example.xck.ui.person.mvp.contract.LoginContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable


class LoginModel :LoginContract.Model{
    override fun login(mobile_phone: String, password: String): Observable<BaseResponseEntity<Login>> =AppApi.Api().login(mobile_phone,password)
    override fun getImUserToken(authorization:String): Observable<BaseResponseEntity<ImToken>> =AppApi.Api().getImUserToken(authorization)
}