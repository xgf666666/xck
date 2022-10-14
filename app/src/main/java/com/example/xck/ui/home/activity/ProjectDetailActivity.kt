package com.example.xck.ui.home.activity

import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.ui.home.mvp.contract.ProjectDetailContract
import com.example.xck.ui.home.mvp.persenter.ProjectDetailPersenter
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
    }

    override fun initEvent() {
    }

    override fun createPresenter(): ProjectDetailPersenter = ProjectDetailPersenter(this)

}