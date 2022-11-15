package com.example.xck.ui.person.mvp.model

import com.example.xck.bean.UpLoadFile
import com.example.xck.common.AppApi
import com.example.xck.ui.person.mvp.contract.ProjectMessageEditContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ProjectMessageEditModel :ProjectMessageEditContract.Model{
    override fun upload(file: File): Observable<BaseResponseEntity<UpLoadFile>>{//image/png"
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)

        val multipartBody = MultipartBody.Builder()
            .addFormDataPart("file", file.name, body)
            .setType(MultipartBody.FORM)
            .build()

//        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return AppApi.Api().uploads(multipartBody.parts())
    }

    override fun setProject(
        Authorization: String,
        project_name: String,
        logo_image: String,
        found_time: String,
        introduction: String,
        wait_finance: String,
        operation: String,
        advantage: String,
        history_financice: String,
        project_file: String,
        team_member: String,
        industries: Array<Int>,
        stages: Array<Int>,
        location: Array<Int>
    ):Observable<BaseResponseEntity<Any>> =AppApi.Api().setProject(Authorization, project_name, logo_image, found_time, introduction, wait_finance, operation, advantage, history_financice, project_file, team_member, industries, stages, location)

}