package com.example.xck.ui.person.activity


import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.ui.person.mvp.contract.RegisterContract
import com.example.xck.ui.person.mvp.persenter.RegisterPersenter

class RegisterActivity : BaseMvpActivity<RegisterPersenter>(),RegisterContract.View {
    override fun getActivityLayoutId(): Int =R.layout.activity_register


    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun createPresenter(): RegisterPersenter =RegisterPersenter(this)

}