package com.example.xck.ui.message.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xck.R
import com.example.xck.bean.User
import com.example.xck.ui.message.mvp.contract.MessageContract
import com.example.xck.utils.loadImag
import de.hdodenhof.circleimageview.CircleImageView

/**
 *   author ： xiaogf
 *   time    ： 2022/10/9
 */
class MessageAdapter : BaseQuickAdapter<User,BaseViewHolder>(R.layout.item_message),LoadMoreModule{
    override fun convert(holder: BaseViewHolder, item: User) {
        val ivPerson=holder.getView<CircleImageView>(R.id.iv_person)

        if (item.avatar!=null){
            ivPerson.loadImag(item.avatar)
        }
        if (item.userMessage!=null){
            holder.setText(R.id.tvName,"${item.userMessage.userName} | ${item.name}")
        }
        holder.setText(R.id.tvMessage,item.message)
        holder.setText(R.id.tvTime,item.time)
        var tvNum=holder.getView<TextView>(R.id.tvNum)
        if (item.messageNum==0){
            tvNum.visibility=View.INVISIBLE
        }else{
            tvNum.visibility=View.VISIBLE
            holder.setText(R.id.tvNum,"${item.messageNum}")

        }
    }
}