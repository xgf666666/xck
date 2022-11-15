package com.example.xck.ui.person.mvp.model

import com.example.xck.bean.UpLoadFile
import com.example.xck.common.AppApi
import com.example.xck.ui.person.mvp.contract.InverstorMessageEditContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class InverstorMessageEditModel :InverstorMessageEditContract.Model{
    override fun upload(file: File): Observable<BaseResponseEntity<UpLoadFile>>{//image/png"
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)

        val multipartBody = MultipartBody.Builder()
            .addFormDataPart("file", file.name, body)
            .setType(MultipartBody.FORM)
            .build()

//        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return AppApi.Api().uploads(multipartBody.parts())
    }

    override fun setCapitalist(
        Authorization: String,
        capitalist_name: String,
        contact_name: String,
        position: String,
        single_amount: String,
        avatar: String,
        introduction: String,
        cases: String,
        business_card_img: String,
        industries: Array<Int>,
        stages: Array<Int>,
        location: Array<Int>
    ): Observable<BaseResponseEntity<Any>> =AppApi.Api().setCapitalist(Authorization, capitalist_name, contact_name, position, single_amount, avatar, introduction, cases, business_card_img, industries, stages, location)
}