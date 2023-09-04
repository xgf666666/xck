package com.example.xck.ui.person.activity


import android.content.Intent
import androidx.viewbinding.ViewBinding
import com.example.xck.App
import com.example.xck.databinding.ActivityPrepareRoleIdentifyBinding
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity

class PrepareRoleIdentifyActivity : BaseMvpViewActivity() {
    private val mBinding by lazy { ActivityPrepareRoleIdentifyBinding.inflate(layoutInflater) }
    override fun getViewBinding(): ViewBinding {
        return mBinding
    }

    override fun initData() {
    }

    override fun initEvent() {
        (application as App).addActivity(this)
        mBinding.cbSelect.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                if (mBinding.cbSelectTzr.isChecked) mBinding.cbSelectTzr.isChecked=false
                var intent=Intent(this,RoleIdentifyActivity::class.java)
                intent.putExtra("user_type_select",1)
                startActivity(intent)
            }
        }
        mBinding.cbSelectTzr.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                if (mBinding.cbSelect.isChecked) mBinding.cbSelect.isChecked=false
                var intent=Intent(this,RoleIdentifyActivity::class.java)
                intent.putExtra("user_type_select",2)
                startActivity(intent)
            }
        }
    }
}