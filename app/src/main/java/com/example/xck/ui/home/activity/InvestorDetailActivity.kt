package com.example.xck.ui.home.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ThreadUtils
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.CallIm
import com.example.xck.bean.Capitalist
import com.example.xck.bean.User
import com.example.xck.bean.UserQuotaNum
import com.example.xck.common.Constants
import com.example.xck.ui.home.adapter.HomeTypeAdapter
import com.example.xck.ui.home.mvp.contract.InvestorDetailContract
import com.example.xck.ui.home.mvp.persenter.InvestorDetailPersenter
import com.example.xck.ui.person.activity.ChatActivity
import com.example.xck.utils.loadImag
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.constants.EaseCommom
import com.hyphenate.easeui.constants.EaseConstant
import com.hyphenate.easeui.constants.UserMessage
import kotlinx.android.synthetic.main.activity_investor_detail.*
import kotlinx.android.synthetic.main.activity_investor_detail.icLoading
import kotlinx.android.synthetic.main.activity_investor_detail.iv_person
import kotlinx.android.synthetic.main.activity_investor_detail.tvAddress
import kotlinx.android.synthetic.main.activity_investor_detail.tvCompany
import kotlinx.android.synthetic.main.activity_investor_detail.tvName
import kotlinx.android.synthetic.main.activity_investor_detail.tvSend
import kotlinx.android.synthetic.main.activity_project_detail.*
import kotlinx.android.synthetic.main.ic_title.*

/**
 *   author ： xiaogf
 *   time    ： 2022/10/13
 *   describe    ：
 */
class InvestorDetailActivity :BaseMvpActivity<InvestorDetailPersenter>(),InvestorDetailContract.View{
    var capitalist: Capitalist? =null
    var isFriend=false;//当前用户是否为好友
    override fun getActivityLayoutId(): Int = R.layout.activity_investor_detail

    override fun initData() {
        tvTilte.text="详情"
        val caId = intent.getIntExtra("capitalist_id", 0)
        val user_id = intent.getIntExtra("user_id", 0)
        getPresenter().getInverstorDetail(Constants.getToken(),caId)
        Thread(Runnable {
            isFriend=EMClient.getInstance().contactManager().allContactsFromServer.contains("$user_id")
        }).start()

    }

    override fun onResume() {
        super.onResume()
        getPresenter().getUserQuotaNum()//获取余额
        getPresenter().getGreetingList()
    }

    override fun initEvent() {
        tvSend.setOnClickListener {
            capitalist?.let { it1 ->
                Thread(Runnable {
                    var userMessage = UserMessage()
                    var inverstor=""
                    var address=""
                    var trade=""
                    for (i in 0 until it1.attr_list.size){
                        if (it1.attr_list[i].attr_parent_id==3){
                            inverstor+=it1.attr_list[i].attr_name+"  "
                        }else if (it1.attr_list[i].attr_parent_id==2){
                            address+=it1.attr_list[i].attr_name+"  "
                        }else if (it1.attr_list[i].attr_parent_id==1){
                            trade+=it1.attr_list[i].attr_name+"  "
                        }
                    }
                    userMessage.id=it1.user_id
                    userMessage.financing=inverstor
                    userMessage.address=address
                    userMessage.name=it1.capitalist_name
                    userMessage.position=it1.contact_name
                    userMessage.describe=it1.introduction
                    userMessage.logo=it1.avatar
                    userMessage.trade=trade
                    EaseCommom.getInstance().userMessage=userMessage
                }).start()
                ChatActivity.actionStart(this,"${it1.user_id}", EaseConstant.CHATTYPE_SINGLE,
                    it1.capitalist_name)
            }
        }

    }

    override fun createPresenter(): InvestorDetailPersenter = InvestorDetailPersenter(this)
    override fun getInverstorDetail(capitalist: Capitalist) {
        this.capitalist=capitalist
        Thread(Runnable {
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
            userMessage.position=capitalist.position
            userMessage.describe=capitalist.introduction
            userMessage.logo=capitalist.avatar
            userMessage.trade=trade
            var user = User(capitalist.user_id, capitalist.avatar, capitalist.capitalist_name)
            user.userMessage=userMessage
            Constants.putUserDetail(user)
        }).start()
        icLoading.visibility=View.GONE
        var inverstor=""
        var address=""
        var datas:ArrayList<Capitalist.AttrListBean> =ArrayList()
        for (i in 0 until capitalist.attr_list.size){
            if (capitalist.attr_list[i].attr_parent_id==3){
                inverstor+=capitalist.attr_list[i].attr_name+"  "
            }else if (capitalist.attr_list[i].attr_parent_id==2){
                address+=capitalist.attr_list[i].attr_name+"  "
            }else if (capitalist.attr_list[i].attr_parent_id==1){
                datas.add(capitalist.attr_list[i])
            }
        }

        tvName.text=capitalist.capitalist_name
        tvCompany.text=capitalist.contact_name+"|"+capitalist.position
        tvOrgan.text=capitalist.introduction
        rcType.visibility= View.VISIBLE
        var layoutManager= LinearLayoutManager(this)
        layoutManager.orientation= LinearLayoutManager.HORIZONTAL
        rcType.layoutManager=layoutManager
        var adapter= HomeTypeAdapter()
        rcType.adapter=adapter
        adapter.data=datas
        tvCase.text="投资案例:"+capitalist.cases
        tvMoeny.text=capitalist.single_amount
        tvInverstor.text=inverstor
        tvAddress.text=address
        iv_person.loadImag(capitalist.avatar)
        setSendState()
    }

    var userQuotaNum: UserQuotaNum? =null
    override fun getUserQuotaNum(userQuotaNum: UserQuotaNum) {
        this.userQuotaNum=userQuotaNum
        setSendState()
    }
    var callIm: CallIm? =null
    override fun getGreetingList(callIm: CallIm) {
        this.callIm=callIm
        setSendState()
    }
    private fun setSendState(){//设置沟通按钮的状态
        if (userQuotaNum==null||callIm==null||capitalist==null) return
        if (userQuotaNum!!.quota_num==0){
            tvSend.isClickable=false
            tvSend.setBackgroundResource(R.drawable.false_click)
            tvSend.text="打个招呼聊聊天(额度余${userQuotaNum!!.quota_num})"
        }else{
            tvSend.isClickable=true
            tvSend.setBackgroundResource(R.drawable.sel_login)
            if (isFriend){
                tvSend.text="继续沟通(额度余${userQuotaNum!!.quota_num})"
            }else {
                var isHas=false
                Thread(Runnable {
                    callIm!!.receive.forEachIndexed { index, activeBean ->
                        if (activeBean.user_id== capitalist!!.user_id){
                            isHas=true
                            return@forEachIndexed
                        }
                    }
                    ThreadUtils.runOnUiThread(Runnable {
                        if (isHas){
                            tvSend.text="继续沟通(额度余${userQuotaNum!!.quota_num})"
                        }else{
                            tvSend.text="打个招呼聊聊天(额度余${userQuotaNum!!.quota_num})"
                        }
                    })
                }).start()

            }



        }
    }

}