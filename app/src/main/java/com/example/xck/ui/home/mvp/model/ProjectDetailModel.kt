package com.example.xck.ui.home.mvp.model

import com.example.xck.App
import com.example.xck.bean.Project
import com.example.xck.common.AppApi
import com.example.xck.ui.home.mvp.contract.ProjectDetailContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable


class ProjectDetailModel :ProjectDetailContract.Model{
    override fun getProjectDetail(
        token: String,
        project_id: Int,
        user_id: Int
    ): Observable<BaseResponseEntity<Project>> =AppApi.Api().getProjectDetail(token,project_id)
}