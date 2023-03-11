package com.example.xck.ui.home.mvp.model

import com.example.xck.bean.Capitalist
import com.example.xck.bean.UserQuotaNum
import com.example.xck.common.AppApi
import com.example.xck.ui.home.mvp.contract.InvestorDetailContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable


class InvestorDetailModel :InvestorDetailContract.Model{
    override fun getInverstorDetail(
        authorization: String,
        capitalist_id: Int
    ): Observable<BaseResponseEntity<Capitalist>> =AppApi.Api().getCapitalDetail(authorization, capitalist_id)

    override fun getUserQuotaNum(authorization: String): Observable<BaseResponseEntity<UserQuotaNum>> =AppApi.Api().getUserQuotaNum(authorization)
}