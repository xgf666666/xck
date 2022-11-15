package com.example.xck.ui.person.activity

import com.example.xck.App
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.common.Constants
import com.example.xck.ui.person.mvp.contract.RoleIdentifyContract
import com.example.xck.ui.person.mvp.persenter.RoleIdentifyPersenter
import kotlinx.android.synthetic.main.activity_role_identify.*
import kotlinx.android.synthetic.main.ic_title.*

class RoleIdentifyActivity : BaseMvpActivity<RoleIdentifyPersenter>(),RoleIdentifyContract.View {
    override fun getActivityLayoutId(): Int =R.layout.activity_role_identify;

    override fun initData() {
        (application as App).addActivity(this)
        tvTilte.text="角色认证"
        tvPhone.text=Constants.getPersonal().mobile_phone

    }

    override fun initEvent() {
        tvSend.setOnClickListener {
            getPresenter().roleIdentify(Constants.getToken(),intent.getIntExtra("user_type_select",0),etName.text.toString(),etWx.text.toString())
        }
    }

    override fun createPresenter(): RoleIdentifyPersenter = RoleIdentifyPersenter(this)
    override fun roleIdentify() {
        (application as App).cleanActivity()
    }


}