package com.example.xck.ui.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xck.R
import com.example.xck.bean.Project

/**
 *   author ： xiaogf
 *   time    ： 2022/10/9
 */
class HomeProjectAdapter : BaseQuickAdapter<Project,BaseViewHolder>(R.layout.item_home),LoadMoreModule{

    override fun convert(holder: BaseViewHolder, item: Project) {
        holder.setText(R.id.tvName,item.project_name)
        var type=""
        var address=""
        for (i in 0 until item.attr_list.size){
            if (i==0){
                type += item.attr_list[i].attr_name
            }else{
                type += "|"+item.attr_list[i].attr_name
            }
            if (item.attr_list[i].attr_parent_id==2){
                address=item.attr_list[i].attr_name
            }
        }
        holder.setText(R.id.tvCompany,type)
        holder.setText(R.id.tvCapital,address)
        holder.setText(R.id.tvCase,item.introduction)

    }
}