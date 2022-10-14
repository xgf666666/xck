package com.xx.baseutilslibrary.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import io.reactivex.annotations.NonNull;

/**
 * 系统应用工具类
 *
 * @author yang
 * @since 2017-10-27 下午5:04:16
 */
public class SystemApplicationUtils {

    /**
     * 拨打电话
     *
     * @param phoneNumber 电话号码
     */
    public static void callPhone(@NonNull Context context, @NonNull String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * 打开浏览器
     *
     * @param url 要打开的网站
     */
    public static void openWebView(@NonNull Context context, @NonNull String url) {
        if (url.isEmpty()) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }


}
