package com.example.xck.ui.person.mvp.model

import com.example.xck.bean.Project
import com.example.xck.bean.UpLoadFile
import com.example.xck.common.AppApi
import com.example.xck.ui.person.mvp.contract.ProjectMessageEditContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ProjectMessageEditModel :ProjectMessageEditContract.Model{
    override fun upload(file: File): Observable<BaseResponseEntity<UpLoadFile>>{//image/png"
        val body = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

        val multipartBody = MultipartBody.Builder()
            .addFormDataPart("file", file.name, body)
            .setType(MultipartBody.FORM)
            .build()

//        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return AppApi.Api().uploads(multipartBody.parts)
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
        industries: IntArray,
        stages: IntArray,
        location: IntArray,
        id: Int
    ):Observable<BaseResponseEntity<Any>> {
        return if (id==0){
            AppApi.Api().setProject(Authorization, project_name, logo_image, found_time, introduction, wait_finance, operation, advantage, history_financice, project_file, team_member, industries, stages, location)
        }else{
            AppApi.Api().setProject(Authorization, project_name, logo_image, found_time, introduction, wait_finance, operation, advantage, history_financice, project_file, team_member, industries, stages, location,id)
        }
    }

    override fun getProjectDetail(
        token: String,
        user_id: Int
    ): Observable<BaseResponseEntity<Project>> =AppApi.Api().getProjectDetailForId(token,user_id)

}