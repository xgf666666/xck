package com.example.xck.ui.person.mvp.persenter

import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.xck.extensions.ui
import com.example.xck.ui.person.mvp.contract.ProjectMessageEditContract
import com.example.xck.ui.person.mvp.model.ProjectMessageEditModel
import java.io.File

class ProjectMessageEditPersenter(view: ProjectMessageEditContract.View) :
    ProjectMessageEditContract.Persenter(view) {
    override fun upload(file: File) {
        getView()?.showLoadingDialog()
        getModel().upload(file).ui({
            getView()?.getloadFile(it.data!!)
            ToastUtils.showShort(it.msg)
            getView()?.dismissLoadingDialog()
//            getView()?.showToast("成功")
        }, {
            getView()?.dismissLoadingDialog()
            getView()?.showToast(it.message)
        })
    }

    override fun setProject(
        Authorization: String,
        project_name: String,
        logo_image: String,
        found_time: String,
        introduction: String,
        wait_finance: String,
        operation: String,
        advantage: String,
        history_financice: String,
        project_file: String,
        team_member: String,
        industries: Array<Int>,
        stages: Array<Int>,
        location: Array<Int>
    ) {
        if (StringUtils.isEmpty(project_name)) {
            ToastUtils.showShort("请输入项目名")
            return
        }
        if (StringUtils.isEmpty(logo_image)) {
            ToastUtils.showShort("请上传头像")
            return
        }
        if (StringUtils.isEmpty(found_time)) {
            ToastUtils.showShort("请选择成立年月")
            return
        }
        if (StringUtils.isEmpty(introduction)) {
            ToastUtils.showShort("请输入项目介绍")
            return
        }
        if (StringUtils.isEmpty(wait_finance)) {
            ToastUtils.showShort("请输入期望融资信息")
            return
        }
        if (StringUtils.isEmpty(operation)) {
            ToastUtils.showShort("请输入运营数据")
            return
        }
        if (StringUtils.isEmpty(advantage)) {
            ToastUtils.showShort("请输入项目优势")
            return
        }
        if (StringUtils.isEmpty(history_financice)) {
            ToastUtils.showShort("请输入历史融资")
            return
        }
        if (StringUtils.isEmpty(project_file)) {
            ToastUtils.showShort("请上传商业书")
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
        getModel().setProject(Authorization, project_name, logo_image, found_time, introduction,
            wait_finance, operation, advantage, history_financice,
            project_file, team_member, industries, stages, location).ui({
            getView()?.dismissLoadingDialog()
            ToastUtils.showShort("上传成功")

        },{
            ToastUtils.showShort(it.message)
        })

    }

    override fun createModel(): ProjectMessageEditContract.Model = ProjectMessageEditModel()
}