package com.example.xck.ui.person.mvp.model

import com.example.xck.bean.Capitalist
import com.example.xck.bean.UpLoadFile
import com.example.xck.common.AppApi
import com.example.xck.ui.person.mvp.contract.InverstorMessageEditContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class InverstorMessageEditModel :InverstorMessageEditContract.Model{
    override fun upload(file: File): Observable<BaseResponseEntity<UpLoadFile>>{//image/png"
//        val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Builder()
            .addFormDataPart("file", file.name, body)
            .setType(MultipartBody.FORM)
            .build()

//        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return AppApi.Api().uploads(multipartBody.parts)
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
        industries: IntArray,
        stages: IntArray,
        location: IntArray,
        id:Int
    ): Observable<BaseResponseEntity<Any>>{
        return if (id==0){
            AppApi.Api().setCapitalist(Authorization, capitalist_name, contact_name, position, single_amount, avatar, introduction, cases, business_card_img, industries, stages, location)
        }else{
            AppApi.Api().setCapitalist(Authorization, capitalist_name, contact_name, position, single_amount, avatar, introduction, cases, business_card_img, industries, stages, location,id)
        }
    }

    override fun getInverstorDetail(
        authorization: String,
        userId: Int
    ): Observable<BaseResponseEntity<Capitalist>> =AppApi.Api().getCapitalDetailForId(authorization,userId)
}