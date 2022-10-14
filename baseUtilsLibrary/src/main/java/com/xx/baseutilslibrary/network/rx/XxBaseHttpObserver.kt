package com.xx.baseutilslibrary.network.rx


import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import com.xx.baseutilslibrary.network.exception.ApiFaileException
import com.xx.baseutilslibrary.network.exception.TokenInvalidException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException


/**
 * XxBaseHttpObserver
 * (๑• . •๑)
 * 类描述: RxJava的Observer封装
 * Created by 雷小星🍀 on 2017/6/2 11:01
 */

abstract class XxBaseHttpObserver<T> : Observer<BaseResponseEntity<T>>, RxHelper.OnLoadingListener {
    companion object {
        val BIND_CELL_PHONE = "cellPhone"
    }

    abstract fun onCompleted(msg: String?, entity: T?)

    abstract fun onError(error: String?)

    override fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        if (throwable is HttpException) {
            val code = throwable.code()
            if (code == 500 || code == 404) {
                onError("服务器错误,请稍后重试")
            }
        } else if (throwable is ConnectException) {
            //断开网络
        } else if (throwable is SocketTimeoutException) {
            onError("连接服务器超时,请稍后重试")
        } else if (throwable is ApiFaileException) {
            onError(throwable.message)//接口请求状态为0的情况
        } else if (throwable is TokenInvalidException) {
            onError(throwable.message)//需要重新登录获取
        } else {
            onError("未知错误" + throwable.message)
        }
    }

    override fun onComplete() {}

    override fun onSubscribe(d: Disposable) {}

    override fun onNext(responseBean: BaseResponseEntity<T>) {
        if (responseBean.status == BaseResponseEntity.SUCCESS) {
            //正常从接口获取到数据
            if (responseBean.code == BaseResponseEntity.NEED_COMPLETE_INFO){
                onCompleted(BaseResponseEntity.NEED_COMPLETE_INFO, responseBean.data)
            }else{
                onCompleted(responseBean.msg, responseBean.data)
            }
        } else {
            //接口返回的错误描述
            onError(responseBean.msg)
        }
    }

}
