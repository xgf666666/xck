package com.example.xck.ui.home.adapter

import android.widget.ImageView
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xck.R
import com.example.xck.bean.Project
import com.example.xck.utils.loadImag
import com.facebook.stetho.common.StringUtil

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
            if (item.attr_list[i].attr_parent_id!=2){
                if (StringUtils.isEmpty(type)){
                    type += item.attr_list[i].attr_name
                }else{
                    type += "|"+item.attr_list[i].attr_name
                }
            }

            if (item.attr_list[i].attr_parent_id==2){
                address=item.attr_list[i].attr_name
            }
        }
        holder.setText(R.id.tvCompany,type)
        holder.setText(R.id.tvCapital,address)
        holder.setText(R.id.tvCase,item.introduction)
        var iv_person=holder.getView<ImageView>(R.id.iv_person)
        iv_person.loadImag(item.logo_image)
    }
}