package com.example.xck.ui.person.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.xck.R
import com.example.xck.base.mvp.BaseActivity
import com.example.xck.ui.person.ChatFragment
import com.hyphenate.easeui.constants.EaseConstant
import com.hyphenate.easeui.widget.EaseTitleBar.OnBackPressListener
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.view.*
import kotlinx.android.synthetic.main.gridview_item.view.*

class ChatActivity : BaseActivity() {
    private var fragment: ChatFragment? = null
    private var conversationId: String? = null
    private var chatType = 0
    private var historyMsgId: String? = null
    override fun getActivityLayoutId(): Int = R.layout.activity_chat
    companion object{
        fun  actionStart(context: Context, conversationId: String?, chatType: Int,name:String) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, conversationId)
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType)
            intent.putExtra("name", name)
            context.startActivity(intent)
        }
    }
    override fun initData() {
        conversationId = intent.getStringExtra(EaseConstant.EXTRA_CONVERSATION_ID)
        chatType = intent.getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE)
        title_bar_message.title_bar_message.setTitle(intent.getStringExtra("name"))
        initChatFragment()
    }

    override fun initEvent() {
        title_bar_message.setLeftImageResource(R.mipmap.iv_back)

        title_bar_message.setOnBackPressListener {
            finish()
        }
    }
    private fun initChatFragment() {
        fragment = ChatFragment()
        val bundle = Bundle()
        bundle.putString(EaseConstant.EXTRA_CONVERSATION_ID, conversationId)
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, chatType)
        bundle.putString(EaseConstant.HISTORY_MSG_ID, historyMsgId)
        fragment!!.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fl_fragment, fragment!!, "chat")
            .commit()
    }

}