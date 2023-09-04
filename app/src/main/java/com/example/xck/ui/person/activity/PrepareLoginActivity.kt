package com.example.xck.ui.person.activity

import android.content.Intent
import androidx.viewbinding.ViewBinding
import com.example.xck.App
import com.example.xck.databinding.ActivityPrepareLoginBinding
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity


/**
 *   author ： xiaogf
 *   time    ： 2022/10/10
 */
class PrepareLoginActivity :BaseMvpViewActivity(){

    private val mBinding by lazy { ActivityPrepareLoginBinding.inflate(layoutInflater) }
    override fun initData() {
        (application as App).addActivity(this)
    }

    override fun initEvent() {
        mBinding.tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            this?.startActivity(intent)
        }
        mBinding.tvRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).deleteActivity(this)
    }

    override fun getViewBinding(): ViewBinding {
        return mBinding
    }
}