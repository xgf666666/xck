package com.example.xck.base.mvp

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.example.xck.R


/**
 * BaseMvpViewFragment
 * (。・∀・)ノ
 * Describe：
 * Created by 雷小星🍀 on 2017/11/2 18:22.
 */
abstract class BaseMvpViewFragment<V : ViewBinding> : BaseFragment<V>() {






    /**
     * 显示Toast信息
     *
     * @param msg 消息信息
     */

    fun showToast(msg: String?) {
//        if (TextUtils.isEmpty(msg)) {
//            return
//        }
//        ToastUtils.setBgColor(ContextCompat.getColor(context ?: return, android.R.color.black))
//        ToastUtils.setMsgColor(ContextCompat.getColor(context ?: return, android.R.color.white))
//        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
//        ToastUtils.showShort(msg)
    }

    private var alertDialog: AlertDialog? = null
    /**
     * 显示加载进度框
     */
    fun showLoadingDialog() {
        if (alertDialog == null) {
            alertDialog = AlertDialog
                    .Builder(context ?: return)
                    .setView(R.layout.dialog_progress)
                    .create()
            alertDialog!!.setCanceledOnTouchOutside(false)
            if (alertDialog!!.window != null) {
                alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable())
            }
        }
        requireActivity().runOnUiThread {
            alertDialog!!.show()
        }
    }

    /**
     * 隐藏加载进度框
     */
    fun dismissLoadingDialog() {
        if (alertDialog != null) {
            requireActivity().runOnUiThread {
                alertDialog!!.dismiss()
            }
        }
    }

}