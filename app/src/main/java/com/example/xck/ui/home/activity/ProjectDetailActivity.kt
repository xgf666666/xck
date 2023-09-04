package com.example.xck.ui.home.activity

import android.view.View
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.CallIm
import com.example.xck.bean.Project
import com.example.xck.bean.User
import com.example.xck.bean.UserQuotaNum
import com.example.xck.common.Constants
import com.example.xck.databinding.ActivityInvestorDetailBinding
import com.example.xck.databinding.ActivityProjectDetailBinding
import com.example.xck.ui.home.mvp.contract.ProjectDetailContract
import com.example.xck.ui.home.mvp.persenter.ProjectDetailPersenter
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
class ProjectDetailActivity :BaseMvpActivity<ProjectDetailPersenter>(),ProjectDetailContract.View{
    var project: Project? =null
    var isFriend=false
    var user_id=0

    override fun initData() {
        mBinding.icTitle.tvTilte.text="详情"
        val projectId = intent.getIntExtra("project_id", 0)
         user_id = intent.getIntExtra("user_id", 0)
        if (Constants.getPersonal().user_type_select==0){
            mBinding.tvSend.text = "先完善角色信息再打招呼"
        }
        getPresenter().getProjectDetail(Constants.getToken(),projectId,0)
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
            project?.let { it1 ->
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
                    userMessage.name=it1.project_name
                    userMessage.position=it1.user_info.position
                    userMessage.describe=it1.introduction
                    userMessage.logo=it1.logo_image
                    userMessage.trade=trade
                    userMessage.userName=it1.user_info.real_name
                    EaseCommom.getInstance().userMessage=userMessage
                }).start()
                ChatActivity.actionStart(this,"${it1.user_id}", EaseConstant.CHATTYPE_SINGLE,
                    it1.project_name)
            }
        }
    }



    override fun createPresenter(): ProjectDetailPersenter = ProjectDetailPersenter(this)
    private val mBinding by lazy { ActivityProjectDetailBinding.inflate(layoutInflater) }
    override fun getViewBinding(): ViewBinding {
        return mBinding
    }

    override fun getProjectDetail(project: Project) {
        this.project=project
        Thread(Runnable {
            var userMessage = UserMessage()
            var inverstor=""
            var address=""
            var trade=""
            for (i in 0 until project.attr_list.size){
                if (project.attr_list[i].attr_parent_id==3){
                    inverstor+=project.attr_list[i].attr_name+"  "
                }else if (project.attr_list[i].attr_parent_id==2){
                    address+=project.attr_list[i].attr_name+"  "
                }else if (project.attr_list[i].attr_parent_id==1){
                    trade+=project.attr_list[i].attr_name+"  "
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
            userMessage.position=project.user_info.position
            userMessage.userName=project.user_info.real_name
            var user = User(project.user_id, project.logo_image, project.project_name)
            user.userMessage=userMessage
            Constants.putUserDetail(user)
        }).start()//存储信息给打招呼和沟通中使用

        mBinding.icLoading.root.visibility=View.GONE
        mBinding.tvName.text=project.project_name
        var type=""
        var address=""
        for (i in 0 until project.attr_list.size){
            if (i==0){
                type += project.attr_list[i].attr_name
            }else{
                type += "|"+project.attr_list[i].attr_name
            }
            if (project.attr_list[i].attr_parent_id==2){
                address=project.attr_list[i].attr_name
            }
        }
        mBinding.tvCompany.text=type
        mBinding.tvAddress.text=address
        mBinding.tvTime.text=project.create_time
        mBinding.tvProjectI.text=project.introduction
        mBinding.tvRongzi.text=project.wait_finance
        mBinding.tvOperateData.text=project.operation
        mBinding.tvProjectAdvantage.text=project.advantage
        mBinding.tvHistoryFinance.text=project.history_financice
        mBinding.tvBusinessBook.text=project.project_file
        mBinding.tvTeamNum.text=project.team_member
        mBinding.ivPerson.loadImag(project.logo_image)
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
                mBinding. tvSend.text="打个招呼聊聊天(额度余${userQuotaNum!!.quota_num})"
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