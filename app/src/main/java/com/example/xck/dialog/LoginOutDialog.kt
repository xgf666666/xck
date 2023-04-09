package com.example.xck.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.example.xck.R
import com.example.xck.bean.Login
import com.example.xck.common.Constants
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.dialog_login_out.*

/**
 *   author ： xiaogf
 *   time    ： 2023/4/9
 *   describe    ：
 */
class LoginOutDialog(context:Context):Dialog(context) {
    private lateinit var loginOutClickListener:LoginOutClickListener
    init {
        setContentView(R.layout.dialog_login_out)
        var lp=window!!.attributes
        lp.width= WindowManager.LayoutParams.WRAP_CONTENT
        lp.height= WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes=lp
        initView()
    }

    private fun initView() {
        tvExit.setOnClickListener {
            dismiss()
        }
        tvSure.setOnClickListener {
            loginOutClickListener?.let {
                it.sure()
                dismiss()
            }
        }
    }
    public fun setLoginOutClickListener(loginOutClickListener:LoginOutClickListener ){
        this.loginOutClickListener=loginOutClickListener;
    }
    interface LoginOutClickListener{
        fun sure()
    }
}