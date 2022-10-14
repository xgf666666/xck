package com.xx.baseutilslibrary.network.entity

/**
 * ResponseEntity
 * 类描述: 公共响应体
 * 上次更新内容:
 * 上次更新时间:
 * 上次更新作者:
 * 作者: LeiXiaoXing on 2017/1/9 20:08
 */

class BaseResponseEntity<T> :BaseResponseStatusEntity {


    var data: T? = null

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果
     */
    constructor(status: String):super(status)

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果
     */
    constructor(status: Int):super(status)

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果 1为成功
     * @param msg    响应消息
     */
    constructor(status: String, msg: String):super(status, msg)

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果 1为成功
     * @param msg    响应消息
     */
    constructor(status: Int, msg: String):super(status, msg)

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果
     * @param data   响应实体类
     */
    constructor(status: String, data: T):super(status) {
        this.data = data
    }

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果
     * @param data   响应实体类
     */
    constructor(status: Int, data: T):super(status.toString()) {
        this.data = data
    }

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果
     * @param msg    响应消息
     * @param data   响应实体类
     */
    constructor(result: String, msg: String, data: T):super(result,msg) {

        this.data = data
    }

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果
     * @param msg    响应消息
     * @param data   响应实体类
     */
    constructor(status: Int, msg: String, data: T):super(status.toString(),msg) {
        this.data = data
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

        val NEED_COMPLETE_INFO = "NEED_COMPLETE_INFO"
    }
}
