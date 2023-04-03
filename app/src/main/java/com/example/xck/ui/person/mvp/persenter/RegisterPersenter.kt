package com.example.xck.ui.person.mvp.persenter

import android.text.TextUtils
import android.util.Log
import com.example.xck.App
import com.example.xck.bean.Login
import com.example.xck.common.Constants
import com.example.xck.common.isPhone
import com.example.xck.extensions.ui
import com.example.xck.ui.person.activity.LoginActivity
import com.example.xck.ui.person.mvp.contract.RegisterContract
import com.example.xck.ui.person.mvp.model.RegisterModel
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient

class RegisterPersenter(view: RegisterContract.View):RegisterContract.Persenter(view) {
    override fun getCodeImage() {
//        getView()?.showLoadingDialog()
        getModel().getCodeImage().ui({
            getView()?.dismissLoadingDialog()
            getView()?.getCodeImage(it.data!!)
        },{
            getView()?.showToast(it.message)
//            getView()?.dismissLoadingDialog()
        })

    }

    override fun getCode(type: String, mobile_phone: String) {
        if (TextUtils.isEmpty(mobile_phone)){
            getView()?.showToast("请输入手机号码")
            getView()?.dismissLoadingDialog()
            return
        }else if (!mobile_phone.isPhone()){
            getView()?.showToast("请输入正确的手机号码")
            getView()?.dismissLoadingDialog()
            return
        }
        getModel().getCode(type,mobile_phone).ui({
                    getView()?.getCode()
        },{
            getView()?.showToast(it.message)
        })
    }

    override fun register(
        mobile_phone: String,
        vercode: String,
        smscode: String,
        key: String,
        password: String,
        repassword: String
    ) {
        if (TextUtils.isEmpty(mobile_phone)){
            getView()?.showToast("请输入手机号")
            return
        }else if (!mobile_phone.isPhone()){
            getView()?.showToast("请输入正确的手机号")
            return
        }
        if (TextUtils.isEmpty(smscode)){
            getView()?.showToast("请输入验证码")
            return
        }else if (6!=smscode.length){
            getView()?.showToast("请输入6位验证码")
            return
        }
        if (TextUtils.isEmpty(vercode)){
            getView()?.showToast("请输入图形验证码")
            return
        }
        if (TextUtils.isEmpty(password)||password.length<6||password.length>16){
            getView()?.showToast("请输入6到16位密码")
            return
        }
        if (TextUtils.isEmpty(repassword)){
            getView()?.showToast("请输入确认密码")
            return
        }
        if (password!=repassword){
            getView()?.showToast("确认密码与密码不一致")
            return
        }
       getView()?.showLoadingDialog()
       getModel().register(mobile_phone,vercode,smscode, key, password, repassword).ui({
           register=it.data!!
           getView()?.register(it.data!!)
           registerIM(it.data!!.access_token)


       },{
           getView()?.dismissLoadingDialog()
           getView()?.showToast(it.message)
       })
    }
    var register: Login? =null
    override fun registerIM(authorization: String) {
        //注册IM
        getModel().registerIM(authorization).ui({
            //获取IMToken
            getModel().getImUserToken(register!!.access_token).ui({
                //登录IM
                EMClient.getInstance().loginWithToken("${register!!.user_info.id}", it.data!!.access_token, object :
                    EMCallBack {
                    override fun onSuccess() {
                        Constants.putIMToken(it.data!!.access_token)
                        register?.let { it1 -> getView()?.login(it1) }
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
            })
            getView()?.registerIM()
//            login(mobile_phone,password)
        },{
            getView()?.showToast(it.message)
            getView()?.dismissLoadingDialog()
        })
    }

    override fun createModel(): RegisterContract.Model =RegisterModel()
}