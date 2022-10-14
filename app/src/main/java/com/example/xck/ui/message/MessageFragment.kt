package com.example.xck.ui.message

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.ui.message.adapter.MessageAdapter
import com.example.xck.ui.message.mvp.contract.MessageContract
import com.example.xck.ui.message.mvp.persenter.MessagePersenter
import kotlinx.android.synthetic.main.fragment_message.*


/**
 *   author ： xiaogf
 *   time    ： 2022/10/8
 */
class MessageFragment :BaseMvpFragment<MessagePersenter>(),MessageContract.View{
    var messageAdapter: MessageAdapter? =null
    override fun getFragmentLayoutId(): Int = R.layout.fragment_message

    override fun init(view: View?) {
        messageAdapter= MessageAdapter()
        rvMessage.layoutManager=LinearLayoutManager(this.context)
        rvMessage.adapter=messageAdapter
        messageAdapter?.data= arrayOf("","").toMutableList()
    }

    override fun createPresenter(): MessagePersenter = MessagePersenter(this)
}