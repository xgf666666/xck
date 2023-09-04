package com.example.xck.ui.person.activity

import androidx.viewbinding.ViewBinding
import com.example.xck.App
import com.example.xck.base.BaseMvpActivity
import com.example.xck.common.Constants
import com.example.xck.databinding.ActivityRoleIdentifyBinding
import com.example.xck.ui.person.mvp.contract.RoleIdentifyContract
import com.example.xck.ui.person.mvp.persenter.RoleIdentifyPersenter

class RoleIdentifyActivity : BaseMvpActivity<RoleIdentifyPersenter>(),RoleIdentifyContract.View {
    private val mBinding by lazy { ActivityRoleIdentifyBinding.inflate(layoutInflater) }
    override fun initData() {
        (application as App).addActivity(this)
        mBinding.icTitle.tvTilte.text="角色认证"
        mBinding.tvPhone.text=Constants.getPersonal().mobile_phone

    }

    override fun initEvent() {
        mBinding.tvSend.setOnClickListener {
            getPresenter().roleIdentify(Constants.getToken(),intent.getIntExtra("user_type_select",0),mBinding.etName.text.toString(),mBinding.etWx.text.toString(),mBinding.etPosition.text.toString())
        }
    }

    override fun createPresenter(): RoleIdentifyPersenter = RoleIdentifyPersenter(this)
    override fun getViewBinding(): ViewBinding {
        return mBinding
    }

    override fun roleIdentify() {
        (application as App).cleanActivity()
    }


}