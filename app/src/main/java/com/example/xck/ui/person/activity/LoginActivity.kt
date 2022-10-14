package com.example.xck.ui.person.activity

import android.content.Intent
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.ui.person.mvp.contract.LoginContract
import com.example.xck.ui.person.mvp.persenter.LoginPersenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseMvpActivity<LoginPersenter>(),LoginContract.View {



    override fun createPresenter(): LoginPersenter =LoginPersenter(this)
    override fun getActivityLayoutId(): Int = R.layout.activity_login

    override fun initData() {

    }

    override fun initEvent() {
        tvRegister.setOnClickListener {
            var intent=Intent(this,RegisterActivity::class.java)
            this.startActivity(intent)
        }
        tvForgetPw.setOnClickListener {
            var intent=Intent(this,ModifyPasswordActivity::class.java)
            this.startActivity(intent)
        }
    }

}