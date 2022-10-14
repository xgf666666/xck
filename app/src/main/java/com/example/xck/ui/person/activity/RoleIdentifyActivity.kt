package com.example.xck.ui.person.activity

import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.ui.person.mvp.contract.RoleIdentifyContract
import com.example.xck.ui.person.mvp.persenter.RoleIdentifyPersenter
import kotlinx.android.synthetic.main.ic_title.*

class RoleIdentifyActivity : BaseMvpActivity<RoleIdentifyPersenter>(),RoleIdentifyContract.View {
    override fun getActivityLayoutId(): Int =R.layout.activity_role_identify;

    override fun initData() {
        tvTilte.text="角色认证"
    }

    override fun initEvent() {
    }

    override fun createPresenter(): RoleIdentifyPersenter = RoleIdentifyPersenter(this)


}