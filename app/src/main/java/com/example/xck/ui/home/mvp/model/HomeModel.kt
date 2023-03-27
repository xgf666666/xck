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
        attr:String,keyword:String,page:Int,page_size:Int
    ): Observable<BaseResponseEntity<List<Project>>> =AppApi.Api().getProjectList(token,attr,keyword,page, page_size)

    override fun getCapitalists(token: String,attr:String,keyword:String,page:Int,page_size:Int):
            Observable<BaseResponseEntity<List<Capitalist>>> =AppApi.Api().getCapitalistList(token,attr,keyword,page, page_size)
    override fun getBanner(type: String): Observable<BaseResponseEntity<Banner>> =AppApi.Api().getBanner(type)
}