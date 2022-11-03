package com.example.xck.ui.person.activity

import android.view.View
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.CodeImage
import com.example.xck.bean.VerifyPhone
import com.example.xck.ui.person.mvp.contract.ModifyPasswordContract
import com.example.xck.ui.person.mvp.persenter.ModifyPasswordPersenter
import kotlinx.android.synthetic.main.activity_modify_password.*

class ModifyPasswordActivity : BaseMvpActivity<ModifyPasswordPersenter>(),ModifyPasswordContract.View {
    override fun getActivityLayoutId(): Int =R.layout.activity_modify_password


    override fun initData() {
    }

    override fun initEvent() {
        tvRegister.setOnClickListener {
            llPhone.visibility=View.GONE
            llPassword.visibility=View.VISIBLE
            tvCodeRz.setTextColor(resources.getColor(R.color.text_333333))
            tvNewPassdword.setTextColor(resources.getColor(R.color.text_FF6C2EA5))
        }
    }

    override fun createPresenter(): ModifyPasswordPersenter = ModifyPasswordPersenter(this)
    override fun getCodeImage(image: CodeImage) {
        TODO("Not yet implemented")
    }

    override fun getCode() {
        TODO("Not yet implemented")
    }

    override fun verifyPhone(verifyPhone: VerifyPhone) {
        TODO("Not yet implemented")
    }
}