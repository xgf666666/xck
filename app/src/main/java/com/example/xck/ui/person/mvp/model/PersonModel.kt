package com.example.xck.ui.person.mvp.model

import com.example.xck.bean.Login
import com.example.xck.common.AppApi
import com.example.xck.common.Constants
import com.example.xck.ui.person.mvp.contract.PersonContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable


class PersonModel :PersonContract.Model{
    override fun getUserInfo(): Observable<BaseResponseEntity<Login.UserInfoBean>> =AppApi.Api().getUserInfo(Constants.getToken())
}