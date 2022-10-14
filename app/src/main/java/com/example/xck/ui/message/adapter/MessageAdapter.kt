package com.example.xck.ui.message.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xck.R

/**
 *   author ： xiaogf
 *   time    ： 2022/10/9
 */
class MessageAdapter : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_message),LoadMoreModule{
    override fun convert(holder: BaseViewHolder, item: String) {
    }
}