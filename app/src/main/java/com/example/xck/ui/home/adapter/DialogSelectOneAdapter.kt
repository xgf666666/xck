package com.example.xck.ui.home.adapter

import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xck.R
import com.example.xck.bean.Select

/**
 *   author ： xiaogf
 *   time    ： 2022/10/9
 */
class DialogSelectOneAdapter : BaseQuickAdapter<Select.ChildrenBean,BaseViewHolder>(R.layout.item_dialog_select),LoadMoreModule{
    override fun convert(holder: BaseViewHolder, item: Select.ChildrenBean) {
        holder.setText(R.id.tvType,item.attr_name)
        var tvType=holder.getView<TextView>(R.id.tvType)
        if (item.isSelect){
            tvType.setBackgroundResource(R.drawable.dialog_select)
        }else{
            tvType.setBackgroundResource(R.drawable.dialog_unselect)
        }

    }
}