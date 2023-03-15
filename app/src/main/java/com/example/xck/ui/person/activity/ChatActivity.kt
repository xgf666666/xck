package com.example.xck.ui.person.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.xck.R
import com.example.xck.base.mvp.BaseActivity
import com.example.xck.ui.person.ChatFragment
import com.hyphenate.easeui.constants.EaseConstant

class ChatActivity : BaseActivity() {
    private var fragment: ChatFragment? = null
    private var conversationId: String? = null
    private var chatType = 0
    private var historyMsgId: String? = null
    override fun getActivityLayoutId(): Int = R.layout.activity_chat
    companion object{
        fun  actionStart(context: Context, conversationId: String?, chatType: Int) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, conversationId)
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType)
            context.startActivity(intent)
        }
    }
    override fun initData() {
        conversationId = intent.getStringExtra(EaseConstant.EXTRA_CONVERSATION_ID)
        chatType = intent.getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE)
        initChatFragment()
    }

    override fun initEvent() {
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