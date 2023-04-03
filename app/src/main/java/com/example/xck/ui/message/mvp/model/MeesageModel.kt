package com.example.xck.ui.message.mvp.model

import com.example.xck.App
import com.example.xck.bean.CallIm
import com.example.xck.common.AppApi
import com.example.xck.common.Constants
import com.example.xck.ui.message.mvp.contract.MessageContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable


class MeesageModel :MessageContract.Model{
    override fun getGreetingList(): Observable<BaseResponseEntity<CallIm>> =AppApi.Api().getGreetingList(Constants.getToken())
}