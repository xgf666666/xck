package com.example.xck .ui.message

import android.content.Intent
import android.util.Log
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ThreadUtils
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.bean.CallIm
import com.example.xck.bean.Capitalist
import com.example.xck.bean.Project
import com.example.xck.bean.User
import com.example.xck.common.Constants
import com.example.xck.ui.message.adapter.MessageAdapter
import com.example.xck.ui.message.mvp.contract.MessageContract
import com.example.xck.ui.message.mvp.persenter.MessagePersenter
import com.example.xck.ui.person.activity.ChatActivity
import com.example.xck.ui.person.activity.LoginActivity
import com.example.xck.ui.person.activity.RegisterActivity
import com.example.xck.utils.TimeUtil
import com.hyphenate.EMContactListener
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.chat.adapter.message.EMATextMessageBody
import com.hyphenate.easeui.constants.EaseConstant
import com.hyphenate.exceptions.HyphenateException
import kotlinx.android.synthetic.main.activity_prepare_login.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_message.et_search


/**
 *   author ： xiaogf
 *   time    ： 2022/10/8
 */
class MessageFragment :BaseMvpFragment<MessagePersenter>(),MessageContract.View{
    var messageAdapter: MessageAdapter? =null
    var allContactsFromServer : MutableList<String> = mutableListOf()
    var callUsers : MutableList<User> = mutableListOf()//打招呼adapter的Data
    var friendUsers : MutableList<User> = mutableListOf()//沟通中adapter的Data
    var search : MutableList<User> = mutableListOf()//搜索的Data
    private var isCall=false
    override fun getFragmentLayoutId(): Int = R.layout.fragment_message
    var hidden=false
    private var ifFirstLoad=false

    override fun init(view: View?) {

        messageAdapter= MessageAdapter()
        rvMessage.layoutManager=LinearLayoutManager(this.context)
        rvMessage.adapter=messageAdapter
        messageAdapter!!.setNewInstance(friendUsers)
        messageAdapter!!.setOnItemClickListener { adapter, view, position ->
            var bean = adapter.data[position] as User
            context?.let {
                ChatActivity.actionStart(
                    it,"${bean.id}", EaseConstant.CHATTYPE_SINGLE,
                    bean.name)
            }
        }
        EMClient.getInstance().contactManager().setContactListener(object : EMContactListener {//好友请求回调
            override fun onContactAdded(username: String?) {
            }

            override fun onContactDeleted(username: String?) {
            }

            override fun onContactInvited(username: String?, reason: String?) {
            }

            override fun onFriendRequestAccepted(username: String?) {
                //好友请求被同意
                if (username != null) {
                    allContactsFromServer.add(username)
//                    Constants.putFriendIDs(allContactsFromServer)
                    setMessageInfo()
                }
            }

            override fun onFriendRequestDeclined(username: String?) {
                //好友请求被拒绝。

            }


        })
        initEvent()
    }

    private fun initEvent() {
        tvToLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            this?.startActivity(intent)
        }
        tvRegister.setOnClickListener {
            startActivity(Intent(context, RegisterActivity::class.java))
        }
        rgSel.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.tvCommunicate){
                isCall=false
                messageAdapter?.setNewInstance(friendUsers)
                tvCommunicate.setTextSize(COMPLEX_UNIT_DIP,19f)
                tvCall.setTextSize(COMPLEX_UNIT_DIP,17f)
            }else{
                isCall=true
                messageAdapter?.setNewInstance(callUsers)
                tvCommunicate.setTextSize(COMPLEX_UNIT_DIP,17f)
                tvCall.setTextSize(COMPLEX_UNIT_DIP,19f)
            }
        }
        et_search.setOnEditorActionListener { v, actionId, event ->
            var keyword=v.text.toString()
            search.clear()
            if (!isCall){//沟通中
                if (keyword.isBlank()){
                    if (messageAdapter!!.data==friendUsers){
                        messageAdapter!!.notifyDataSetChanged()
                    }else{
                        messageAdapter!!.setNewInstance(friendUsers)
                    }
                    return@setOnEditorActionListener true
                }
                friendUsers.forEachIndexed { index, user ->
                    if (user.name.contains(keyword)){
                        search.add(user)
                    }
                }
                if (messageAdapter!!.data==search){
                    messageAdapter!!.notifyDataSetChanged()
                }else{
                    messageAdapter!!.setNewInstance(search)
                }
            }else{//打招呼
                if (keyword.isBlank()){
                    if (messageAdapter!!.data==callUsers){
                        messageAdapter!!.notifyDataSetChanged()
                    }else{
                        messageAdapter!!.setNewInstance(callUsers)
                    }
                    return@setOnEditorActionListener true
                }
                callUsers.forEachIndexed { index, user ->
                    if (user.name.contains(keyword)){
                        search.add(user)
                    }
                }
                if (messageAdapter!!.data==search){
                    messageAdapter!!.notifyDataSetChanged()
                }else{
                    messageAdapter!!.setNewInstance(search)
                }
            }
            true
        }
        EMClient.getInstance().chatManager().addMessageListener(object : EMMessageListener {
            override fun onMessageReceived(messages: List<EMMessage?>?) {
              Log.i("xgf","收到一条消息")
                if (!hidden){
                    setMessageInfo()
                }
            }

            override fun onCmdMessageReceived(messages: List<EMMessage?>?) {
                // 处理收到的透传消息
            }

            override fun onMessageRead(messages: List<EMMessage?>?) {
                // 处理消息已被对方阅读的事件
            }

            override fun onMessageDelivered(messages: List<EMMessage?>?) {
                // 处理消息已发送到对方设备的事件
            }

            override fun onMessageChanged(message: EMMessage?, change: Any?) {
                // 处理消息状态变化的事件
            }
        })

    }

    override fun onResume() {
        super.onResume()
        if (!hidden) {
            setVisLogin()
        }
    }

    override fun createPresenter(): MessagePersenter = MessagePersenter(this)
    override fun onHiddenChanged(hidden: Boolean) {
        this.hidden=hidden
        if (!hidden)
            setVisLogin()
    }
    private fun setVisLogin(){
        if (Constants.isLogin()){
            icPrePareLogin.visibility=View.GONE
            if (!ifFirstLoad){
                ifFirstLoad=true
                Thread(Runnable {
                    try {
                        allContactsFromServer = EMClient.getInstance().contactManager().allContactsFromServer
//                        Constants.putFriendIDs(allContactsFromServer)
                        Log.i("xgf",allContactsFromServer.toString())
//                        getPresenter().getGreetingList()
                        setMessageInfo()
                    } catch (e: HyphenateException) {
                        e.printStackTrace()
                    }
                }).start()
            }else{
//                getPresenter().getGreetingList()
                setMessageInfo()
            }
        }else{
            icPrePareLogin.visibility=View.VISIBLE
        }
    }

    override fun getGreetingList(call: CallIm) {//得到打招呼列表


    }
    fun setMessageInfo(){//对打招呼列表和沟通中列表处理
        Thread(Runnable {
            var users=Constants.getUserDetail()//得到缓存的用户信息
            callUsers.clear()
            friendUsers.clear()
            var chat=EMClient.getInstance().chatManager().allConversations//获取所有会话
            var iterator=chat.iterator()
            while (iterator.hasNext()){
                var next=iterator.next()
                var isHas=false
                users.forEachIndexed { index, user ->
                    if (next.key.equals("${user.id}")){
                        isHas=true
                        val lastMessage = next.value.lastMessage
                        if (lastMessage!=null){
                            if (lastMessage.body is EMTextMessageBody){
                                user.message=(lastMessage.body as EMTextMessageBody).message
                            }else{
                                user.message=lastMessage.body.toString()
                            }
                            user.time=TimeUtil.getDate(lastMessage.msgTime)
                        }
                        user.messageNum=next.value.unreadMsgCount
                        callUsers.add(user)
                        return@forEachIndexed
                    }
                }
                if (!isHas){
                    var user=User()
                    user.id=next.key.toInt()
                    val lastMessage = next.value.lastMessage
                    if (lastMessage.body is EMATextMessageBody){
                        user.message=(lastMessage.body as EMATextMessageBody).text()
                    }else{
                        user.message=lastMessage.body.toString()
                    }

                    user.messageNum=next.value.unreadMsgCount
                    user.time=TimeUtil.getDate(next.value.lastMessage.msgTime)
                    callUsers.add(user)
                    if (Constants.getPersonal().user_type_select==1){//创业者
                        getPresenter().getInverstorDetail(Constants.getToken(),user.id)
                    }else {
                        getPresenter().getProjectDetail(Constants.getToken(),user.id)
                    }
                }
            }
            callUsers.sortByDescending {
                TimeUtil.getFormFormatTime(it.time)

            }


            allContactsFromServer.forEach {
                var i = 0
                while (i < callUsers.size) {
                    if (callUsers[i].id==it.toInt()){
                        friendUsers.add(callUsers[i]);
                        callUsers.removeAt(i)
                        i--
                        break
                    }
                    i++
                }
            }
            friendUsers.sortByDescending {
                TimeUtil.getFormFormatTime(it.time)

            }

            ThreadUtils.runOnUiThread(Runnable {
                    messageAdapter?.notifyDataSetChanged()
            })
        }).start()

    }

    /**
     * 机构详情
     */
    override fun getInverstorDetail(capitalist: Capitalist,user_id: Int) {
        Thread(Runnable {
            var isHas=false
            if (capitalist.user_id==0){
                EMClient.getInstance().chatManager().deleteConversation("$user_id",true)
            }
            callUsers.forEachIndexed { index, user ->
                if (user.id==capitalist.user_id){
                    user.avatar=capitalist.logo_image
                    user.name=capitalist.capitalist_name
                    Constants.putUserDetail(user)
                    isHas=true
                    return@forEachIndexed
                }
            }
            if (!isHas){
                friendUsers.forEachIndexed { index, user ->
                    if (user.id==capitalist.user_id){
                        user.avatar=capitalist.logo_image
                        user.name=capitalist.capitalist_name
                        Constants.putUserDetail(user)
                        return@forEachIndexed
                    }
                }
            }
            ThreadUtils.runOnUiThread(Runnable {
                messageAdapter?.notifyDataSetChanged()
            })
        }).start()
    }

    /**
     * 项目详情
     */
    override fun getProjectDetail(project: Project,user_id: Int) {
        Thread(Runnable {
            if (project.user_id==0){
                EMClient.getInstance().chatManager().deleteConversation("$user_id",true)
            }
            var isHas=false
            callUsers.forEachIndexed { index, user ->
                if (user.id == project.user_id) {
                    user.avatar = project.logo_image
                    user.name = project.project_name
                    Constants.putUserDetail(user)
                    isHas = true
                    return@forEachIndexed
                }
            }

                if (!isHas){
                    friendUsers.forEachIndexed { index, user ->
                        if (user.id==project.user_id){
                            user.avatar=project.logo_image
                            user.name=project.project_name
                            Constants.putUserDetail(user)
                            return@forEachIndexed
                        }
                    }
                }
            ThreadUtils.runOnUiThread(Runnable {
                messageAdapter?.notifyDataSetChanged()
            })

            }).start()
    }
}