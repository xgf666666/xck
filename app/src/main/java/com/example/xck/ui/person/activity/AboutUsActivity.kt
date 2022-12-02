package com.example.xck.ui.person.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.bean.Doc
import com.example.xck.ui.person.mvp.contract.AboutUsContract
import com.example.xck.ui.person.mvp.persenter.AboutUsPresenter
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.ic_title.*

class AboutUsActivity : BaseMvpActivity<AboutUsPresenter>(),AboutUsContract.View {
    override fun getActivityLayoutId(): Int =R.layout.activity_about_us


    override fun initData() {
        tvTilte.text="关于我们"
        getPresenter().getDoc("about_us")
    }

    override fun initEvent() {

    }

    override fun createPresenter(): AboutUsPresenter = AboutUsPresenter(this)

    override fun getDoc(doc: Doc) {
        tvAboutUs.text=doc.doc

    }
}