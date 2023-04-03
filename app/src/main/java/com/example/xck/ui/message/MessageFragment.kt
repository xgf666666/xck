package com.example.xck.ui.message

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.annotation.IntegerRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.bean.CallIm
import com.example.xck.common.Constants
import com.example.xck.ui.message.adapter.MessageAdapter
import com.example.xck.ui.message.mvp.contract.MessageContract
import com.example.xck.ui.message.mvp.persenter.MessagePersenter
import com.example.xck.ui.person.activity.LoginActivity
import com.example.xck.ui.person.activity.RegisterActivity
import com.hyphenate.EMContactListener
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import kotlinx.android.synthetic.main.activity_prepare_login.*
import kotlinx.android.synthetic.main.fragment_message.*


/**
 *   author ： xiaogf
 *   time    ： 2022/10/8
 */
class MessageFragment :BaseMvpFragment<MessagePersenter>(),MessageContract.View{
    var messageAdapter: MessageAdapter? =null
    var allContactsFromServer : MutableList<String> = mutableListOf()
    override fun getFragmentLayoutId(): Int = R.layout.fragment_message
    var hidden=false
    private var ifFirstLoad=false

    override fun init(view: View?) {
        tvToLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            this?.startActivity(intent)
        }
        tvRegister.setOnClickListener {
            startActivity(Intent(context, RegisterActivity::class.java))
        }
        messageAdapter= MessageAdapter()
        rvMessage.layoutManager=LinearLayoutManager(this.context)
        rvMessage.adapter=messageAdapter
        messageAdapter?.data= arrayOf("","").toMutableList()
//        setVisLogin()
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
                    Constants.putFriendIDs(allContactsFromServer)
                }
            }

            override fun onFriendRequestDeclined(username: String?) {
                //好友请求被拒绝。

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
                        Constants.putFriendIDs(allContactsFromServer)
                        Log.i("xgf",allContactsFromServer.toString())
                      
                    } catch (e: HyphenateException) {
                        e.printStackTrace()
                    }
                }).start()
            }
            getPresenter().getGreetingList()
        }else{
            icPrePareLogin.visibility=View.VISIBLE
        }
    }

    override fun getGreetingList(callIm: CallIm) {
        Thread(Runnable {
            var i=0;
            while (i<callIm.active.size){
                allContactsFromServer.forEachIndexed { index, s ->
                    if ("${callIm.active[i].id}" == s){
                        callIm.active.removeAt(i)
                        i--
                        return@forEachIndexed
                    }

                }
                i++
            }
            var y=0
            while (y<callIm.receive.size){
                allContactsFromServer.forEachIndexed { index, s ->
                    if ("${callIm.receive[y].id}" == s){
                        y--
                        return@forEachIndexed
                    }
                }
                y++
            }
            ThreadUtils.runOnUiThread(Runnable {

            })
        })


    }
}