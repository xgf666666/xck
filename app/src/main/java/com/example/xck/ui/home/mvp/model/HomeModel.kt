package com.example.xck.ui.home.mvp.model

import com.example.xck.App
import com.example.xck.bean.Banner
import com.example.xck.bean.Capitalist
import com.example.xck.bean.Project
import com.example.xck.common.AppApi
import com.example.xck.ui.home.mvp.contract.HomeContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable


class HomeModel :HomeContract.Model{
    override fun getProjects(
        token: String,
        page: Int,
        page_size: Int
    ): Observable<BaseResponseEntity<List<Project>>> =AppApi.Api().getProjectList(token,page, page_size)

    override fun getCapitalists(token: String): Observable<BaseResponseEntity<List<Capitalist>>> =AppApi.Api().getCapitalistList(token)
    override fun getBanner(type: String): Observable<BaseResponseEntity<Banner>> =AppApi.Api().getBanner(type)
}