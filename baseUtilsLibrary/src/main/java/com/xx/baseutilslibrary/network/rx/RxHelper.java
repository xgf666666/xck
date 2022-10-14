package com.xx.baseutilslibrary.network.rx;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * RxHelper
 * 沉迷学习不能自拔
 * Describe：
 * Created by 雷小星🍀 on 2017/12/1 14:01.
 */

public class RxHelper {
    /**
     * 线程调度切换
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> io_main() {
        return tObservable -> tObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 添加开始结束监听/
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> start_finish(OnLoadingListener onLoadingListener) {
        return tObservable -> tObservable
                .doOnSubscribe(disposable -> {
                    if (onLoadingListener != null) {
                        onLoadingListener.onStart();
                    }
                })
                .doFinally((Action) () -> {
                    if (onLoadingListener != null) {
                        onLoadingListener.onFinish();
                    }
                });
    }

    public interface OnLoadingListener {
        void onStart();

        void onFinish();
    }

    /**
     * 数据加载状态监听
     */
    public interface OnLoadingStatusListener {

        void onLoadingStart();

        void onLoadingFinish();
    }
}
