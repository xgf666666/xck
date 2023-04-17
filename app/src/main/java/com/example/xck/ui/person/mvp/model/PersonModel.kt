package com.example.xck.ui.person.mvp.model

import com.example.xck.bean.Login
import com.example.xck.bean.UpLoadFile
import com.example.xck.common.AppApi
import com.example.xck.common.Constants
import com.example.xck.ui.person.mvp.contract.PersonContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class PersonModel :PersonContract.Model{
    override fun getUserInfo(): Observable<BaseResponseEntity<Login.UserInfoBean>> =AppApi.Api().getUserInfo(Constants.getToken())
    override fun upload(file: File): Observable<BaseResponseEntity<UpLoadFile>>{//image/png"
        val body = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

        val multipartBody = MultipartBody.Builder()
            .addFormDataPart("file", file.name, body)
            .setType(MultipartBody.FORM)
            .build()

//        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return AppApi.Api().uploads(multipartBody.parts)
    }

    override fun setMessageImage(avatar: String): Observable<BaseResponseEntity<Any>> =AppApi.Api().setMessageImage(Constants.getToken(),avatar)
}