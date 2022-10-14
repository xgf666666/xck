package com.example.xck.ui.person.activity

import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.ui.person.mvp.contract.InverstorMessageEditContract
import com.example.xck.ui.person.mvp.persenter.InverstorMessageEditPersenter
import kotlinx.android.synthetic.main.ic_title.*

class InvestorMessageEditActivity : BaseMvpActivity<InverstorMessageEditPersenter>(),InverstorMessageEditContract.View {
    override fun getActivityLayoutId(): Int =R.layout.activity_investor_message_edit


    override fun initData() {
        tvTilte.text="投资人信息完善"
    }

    override fun initEvent() {
    }

    override fun createPresenter(): InverstorMessageEditPersenter = InverstorMessageEditPersenter(this)

}