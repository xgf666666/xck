package com.example.xck.ui.person.activity

import android.content.Intent
import com.example.xck.App
import com.example.xck.R
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity
import kotlinx.android.synthetic.main.activity_prepare_login.*

/**
 *   author ： xiaogf
 *   time    ： 2022/10/10
 */
class PrepareLoginActivity :BaseMvpViewActivity(){
    override fun getActivityLayoutId(): Int = R.layout.activity_prepare_login

    override fun initData() {
        (application as App).addActivity(this)
    }

    override fun initEvent() {
        tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            this?.startActivity(intent)
        }
        tvRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).deleteActivity(this)
    }
}