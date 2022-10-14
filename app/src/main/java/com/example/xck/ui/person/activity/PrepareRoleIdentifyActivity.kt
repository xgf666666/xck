package com.example.xck.ui.person.activity


import android.content.Intent
import com.example.xck.R
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity
import kotlinx.android.synthetic.main.activity_prepare_role_identify.*

class PrepareRoleIdentifyActivity : BaseMvpViewActivity() {
    override fun getActivityLayoutId(): Int= R.layout.activity_prepare_role_identify

    override fun initData() {
    }

    override fun initEvent() {
        cb_select.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                var intent=Intent(this,RoleIdentifyActivity::class.java)
                startActivity(intent)
            }
        }
    }
}