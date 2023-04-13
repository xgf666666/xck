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
import com.hyphenate.easeui.constants.EaseCommom
import com.hyphenate.easeui.constants.EaseConstant
import com.hyphenate.easeui.constants.UserMessage
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
                EaseCommom.getInstance().userMessage=bean.userMessage
                ChatActivity.actionStart(
                    it,"${bean.id}", EaseConstant.CHATTYPE_SINGLE,
                    bean.name)
            }
        }
        EMClient.getInstance().contactManager().setContactListener(object : EMContactListener {//好友请求回调
            override fun onContactAdded(username: String?) {//同意别人的好友请求
            if (username != null) {
                allContactsFromServer.add(username)
//                    Constants.putFriendIDs(allContactsFromServer)
                call?.let { setMessageInfo(it) }
            }
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
                    call?.let { setMessageInfo(it) }
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
                    getPresenter().getGreetingList()
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
                        getPresenter().getGreetingList()
//                        setMessageInfo()
                    } catch (e: HyphenateException) {
                        e.printStackTrace()
                    }
                }).start()
            }else{
                getPresenter().getGreetingList()
//                setMessageInfo()
            }
        }else{
            icPrePareLogin.visibility=View.VISIBLE
        }
    }
    private var call: CallIm? =null
    override fun getGreetingList(call: CallIm) {//得到打招呼列表
        this.call=call
        setMessageInfo(call)
    }
    fun setMessageInfo(call: CallIm){//对打招呼列表和沟通中列表处理
        Thread(Runnable {
            callUsers.clear()
            friendUsers.clear()

            var users=Constants.getUserDetail()//得到缓存的用户信息
            call.active.forEach {
                var isHas=false
                users.forEachIndexed { index, user ->
                    if (it.from_user_id==user.id){
                        isHas=true
                        callUsers.add(user)
                        return@forEachIndexed
                    }
                }
                if (!isHas){
                    var userBean=User()
                    userBean.id=it.from_user_id
                    callUsers.add(userBean)
                    if (Constants.getPersonal().user_type_select==1){//创业者
                        getPresenter().getInverstorDetail(Constants.getToken(),it.from_user_id)
                    }else {
                        getPresenter().getProjectDetail(Constants.getToken(),it.from_user_id)
                    }
                }
            }
            call.receive.forEach {
                var isHas=false
                users.forEachIndexed { index, user ->
                    if (it.user_id==user.id){
                        isHas=true
                        callUsers.add(user)
                        return@forEachIndexed
                    }
                }
                if (!isHas){
                    var userBean=User()
                     userBean.id=it.user_id
                    callUsers.add(userBean)
                    if (Constants.getPersonal().user_type_select==1){//创业者
                        getPresenter().getInverstorDetail(Constants.getToken(),it.user_id)
                    }else {
                        getPresenter().getProjectDetail(Constants.getToken(),it.user_id)
                    }
                }
            }



            var chat=EMClient.getInstance().chatManager().allConversations//获取所有会话
            var iterator=chat.iterator()
            while (iterator.hasNext()){
                var next=iterator.next()
                callUsers.forEachIndexed { index, user ->
                    if (next.key.equals("${user.id}")){
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
                        return@forEachIndexed
                    }
                }
            }
            callUsers.sortByDescending {
                TimeUtil.getFormFormatTime(it.time)

            }


            allContactsFromServer.forEach {
                var i = 0
                var isHas=false
                while (i < callUsers.size) {
                    if (callUsers[i].id==it.toInt()){
                        isHas=true
                        friendUsers.add(callUsers[i]);
                        callUsers.removeAt(i)
                        i--
                        break
                    }
                    i++
                }
                if (!isHas){
                    var user=User()
                    user.id=it.toInt()
                    friendUsers.add(user);
                    if (Constants.getPersonal().user_type_select==1){//创业者
                        getPresenter().getInverstorDetail(Constants.getToken(),user.id)
                    }else {
                        getPresenter().getProjectDetail(Constants.getToken(),user.id)
                    }
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
            var userMessage = UserMessage()
            var inverstor=""
            var address=""
            var trade=""
            for (i in 0 until capitalist.attr_list.size){
                if (capitalist.attr_list[i].attr_parent_id==3){
                    inverstor+=capitalist.attr_list[i].attr_name+"  "
                }else if (capitalist.attr_list[i].attr_parent_id==2){
                    address+=capitalist.attr_list[i].attr_name+"  "
                }else if (capitalist.attr_list[i].attr_parent_id==1){
                    trade+=capitalist.attr_list[i].attr_name+"  "
                }
            }
            userMessage.id=capitalist.user_id
            userMessage.financing=inverstor
            userMessage.address=address
            userMessage.name=capitalist.capitalist_name
            userMessage.position=capitalist.contact_name
            userMessage.describe=capitalist.introduction
            userMessage.logo=capitalist.avatar
            userMessage.trade=trade
            callUsers.forEachIndexed { index, user ->
                if (user.id==capitalist.user_id){
                    user.avatar=capitalist.logo_image
                    user.name=capitalist.capitalist_name
                    user.userMessage=userMessage
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
                        user.userMessage=userMessage
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
            var userMessage = UserMessage()
            var inverstor=""
            var address=""
            var trade=""
            if (project.attr_list!=null){
                for (i in 0 until project.attr_list.size){
                    if (project.attr_list[i].attr_parent_id==3){
                        inverstor+=project.attr_list[i].attr_name+"  "
                    }else if (project.attr_list[i].attr_parent_id==2){
                        address+=project.attr_list[i].attr_name+"  "
                    }else if (project.attr_list[i].attr_parent_id==1){
                        trade+=project.attr_list[i].attr_name+"  "
                    }
                }
            }
            userMessage.id=project.user_id
            userMessage.financing=inverstor
            userMessage.address=address
            userMessage.name=project.project_name
//            userMessage.position=project.position
            userMessage.describe=project.introduction
            userMessage.logo=project.logo_image
            userMessage.trade=trade
            var isHas=false
            callUsers.forEachIndexed { index, user ->
                if (user.id == project.user_id) {
                    user.avatar = project.logo_image
                    user.name = project.project_name
                    user.userMessage=userMessage
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
                            user.userMessage=userMessage
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