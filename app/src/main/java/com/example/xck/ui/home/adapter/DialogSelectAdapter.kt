package com.example.xck.ui.home.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xck.R
import com.example.xck.bean.Select
import kotlinx.android.synthetic.main.dialog_select.*

/**
 *   author ： xiaogf
 *   time    ： 2022/10/9
 */
class DialogSelectAdapter : BaseQuickAdapter<Select,BaseViewHolder>(R.layout.item_dialog_select_one),LoadMoreModule{
    override fun convert(holder: BaseViewHolder, item: Select) {
        holder.setText(R.id.tvTilte,item.attr_name)
        var rvSelect=holder.getView<RecyclerView>(R.id.rvSelect)
        rvSelect.layoutManager= GridLayoutManager(context,4)
        var selectAdapter=DialogSelectOneAdapter()
        rvSelect.adapter=selectAdapter
//        selectAdapter.setNewData(item.children)
        selectAdapter.data=item.children
    }
}