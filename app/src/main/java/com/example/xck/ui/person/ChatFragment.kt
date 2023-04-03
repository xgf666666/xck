package com.example.xck.ui.person
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.example.xck.common.AppApi
import com.example.xck.common.Constants
import com.example.xck.extensions.ui
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.constants.EaseConstant
import com.hyphenate.easeui.event.CallEvet
import com.hyphenate.easeui.event.FriendRequestEvent
import com.hyphenate.easeui.event.ReportQuotaEvent
import com.hyphenate.easeui.manager.EaseThreadManager
import com.hyphenate.easeui.modules.chat.EaseChatFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : EaseChatFragment() {
    override fun initView() {
        super.initView()
        EventBus.getDefault().register(this)
        val messageListLayout = chatLayout.chatMessageListLayout
        val bundle = arguments
        if (bundle != null) {
            conversationId = bundle.getString(EaseConstant.EXTRA_CONVERSATION_ID)
            chatType = bundle.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE)
            historyMsgId = bundle.getString(EaseConstant.HISTORY_MSG_ID)
            isRoam = bundle.getBoolean(EaseConstant.EXTRA_IS_ROAM, false)
        }
    }

    override fun initListener() {
        super.initListener()
    }

    override fun initData() {
        super.initData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            sendFriendRequst()
        }
    }

    /**
     * 同意好友请求
     */
    private fun sendFriendRequst(){
        EaseThreadManager.getInstance().runOnIOThread {
            if (!Constants.getFriendIDs().contains(conversationId)){
                EMClient.getInstance().contactManager().acceptInvitation(conversationId);
            }

        }


    }
    /**
     * 同意好友请求
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun friendRequestEvent(event: FriendRequestEvent){
        sendFriendRequst()
    }
    /**
     * 打招呼
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun callEvet(event: CallEvet){
        AppApi.Api().reportQuota(Constants.getToken()).ui({
//            if (it.data!!.quota_num<=0){
                EventBus.getDefault().post(ReportQuotaEvent())
//            }
        },{
            ToastUtils.showShort(it.message)
        })
        AppApi.Api().sendCall(Constants.getToken(),conversationId,1).ui({
//                  ToastUtils.showShort("成功")
              },{
//                ToastUtils.showShort("失败")
              })
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }


}