package com.example.xck.ui.home.activity

import android.view.View
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.Project
import com.example.xck.bean.UserQuotaNum
import com.example.xck.common.Constants
import com.example.xck.ui.home.mvp.contract.ProjectDetailContract
import com.example.xck.ui.home.mvp.persenter.ProjectDetailPersenter
import kotlinx.android.synthetic.main.activity_investor_detail.*
import kotlinx.android.synthetic.main.activity_project_detail.*
import kotlinx.android.synthetic.main.activity_project_detail.icLoading
import kotlinx.android.synthetic.main.activity_project_detail.tvAddress
import kotlinx.android.synthetic.main.activity_project_detail.tvCompany
import kotlinx.android.synthetic.main.activity_project_detail.tvName
import kotlinx.android.synthetic.main.activity_project_detail.tvSend
import kotlinx.android.synthetic.main.ic_title.*

/**
 *   author ： xiaogf
 *   time    ： 2022/10/13
 *   describe    ：
 */
class ProjectDetailActivity :BaseMvpActivity<ProjectDetailPersenter>(),ProjectDetailContract.View{
    override fun getActivityLayoutId(): Int = R.layout.activity_project_detail

    override fun initData() {
        tvTilte.text="详情"
        getPresenter().getProjectDetail(Constants.getToken(),intent.getIntExtra("project_id",0),0)
        getPresenter().getUserQuotaNum()
    }

    override fun initEvent() {
    }

    override fun createPresenter(): ProjectDetailPersenter = ProjectDetailPersenter(this)
    override fun getProjectDetail(project: Project) {
        icLoading.visibility=View.GONE
        tvName.text=project.project_name
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
        tvCompany.text=type
        tvAddress.text=address
        tvTime.text=project.create_time
        tv_ProjectI.text=project.introduction
        tvRongzi.text=project.wait_finance
        tvOperateData.text=project.operation
        tvProjectAdvantage.text=project.advantage
        tvHistoryFinance.text=project.history_financice
        tvBusinessBook.text=project.project_file
        tvTeamNum.text=project.team_member
    }

    override fun getUserQuotaNum(userQuotaNum: UserQuotaNum) {
        tvSend.text="打个招呼聊聊天(额度余${userQuotaNum.quota_num})"
    }

}