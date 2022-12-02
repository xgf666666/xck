package com.example.xck.ui.person.activity

import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.Doc
import com.example.xck.ui.person.mvp.contract.AboutUsContract
import com.example.xck.ui.person.mvp.persenter.AboutUsPresenter
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.ic_title.*

class ServiceActivity : BaseMvpActivity<AboutUsPresenter>(),AboutUsContract.View {
    override fun getActivityLayoutId(): Int =R.layout.activity_about_us


    override fun initData() {
        tvTilte.text="隐私政策"
        getPresenter().getDoc("service_agreement")
    }

    override fun initEvent() {

    }

    override fun createPresenter(): AboutUsPresenter = AboutUsPresenter(this)

    override fun getDoc(doc: Doc) {
        tvAboutUs.text=doc.doc

    }
}