package com.xx.baseutilslibrary.network.rx;


import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * RetryWhenProcess
 * 沉迷学习不能自拔
 * Describe：
 * Created by 雷小星🍀 on 2017/12/1 16:19.
 */

public class RetryWhenProcess implements Function<Observable<? extends Throwable>, Observable<?>> {


    /**
     * 最大重连次数
     */
    private int maxRetries;
    /**
     * 每次重连间隔时间，毫秒单位
     */
    private Long retryDelayMillis;

    /**
     * 当前重连次数
     */
    private int retryCount;


    public RetryWhenProcess(int maxRetries, Long retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable
                .flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    if (throwable instanceof UnknownHostException) {
                        //没有连接网络，直接返回错误信息
                        return Observable.error(throwable);
                    }
                    if (++retryCount <= maxRetries) {
                        //重连最大次数范围内
                        return Observable
                                .timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                    }
                    //超过次数返回异常信息
                    return Observable.error(throwable);
                });
    }
}
