package com.example.xck.dialog

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import com.example.xck.databinding.DialogLoginOutBinding


/**
 *   author ： xiaogf
 *   time    ： 2023/4/9
 *   describe    ：
 */
class LoginOutDialog(context:Context):Dialog(context) {
    private lateinit var loginOutClickListener:LoginOutClickListener
    private val mBinding by lazy { DialogLoginOutBinding.inflate(layoutInflater) }
    init {
        setContentView(mBinding.root)
        var lp=window!!.attributes
        lp.width= WindowManager.LayoutParams.WRAP_CONTENT
        lp.height= WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes=lp
        initView()
    }

    private fun initView() {
        mBinding.tvExit.setOnClickListener {
            dismiss()
        }
        mBinding.tvSure.setOnClickListener {
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