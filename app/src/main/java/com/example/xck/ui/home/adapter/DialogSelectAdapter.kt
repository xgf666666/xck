package com.example.xck.ui.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xck.R

/**
 *   author ： xiaogf
 *   time    ： 2022/10/9
 */
class DialogSelectAdapter : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_dialog_select),LoadMoreModule{
    override fun convert(holder: BaseViewHolder, item: String) {
    }
}