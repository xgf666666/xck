package com.example.xck.ui.home.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.CallIm
import com.example.xck.bean.Capitalist
import com.example.xck.bean.User
import com.example.xck.bean.UserQuotaNum
import com.example.xck.common.Constants
import com.example.xck.databinding.ActivityInvestorDetailBinding
import com.example.xck.ui.home.adapter.HomeTypeAdapter
import com.example.xck.ui.home.mvp.contract.InvestorDetailContract
import com.example.xck.ui.home.mvp.persenter.InvestorDetailPersenter
import com.example.xck.ui.person.activity.ChatActivity
import com.example.xck.utils.loadImag
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.constants.EaseCommom
import com.hyphenate.easeui.constants.EaseConstant
import com.hyphenate.easeui.constants.UserMessage


/**
 *   author ： xiaogf
 *   time    ： 2022/10/13
 *   describe    ：
 */
class InvestorDetailActivity :BaseMvpActivity<InvestorDetailPersenter>(),InvestorDetailContract.View{
    var capitalist: Capitalist? =null
    var isFriend=false;//当前用户是否为好友
    var user_id=0
    private val mBinding by lazy { ActivityInvestorDetailBinding.inflate(layoutInflater) }

    override fun initData() {
        mBinding.icTitle.tvTilte.text="详情"
        val caId = intent.getIntExtra("capitalist_id", 0)
         user_id = intent.getIntExtra("user_id", 0)
        if (Constants.getPersonal().user_type_select==0){
            mBinding.tvSend.text = "先完善角色信息再打招呼"
        }
        getPresenter().getInverstorDetail(Constants.getToken(),caId)

    }

    override fun onResume() {
        super.onResume()
        if (Constants.getPersonal().user_type_select!=0){
            Thread(Runnable {
                isFriend= EMClient.getInstance().contactManager().allContactsFromServer.contains("$user_id")
                if (!isFriend) {
                    getPresenter().getGreetingList()//请求打招呼
                }
                getPresenter().getUserQuotaNum()//获取余额
            }).start()
        }

    }

    override fun initEvent() {
        mBinding.tvSend.setOnClickListener {
            if (Constants.getPersonal().user_type_select==0){
                ToastUtils.showShort("先完善角色信息再打招呼")
                return@setOnClickListener
            }
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
                    userMessage.position=it1.position
                    userMessage.userName=it1.contact_name
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
    override fun getViewBinding(): ViewBinding {
        return mBinding
    }

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
            userMessage.userName=capitalist.contact_name
            userMessage.trade=trade
            var user = User(capitalist.user_id, capitalist.avatar, capitalist.capitalist_name)
            user.userMessage=userMessage
            Constants.putUserDetail(user)
        }).start()
        mBinding.icLoading.root.visibility=View.GONE
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

        mBinding.tvName.text=capitalist.capitalist_name
        mBinding.tvCompany.text=capitalist.contact_name+"|"+capitalist.position
        mBinding.tvOrgan.text=capitalist.introduction
        mBinding.rcType.visibility= View.VISIBLE
        var layoutManager= LinearLayoutManager(this)
        layoutManager.orientation= LinearLayoutManager.HORIZONTAL
        mBinding.rcType.layoutManager=layoutManager
        var adapter= HomeTypeAdapter()
        mBinding.rcType.adapter=adapter
        adapter.data=datas
        mBinding.tvCase.text="投资案例:"+capitalist.cases
        mBinding.tvMoeny.text=capitalist.single_amount
        mBinding.tvInverstor.text=inverstor
        mBinding.tvAddress.text=address
        mBinding.ivPerson.loadImag(capitalist.avatar)
//        setSendState()
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
        if (!isFriend){
            if (userQuotaNum==null||callIm==null) return
        }
        if (isFriend){
            mBinding.tvSend.isClickable=true
            mBinding.tvSend.text="继续沟通(额度余${userQuotaNum!!.quota_num})"
        }else{
            if (userQuotaNum!!.quota_num==0){
                mBinding.tvSend.isClickable=false
                mBinding.tvSend.setBackgroundResource(R.drawable.false_click)
                mBinding.tvSend.text="打个招呼聊聊天(额度余${userQuotaNum!!.quota_num})"
            }else{
                mBinding.tvSend.isClickable=true
                mBinding.tvSend.setBackgroundResource(R.drawable.sel_login)
                    var isHas=false
                    Thread(Runnable {
                        callIm!!.receive.forEachIndexed { index, activeBean ->
                            if (activeBean.user_id== user_id){
                                isHas=true
                                return@forEachIndexed
                            }
                        }
                        ThreadUtils.runOnUiThread(Runnable {
                            if (isHas){
                                mBinding.tvSend.text="继续沟通(额度余${userQuotaNum!!.quota_num})"
                            }else{
                                mBinding.tvSend.text="打个招呼聊聊天(额度余${userQuotaNum!!.quota_num})"
                            }
                        })
                    }).start()
            }

        }
    }

}