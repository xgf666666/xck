package com.xx.baseutilslibrary.network.provider;

/**
 * JApiConfigProvider
 * (。・∀・)ノ
 * Describe：
 * Created by 雷小星🍀 on 2017/10/31 17:48.
 */

public interface JApiConfigProvider {
    /**
     * 获取Api绝对路径
     *
     * @return 接口基础路径
     */
    default String getApiBaseUrl() {
        return getBaseUrl();
    }

    /**
     * 获取Api相对路径
     *
     * @return
     */
    String getBaseUrl();

}
