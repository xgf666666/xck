package com.example.xck.ui.home.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xck.R
import com.example.xck.bean.Select

/**
 *   author ： xiaogf
 *   time    ： 2022/10/9
 */
class DialogSelectAdapter :
    BaseQuickAdapter<Select, BaseViewHolder>(R.layout.item_dialog_select_one), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: Select) {
        holder.setText(R.id.tvTilte, item.attr_name)
        var rvSelect = holder.getView<RecyclerView>(R.id.rvSelect)
        rvSelect.layoutManager = GridLayoutManager(context, 4)
        var selectAdapter = DialogSelectOneAdapter()
        rvSelect.adapter = selectAdapter
//        selectAdapter.setNewData(item.children)
        selectAdapter.data = item.children
        selectAdapter.setOnItemClickListener(OnItemClickListener { adapter, view, position ->
            var children = adapter.data[position] as Select.ChildrenBean
            if (position == 0) {
                if (!children.isSelect) {
                    children.isSelect = true
                    for (i in 0 until item.children.size) {
                        var childreni = adapter.data[i] as Select.ChildrenBean
                        if (i != 0) {
                            childreni.isSelect = false;
                        }
                    }

                } else {
                    children.isSelect = false
                }
            } else  {
                if (children.isSelect) {
                    children.isSelect = false
                } else {
                    if (item.children[0].isSelect){
                        item.children[0].isSelect=false
                    }
                    children.isSelect = true
                }
            }
            selectAdapter.notifyDataSetChanged()
        })
    }
}