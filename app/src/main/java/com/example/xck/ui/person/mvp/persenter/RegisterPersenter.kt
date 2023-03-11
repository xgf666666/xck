package com.example.xck.ui.person.mvp.persenter

import android.text.TextUtils
import com.example.xck.common.Constants
import com.example.xck.common.isPhone
import com.example.xck.extensions.ui
import com.example.xck.ui.person.mvp.contract.RegisterContract
import com.example.xck.ui.person.mvp.model.RegisterModel

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
        this.mobile_phone=mobile_phone
        this.password=password
       getView()?.showLoadingDialog()
       getModel().register(mobile_phone,vercode,smscode, key, password, repassword).ui({
           getView()?.register(it.data!!)
           registerIM(it.data!!.access_token)


       },{
           getView()?.dismissLoadingDialog()
           getView()?.showToast(it.message)
       })
    }

    override fun login(mobile_phone: String, password: String) {
        getModel().login(mobile_phone, password).ui({
            getView()?.dismissLoadingDialog()
            getView()?.login(it.data!!)
        },{
            getView()?.showToast(it.message)
            getView()?.dismissLoadingDialog()
        })
    }
    private var mobile_phone=""
    private var password=""
    override fun registerIM(authorization: String) {
        getModel().registerIM(authorization).ui({
            Constants.putIMToken(it.data!!.access_token)
            getView()?.registerIM()
            login(mobile_phone,password)
        },{
            getView()?.showToast(it.message)
        })
    }

    override fun createModel(): RegisterContract.Model =RegisterModel()
}