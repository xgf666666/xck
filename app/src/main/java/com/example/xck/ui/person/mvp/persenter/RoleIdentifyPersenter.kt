package com.example.xck.ui.person.mvp.persenter

import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.xck.extensions.ui
import com.example.xck.ui.person.mvp.contract.RoleIdentifyContract
import com.example.xck.ui.person.mvp.model.RoleIdentifyModel

class RoleIdentifyPersenter(view: RoleIdentifyContract.View):RoleIdentifyContract.Persenter(view) {
    override fun roleIdentify(
        Authorization: String,
        user_type_select: Int,
        real_name: String,
        wechat: String
    ) {
        if (StringUtils.isEmpty(real_name)){
            ToastUtils.showShort("请输入真实姓名")
            return
        }
        if (StringUtils.isEmpty(wechat)){
            ToastUtils.showShort("请输入微信")
            return
        }
        getView()?.showLoadingDialog()
        getModel().roleIdentify(Authorization, user_type_select, real_name, wechat).ui({
            getView()?.dismissLoadingDialog()
            getView()?.showToast("提交成功，等待审核")
            getView()?.roleIdentify()
        },{
            getView()?.showToast(it.message)
            getView()?.dismissLoadingDialog()
        })
    }

    override fun createModel(): RoleIdentifyContract.Model =RoleIdentifyModel()
}