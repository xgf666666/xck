package com.example.xck.ui.message.mvp.model

import com.example.xck.App
import com.example.xck.bean.CallIm
import com.example.xck.bean.Capitalist
import com.example.xck.bean.Project
import com.example.xck.common.AppApi
import com.example.xck.common.Constants
import com.example.xck.ui.message.mvp.contract.MessageContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable


class MeesageModel :MessageContract.Model{
    override fun getGreetingList(): Observable<BaseResponseEntity<CallIm>> =AppApi.Api().getGreetingList(Constants.getToken())
    override fun getInverstorDetail(
        authorization: String,
        user_id: Int
    ): Observable<BaseResponseEntity<Capitalist>> =AppApi.Api().getCapitalDetailForId(authorization, user_id)

    override fun getProjectDetail(
        token: String,
        user_id: Int
    ): Observable<BaseResponseEntity<Project>> =AppApi.Api().getProjectDetailForId(token,user_id)
}