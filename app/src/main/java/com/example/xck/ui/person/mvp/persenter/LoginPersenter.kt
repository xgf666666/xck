package com.example.xck.ui.person.mvp.persenter

import android.text.TextUtils
import android.util.Log
import com.example.xck.App
import com.example.xck.MainActivity
import com.example.xck.common.isPhone
import com.example.xck.extensions.ui
import com.example.xck.ui.person.activity.LoginActivity
import com.example.xck.ui.person.mvp.contract.LoginContract
import com.example.xck.ui.person.mvp.model.LoginModel
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient

class LoginPersenter(view: LoginContract.View):LoginContract.Persenter(view) {
    override fun login(mobile_phone: String, password: String) {
        if (TextUtils.isEmpty(mobile_phone)){
            getView()?.showToast("请输入手机号码")
            getView()?.dismissLoadingDialog()
            return
        }else if (!mobile_phone.isPhone()){
            getView()?.showToast("请输入正确的手机号码")
            getView()?.dismissLoadingDialog()
            return
        }
        getView()?.showLoadingDialog()
        getModel().login(mobile_phone, password).ui({
            var loginInfo=it.data!!
            it.data?.let { it1 -> getModel().getImUserToken(it1.access_token).ui({
                EMClient.getInstance().loginWithToken("${loginInfo.user_info.id}", it.data!!.access_token, object :
                    EMCallBack {
                    override fun onSuccess() {
                        getView()?.login(loginInfo)
                        getView()?.showToast("登录成功")
                        ((getView() as LoginActivity).application as App).cleanActivity()
                        getView()?.dismissLoadingDialog()
                    }

                    override fun onError(code: Int, error: String) {
                        getView()?.showToast(error)
                        Log.i("xgf",error)
                        getView()?.dismissLoadingDialog()
                    }
                })

            },{
                getView()?.showToast(it.message)
            }) }

        },{
            getView()?.dismissLoadingDialog()
            getView()?.showToast(it.message)
        })
    }

    override fun createModel(): LoginContract.Model =LoginModel()
}