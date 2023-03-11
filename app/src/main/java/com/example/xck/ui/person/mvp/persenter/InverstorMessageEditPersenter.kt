package com.example.xck.ui.person.mvp.persenter

import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.xck.extensions.ui
import com.example.xck.ui.person.mvp.contract.InverstorMessageEditContract
import com.example.xck.ui.person.mvp.model.InverstorMessageEditModel
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity
import io.reactivex.Observable
import java.io.File

class InverstorMessageEditPersenter(view: InverstorMessageEditContract.View):InverstorMessageEditContract.Persenter(view) {
    override fun upload(file: File) {
        getView()?.showLoadingDialog()
        getModel().upload(file).ui({
            ToastUtils.showShort(it.msg)
            getView()?.dismissLoadingDialog()
            getView()?.getloadFile(it.data!!)
//            getView()?.showToast("成功")
        },{
            getView()?.dismissLoadingDialog()
            getView()?.showToast(it.message)
        })
    }

    override fun setCapitalist(
        Authorization: String,
        capitalist_name: String,
        contact_name: String,
        position: String,
        single_amount: String,
        avatar: String,
        introduction: String,
        cases: String,
        business_card_img: String,
        industries: IntArray,
        stages: IntArray,
        location: IntArray,
        id:Int
    ) {
        if (StringUtils.isEmpty(capitalist_name)) {
            ToastUtils.showShort("请输入机构名称")
            return
        }
        if (StringUtils.isEmpty(contact_name)) {
            ToastUtils.showShort("请输入联系人")
            return
        }
        if (StringUtils.isEmpty(position)) {
            ToastUtils.showShort("请输入联系人职位")
            return
        }
        if (StringUtils.isEmpty(single_amount)) {
            ToastUtils.showShort("请输入额度")
            return
        }
        if (StringUtils.isEmpty(avatar)) {
            ToastUtils.showShort("投资人头像")
            return
        }
        if (StringUtils.isEmpty(introduction)) {
            ToastUtils.showShort("请输入机构介绍")
            return
        }
        if (StringUtils.isEmpty(cases)) {
            ToastUtils.showShort("请输入投资案例")
            return
        }
        if (business_card_img.isEmpty()) {
            ToastUtils.showShort("请上传联系人名片")
            return
        }
        if (industries.isEmpty()) {
            ToastUtils.showShort("请选择所在领域")
            return
        }
        if (stages.isEmpty()) {
            ToastUtils.showShort("请选择融资阶段")
            return
        }
        if (stages.isEmpty()) {
            ToastUtils.showShort("请选择项目地点")
            return
        }
        getView()?.showLoadingDialog()
        getModel().setCapitalist(Authorization, capitalist_name, contact_name, position, single_amount, avatar, introduction, cases, business_card_img, industries, stages, location,id).ui(
            {
                getView()?.dismissLoadingDialog()
                ToastUtils.showShort("提交成功")
                getView()?.setCapitalist()

            },{
                getView()?.dismissLoadingDialog()
                ToastUtils.showShort(it.message)
            }
        )
    }

    override fun getInverstorDetail(authorization: String, userId: Int) {
        getModel().getInverstorDetail(authorization,userId).ui({
            getView()?.getInverstorDetail(it.data!!)
        },{
            ToastUtils.showShort(it.message)
        })
    }


    override fun createModel(): InverstorMessageEditContract.Model =InverstorMessageEditModel()
}