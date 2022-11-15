package com.example.xck.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xck.R
import com.example.xck.bean.Capitalist

/**
 *   author ： xiaogf
 *   time    ： 2022/10/9
 */
class HomeAdapter : BaseQuickAdapter<Capitalist,BaseViewHolder>(R.layout.item_home),LoadMoreModule{

    override fun convert(holder: BaseViewHolder, item: Capitalist) {
        holder.setText(R.id.tvName,item.contact_name)
        holder.setText(R.id.tvCompany,item.capitalist_name+"|"+item.position)
        holder.setText(R.id.tvCase,"投资案例:"+item.cases)
        holder.setText(R.id.tvCapital,item.single_amount)
        var rcType=holder.getView<RecyclerView>(R.id.rcType)
        rcType.visibility=View.VISIBLE
        var layoutManager=LinearLayoutManager(context)
        layoutManager.orientation=LinearLayoutManager.HORIZONTAL
        rcType.layoutManager=layoutManager
        var adapter=HomeTypeAdapter()
        rcType.adapter=adapter
        adapter.data=item.attr_list
    }
}