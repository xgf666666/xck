package com.example.xck.ui.person.mvp.model

import com.example.xck.common.AppApi
import com.example.xck.ui.person.mvp.contract.LoginContract
import com.example.xck.ui.person.mvp.contract.RoleIdentifyContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable


class RoleIdentifyModel :RoleIdentifyContract.Model{
    override fun roleIdentify(
        Authorization: String,
        user_type_select: Int,
        real_name: String,
        wechat: String
    ): Observable<BaseResponseEntity<Any>> =AppApi.Api().roleIdentify(Authorization, user_type_select, real_name, wechat)
}