package com.example.xck.ui.person.activity

import androidx.viewbinding.ViewBinding
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.Doc
import com.example.xck.databinding.ActivityAboutUsBinding
import com.example.xck.ui.person.mvp.contract.AboutUsContract
import com.example.xck.ui.person.mvp.persenter.AboutUsPresenter

class ServiceActivity : BaseMvpActivity<AboutUsPresenter>(),AboutUsContract.View {
    private val mBinding by lazy { ActivityAboutUsBinding.inflate(layoutInflater) }

    override fun initData() {
        mBinding.icTitle.tvTilte.text="隐私政策"
        getPresenter().getDoc("service_agreement")
    }

    override fun initEvent() {

    }

    override fun createPresenter(): AboutUsPresenter = AboutUsPresenter(this)
    override fun getViewBinding(): ViewBinding {
        return mBinding
    }

    override fun getDoc(doc: Doc) {
        mBinding.tvAboutUs.text=doc.doc

    }
}