package com.example.xck.ui.person.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.ui.person.mvp.contract.ProjectMessageEditContract
import com.example.xck.ui.person.mvp.persenter.ProjectMessageEditPersenter
import kotlinx.android.synthetic.main.ic_title.*

class ProjectMessageEditActivity : BaseMvpActivity<ProjectMessageEditPersenter>(),ProjectMessageEditContract.View {
    override fun getActivityLayoutId(): Int =R.layout.activity_project_message_edit


    override fun initData() {
        tvTilte.text="创业项目信息完善"
    }

    override fun initEvent() {
    }

    override fun createPresenter(): ProjectMessageEditPersenter =ProjectMessageEditPersenter(this)

}