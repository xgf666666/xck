package com.example.xck.common

import android.content.Context
import com.blankj.utilcode.util.StringUtils
import java.util.regex.Pattern


/*fun String.md5Salt(): String =
        EncryptUtils.encryptMD5ToString(this)
                .toLowerCase()*/

fun Int.pxtodp(context: Context): Int {
    var scale = context.getResources().getDisplayMetrics().density
    return (this * scale + 0.5f).toInt()

}

fun String.isPhone(): Boolean {
    // ^ 匹配输入字符串开始的位置
    // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
    // $ 匹配输入字符串结尾的位置
    var regExp = "^1[3-9]{1}[0-9]{9}\$"
    var p = Pattern.compile(regExp)
    var m = p.matcher(this)
    return m.matches()
}

/**
 * 获取文件后缀名
 */
fun getsuffix(str: String): String? {
    return if (StringUtils.isEmpty(str)) {
        ""
    } else str.substring(str.lastIndexOf(".") + 1)
}

/*
     *获取文件名
     */
fun getFileName(pathandname: String): String? {
    val start = pathandname.lastIndexOf("/")
    return if (start != -1) {
        pathandname.substring(start + 1)
    } else {
        null
    }
}
