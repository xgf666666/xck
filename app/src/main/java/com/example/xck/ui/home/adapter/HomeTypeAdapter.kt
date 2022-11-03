package com.example.xck.ui.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xck.R
import com.example.xck.bean.Capitalist

/**
 *   author ： xiaogf
 *   time    ： 2022/11/3
 *   describe    ：
 */
class HomeTypeAdapter :BaseQuickAdapter<Capitalist.AttrListBean,BaseViewHolder>(R.layout.item_home_type){
    override fun convert(holder: BaseViewHolder, item: Capitalist.AttrListBean) {
        holder.setText(R.id.tvType,item.attr_name)
    }
}