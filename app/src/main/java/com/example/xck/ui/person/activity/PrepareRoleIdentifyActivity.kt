package com.example.xck.ui.person.activity


import android.content.Intent
import com.example.xck.App
import com.example.xck.R
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity
import kotlinx.android.synthetic.main.activity_prepare_role_identify.*

class PrepareRoleIdentifyActivity : BaseMvpViewActivity() {
    override fun getActivityLayoutId(): Int= R.layout.activity_prepare_role_identify

    override fun initData() {
    }

    override fun initEvent() {
        (application as App).addActivity(this)
        cb_select.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                var intent=Intent(this,RoleIdentifyActivity::class.java)
                intent.putExtra("user_type_select",1)
                startActivity(intent)
            }
        }
        cbSelectTzr.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                var intent=Intent(this,RoleIdentifyActivity::class.java)
                intent.putExtra("user_type_select",2)
                startActivity(intent)
            }
        }
    }
}