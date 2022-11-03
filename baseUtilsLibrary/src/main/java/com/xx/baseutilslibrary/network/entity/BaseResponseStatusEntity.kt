package com.xx.baseutilslibrary.network.entity

/**
 * ResponseEntity
 * 类描述: 公共响应体
 * 上次更新内容:
 * 上次更新时间:
 * 上次更新作者:
 * 作者: LeiXiaoXing on 2017/1/9 20:08
 */

open class BaseResponseStatusEntity {

    var code: Int? = null
    var msg: String? = null



    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果
     */
    constructor(code: Int) {
        this.code = code
    }

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果 1为成功
     * @param msg    响应消息
     */
    constructor( msg: String) {
        this.msg = msg
    }

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果 1为成功
     * @param msg    响应消息
     */
    constructor(code: Int, msg: String) {
        this.code = code
        this.msg = msg
    }


    companion object {
        /**
         * 成功状态
         */
        val SUCCESS = "1"
        /**
         * 失败状态
         */
        val FAILE = "0"
    }
}
