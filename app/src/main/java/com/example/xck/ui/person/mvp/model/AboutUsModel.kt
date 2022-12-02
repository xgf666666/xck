package com.example.xck.ui.person.mvp.model

import com.example.xck.bean.Doc
import com.example.xck.common.AppApi
import com.example.xck.ui.person.mvp.contract.AboutUsContract
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable

/**
 *   author ： xiaogf
 *   time    ： 2022/11/18
 *   describe    ：
 */
class AboutUsModel :AboutUsContract.Model {
    override fun getDoc(type: String): Observable<BaseResponseEntity<Doc>> =AppApi.Api().getDoc(type)
}