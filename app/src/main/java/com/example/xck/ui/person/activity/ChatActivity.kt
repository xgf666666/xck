package com.example.xck.ui.person.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.xck.R
import com.example.xck.base.mvp.BaseActivity
import com.example.xck.databinding.ActivityChatBinding
import com.example.xck.ui.person.ChatFragment
import com.hyphenate.easeui.constants.EaseConstant


class ChatActivity : BaseActivity() {
    private var fragment: ChatFragment? = null
    private var conversationId: String? = null
    private var chatType = 0
    private var historyMsgId: String? = null
    private val mBinding by lazy { ActivityChatBinding.inflate(layoutInflater) }
    companion object{
        fun  actionStart(context: Context, conversationId: String?, chatType: Int,name:String) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, conversationId)
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType)
            intent.putExtra("name", name)
            context.startActivity(intent)
        }
    }

    override fun getViewBinding(): ViewBinding {
       return mBinding
    }

    override fun initData() {
        conversationId = intent.getStringExtra(EaseConstant.EXTRA_CONVERSATION_ID)
        chatType = intent.getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE)
        mBinding.titleBarMessage.setTitle(intent.getStringExtra("name"))
        initChatFragment()
    }

    override fun initEvent() {
        mBinding.titleBarMessage.setLeftImageResource(R.mipmap.iv_back)

        mBinding.titleBarMessage.setOnBackPressListener {
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