package com.example.xck.common


import com.xx.baseutilslibrary.network.exception.ApiFaileException
import com.xx.baseutilslibrary.network.exception.TokenInvalidException
import io.reactivex.functions.Consumer
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class ExceptionEngine : Consumer<Throwable> {

    @Throws(Exception::class)
    override fun accept(throwable: Throwable) {
        var msg = ""

        if (throwable is HttpException) {
            val code = throwable.code()
            if (code == 500 || code == 404) {
                msg="服务器错误,请稍后重试"
            }
        } else if (throwable is ConnectException) {
            //断开网络
            msg="网络已断开"
        } else if (throwable is UnknownHostException){
            msg="网络已断开"
        } else if (throwable is SocketTimeoutException) {
           msg="连接服务器超时,请稍后重试"
        } else if (throwable is ApiFaileException) {
           msg=throwable.message!!//接口请求状态为0的情况
        } else if (throwable is TokenInvalidException) {
           msg=throwable.message!!//需要重新登录获取
        } else {
           msg="" +throwable.message
        }

        handle(throwable)
    }

    abstract fun handle(msg: Throwable)

    companion object {
        private val TAG = "ExceptionEngine"
    }
}