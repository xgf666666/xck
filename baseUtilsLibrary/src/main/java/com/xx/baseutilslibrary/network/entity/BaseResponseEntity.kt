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
    constructor(code: Int):super(code)

    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果 1为成功
     * @param msg    响应消息
     */
    constructor(code: Int, msg: String):super(code, msg)


    /**
     * 构造方法,模拟数据用
     *
     * @param result 响应结果
     * @param data   响应实体类
     */
    constructor(code: Int, data: T):super(code.toString()) {
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
