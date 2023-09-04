package com.example.xck.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.xck.R
import com.example.xck.base.mvp.BaseActivity
import com.example.xck.base.mvp.BaseFragment
import com.example.xck.base.mvp.contract.BaseMvpView
import com.xx.baseutilslibrary.network.exception.ApiFaileException
import com.xx.baseutilslibrary.network.exception.TokenInvalidException
import retrofit2.HttpException
import java.math.BigDecimal
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import java.text.SimpleDateFormat


/**
 * author: HuaiXianZhong
 * date: 2018/7/10
 * describe:
 */

@SuppressLint("UseRequireInsteadOfGet")
fun BaseMvpView.showToast(throwable:Throwable){
    var msg = ""

    if (throwable is HttpException) {
        val code = throwable.code()
        if (code == 500 || code == 404) {
            msg="服务器错误,请稍后重试"
        }
    } else if (throwable is ConnectException) {
        //断开网络
    } else if (throwable is SocketTimeoutException) {
        msg="连接服务器超时,请稍后重试"
    } else if (throwable is UnknownHostException){
        msg="网络已断开"
    } else if (throwable is ApiFaileException) {
        msg=throwable.message!!//接口请求状态为0的情况
    } else if (throwable is TokenInvalidException) {
        msg="状态异常，请重新登录！"//需要重新登录获取
        if (this is BaseActivity){
//            tokenError((this as Context),msg)
        }else{
            showToast(msg)
        }
        return
    } else {
        msg="未知错误" + throwable.message
    }
    dismissLoadingDialog()

    showToast(msg)
}

/**
 * 获取手机屏幕的宽度
 */
fun getScreenWidth(context: Context): Int {
    //安卓4.2以上提供的方法，判断绝对值的屏幕宽度（忽略导航栏）
    val resources: Resources = context.resources
    val dm: DisplayMetrics = resources.getDisplayMetrics()
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getRealMetrics(dm)
    return dm.widthPixels
}

/**
 * 获取手机屏幕的高度
 */
fun getScreenHeight(context: Context): Int {
    //安卓4.2以上提供的方法，判断绝对值的屏幕高度（忽略导航栏）
    val resources = context.resources
    val dm = resources.displayMetrics
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getRealMetrics(dm)
    return dm.heightPixels
}
/**
 * m 转换 km
 */
fun Int.changeKm() : String{
    if (this >= 1000){
        var d = this / 1000.00
        var toDouble = BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
        return toDouble.toString()+"km"
    }else{
        return this.toString() + "m"
    }

}
fun ImageView.loadImag(url: String) {
    var urltemp=url

//    Glide.get(context).setMemoryCategory(MemoryCategory.HIGH)
    Glide.with(this.context).load(urltemp)
        .placeholder(R.mipmap.icon_base)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate()
        .error(R.mipmap.icon_base)
        .into(this)
}
fun String.getFormFormatTime(): Long {
//		String date = "2001-03-15 15-37-05";
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //24小时制
    var time: Long = 0
    try {
        time = simpleDateFormat.parse(this).time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return time
}



