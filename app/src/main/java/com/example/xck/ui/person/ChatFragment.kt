package com.example.xck.ui.person
import androidx.fragment.app.Fragment
import com.hyphenate.easeui.modules.chat.EaseChatFragment


/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : EaseChatFragment() {
    override fun initView() {
        super.initView()
        val messageListLayout = chatLayout.chatMessageListLayout
    }

    override fun initListener() {
        super.initListener()
    }

    override fun initData() {
        super.initData()
    }


}