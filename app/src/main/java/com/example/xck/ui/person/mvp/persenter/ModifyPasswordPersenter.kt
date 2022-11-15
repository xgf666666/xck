package com.example.xck.ui.person.mvp.persenter

import android.text.TextUtils
import com.example.xck.common.Constants
import com.example.xck.common.isPhone
import com.example.xck.extensions.ui
import com.example.xck.ui.person.mvp.contract.ModifyPasswordContract
import com.example.xck.ui.person.mvp.model.ModifyPasswordModel

class ModifyPasswordPersenter(view: ModifyPasswordContract.View):ModifyPasswordContract.Persenter(view) {
    override fun getCodeImage() {
        getModel().getCodeImage().ui({
            getView()?.getCodeImage(it.data!!)
        },{
            getView()?.showToast(it.message)
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

    override fun verifyPhone(
        Authorization: String,
        type: String,
        mobile_phone: String,
        vercode: String,
        key: String,
        smscode: String
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
        getView()?.showLoadingDialog()
        if (Constants.isLogin()){
            getModel().verifyPhone(Authorization, type, mobile_phone, vercode, key, smscode).ui({
                getView()?.dismissLoadingDialog()
                getView()?.verifyPhone(it.data!!)
            },{
                getView()?.dismissLoadingDialog()
                getView()?.showToast(it.message)
            })
        }else{
            getModel().verifyPhone(type, mobile_phone, vercode, key, smscode).ui({
                getView()?.dismissLoadingDialog()
                getView()?.verifyPhone(it.data!!)
            },{
                getView()?.dismissLoadingDialog()
                getView()?.showToast(it.message)
            })
        }
    }

    override fun modify(
        Authorization: String,
        password: String,
        repassword: String,
        verify_token: String
    ) {
        if (TextUtils.isEmpty(repassword)){
            getView()?.showToast("请输入确认密码")
            return
        }
        if (TextUtils.isEmpty(password)||password.length<6||password.length>16){
            getView()?.showToast("请输入6到16位密码")
            return
        }
        if (password!=repassword){
            getView()?.showToast("确认密码与密码不一致")
            return

        }
        getView()?.showLoadingDialog()
        getModel().modify(Authorization, password, repassword, verify_token).ui({
            getView()?.dismissLoadingDialog()
            getView()?.modify()

        },{
            getView()?.dismissLoadingDialog()
            getView()?.showToast(it.message)
        })
    }


    override fun createModel(): ModifyPasswordContract.Model =ModifyPasswordModel()
}