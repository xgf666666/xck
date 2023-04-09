package com.example.xck.ui.person.activity

import android.Manifest
import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UtilsTransActivity
import com.example.xck.BuildConfig
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.Capitalist
import com.example.xck.bean.Select
import com.example.xck.bean.UpLoadFile
import com.example.xck.common.Constants
import com.example.xck.dialog.SelectDialog
import com.example.xck.ui.person.mvp.contract.InverstorMessageEditContract
import com.example.xck.ui.person.mvp.persenter.InverstorMessageEditPersenter
import com.example.xck.utils.loadImag
import com.xx.baseutilslibrary.common.ImageChooseHelper
import kotlinx.android.synthetic.main.activity_investor_message_edit.*
import kotlinx.android.synthetic.main.activity_investor_message_edit.icLoading
import kotlinx.android.synthetic.main.activity_investor_message_edit.ivPerson
import kotlinx.android.synthetic.main.activity_investor_message_edit.llAddress
import kotlinx.android.synthetic.main.activity_investor_message_edit.llFinance
import kotlinx.android.synthetic.main.activity_investor_message_edit.llFleid
import kotlinx.android.synthetic.main.activity_investor_message_edit.tvAddress
import kotlinx.android.synthetic.main.activity_investor_message_edit.tvFinance
import kotlinx.android.synthetic.main.activity_investor_message_edit.tvFleid
import kotlinx.android.synthetic.main.activity_investor_message_edit.tvSend
import kotlinx.android.synthetic.main.activity_project_message_edit.*
import kotlinx.android.synthetic.main.ic_title.*

class InvestorMessageEditActivity : BaseMvpActivity<InverstorMessageEditPersenter>(),InverstorMessageEditContract.View,
    View.OnClickListener {
    private var cardUri: String?=null
    private var imageUri: String?=null
    private var imageChooseHelper: ImageChooseHelper? = null
    private var selectDialog:SelectDialog?=null
    private var card=false
    private var filids: MutableList<Int>?= mutableListOf()
    private var fanances: MutableList<Int>?=mutableListOf()
    private var addresss: MutableList<Int>?=mutableListOf()
    private var completeStatus=0;
    private var capitalistId=0;

    override fun getActivityLayoutId(): Int =R.layout.activity_investor_message_edit


    override fun initData() {
        tvTilte.text="投资人信息完善"
        initImageChooseHelper()
        completeStatus = intent.getIntExtra("complete_status", 0)
        if (completeStatus==1){
            icLoading.visibility=View.VISIBLE
            getPresenter().getInverstorDetail(Constants.getToken(),Constants.getPersonal().id)
        }
    }

    override fun initEvent() {
        ivPerson.setOnClickListener {
            card=false
            showEditAvatarDialog()
        }
        iv_card.setOnClickListener {
            card=true
            showEditAvatarDialog()
        }
        tvSend.setOnClickListener {
            if (completeStatus==0){
                getPresenter().setCapitalist(Constants.getToken(),etName.text.toString(),etPerson.text.toString(),etPosition.text.toString(),
                    "${etlessMoney.text.toString()}"+"-"+"${etMoreMoney.text.toString()}",imageUri!!,etOrganIntroduce.text.toString(),
                    etCase.text.toString(),cardUri!!,
                    filids!!.toIntArray(),fanances!!.toIntArray() ,addresss!!.toIntArray(),0)

            }else{
                getPresenter().setCapitalist(Constants.getToken(),etName.text.toString(),etPerson.text.toString(),etPosition.text.toString(),
                    "${etlessMoney.text.toString()}"+"-"+"${etMoreMoney.text.toString()}",imageUri!!,etOrganIntroduce.text.toString(),
                    etCase.text.toString(),cardUri!!,
                    filids!!.toIntArray(),fanances!!.toIntArray() ,addresss!!.toIntArray(),capitalistId)

            }

        }
        llFinance.setOnClickListener(this)
        llFleid.setOnClickListener(this)
        llAddress.setOnClickListener(this)
    }

    override fun createPresenter(): InverstorMessageEditPersenter = InverstorMessageEditPersenter(this)

    private fun initImageChooseHelper(){
        imageChooseHelper = ImageChooseHelper.Builder()
            .setUpActivity(this)
            .setAuthority("${BuildConfig.APPLICATION_ID}.fileprovider")//设置文件提供者
            .setDirPath(Constants.DOWNLOAD_PATH)//设置文件存储路径
            .isCrop(false)//开启裁剪
            .setCompressQuality(100)//压缩质量[1,100]
            .setSize(120, 120)//裁剪尺寸
            .setOnFinishChooseAndCropImageListener { bitmap, file ->
                //                    显示选好得图片
//                ivPerson.setImageBitmap(bitmap)
                showLoadingDialog()
                getPresenter().upload(file)
            }
            .setOnFinishChooseImageListener { uri, file ->
               /* if (card){
                    iv_card.setImageURI(uri)
                }else{
                    ivPerson.setImageURI(uri)
                }*/
                showLoadingDialog()
                getPresenter().upload(file)
            }
            .create()
    }

    /**
     * 显示修改头像弹窗
     */
    private fun showEditAvatarDialog() {
        //选图弹窗
        //请求相机和内存读取权限
        PermissionUtils.permission(
//            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE)
            .callback(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    //被给予权限,调起选图弹窗
                    imageChooseHelper!!.startImageChoose();
                }

                override fun onDenied() {

                }
            })
            .rationale { utilsTransActivity: UtilsTransActivity, shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest -> shouldRequest.again(true)
            }
            .request()
    }
    override fun onClick(v: View?) {
        if (selectDialog==null){
            selectDialog= SelectDialog(this)
        }
        selectDialog?.setOnSureListener( object : SelectDialog.OnSureListener{
            override fun onSure(
                filid: ArrayList<Select.ChildrenBean>,
                fanance: ArrayList<Select.ChildrenBean>,
                address: ArrayList<Select.ChildrenBean>
            ) {
                var field=""
                var fananceStr=""
                var addressStr=""
                filids?.clear()
                fanances?.clear()
                addresss?.clear()
                for (i in 0 until filid.size){
                    filids!!.add(filid[i].id)
                    if (i==0){
                        field+=filid[i].attr_name
                    }else{
                        field+="、${filid[i].attr_name}"
                    }

                }
                for (i in 0 until fanance.size){
                    fanances!!.add(fanance[i].id)
                    if (i==0){
                        fananceStr+=fanance[i].attr_name
                    }else{
                        fananceStr+="、${fanance[i].attr_name}"
                    }
                }

                for (i in 0 until address.size){
                    addresss!!.add(address[i].id)
                    if (i==0){
                        addressStr+=address[i].attr_name
                    }else{
                        addressStr+="、${address[i].attr_name}"
                    }
                }

                tvFleid.text=field
                tvFinance.text=fananceStr
                tvAddress.text=addressStr
            }

        })
        selectDialog?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageChooseHelper!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun getloadFile(upLoadFile: UpLoadFile) {
        if (card){
            cardUri=upLoadFile.url
            iv_card.loadImag(cardUri!!)
        }else{
            imageUri=upLoadFile.url
            ivPerson.loadImag(imageUri!!)
        }
    }

    override fun setCapitalist() {
        finish()
    }

    override fun getInverstorDetail(capitalist: Capitalist) {
        capitalistId=capitalist.id
        icLoading.visibility=View.GONE
        etName.setText(capitalist.capitalist_name)
        etPerson.setText(capitalist.contact_name)
        etPosition.setText(capitalist.position)
        var amount=capitalist.single_amount.split("-")
        if (amount.size>1){
            etlessMoney.setText(amount[0])
            etMoreMoney.setText(amount[1])
        }
        ivPerson.loadImag(capitalist.avatar)
        iv_card.loadImag(capitalist.business_card_img)
            cardUri=capitalist.business_card_img
            imageUri=capitalist.avatar
        var address=""//地址
        var trade=""//行业
        var finance=""//轮次
        for (i in 0 until capitalist.attr_list.size) {
            if (capitalist.attr_list[i].attr_parent_id == 1) {
                if (StringUtils.isEmpty(trade)) {
                    trade += capitalist.attr_list[i].attr_name
                } else {
                    trade += ",${capitalist.attr_list[i].attr_name}"
                }
                filids?.add(capitalist.attr_list[i].attr_id)
            }
            if (capitalist.attr_list[i].attr_parent_id == 2) {
                if (StringUtils.isEmpty(address)) {
                    address += capitalist.attr_list[i].attr_name
                } else {
                    address += ",${capitalist.attr_list[i].attr_name}"
                }
                fanances?.add(capitalist.attr_list[i].attr_id)
            }
            if (capitalist.attr_list[i].attr_parent_id == 3) {
                if (StringUtils.isEmpty(finance)) {
                    finance += capitalist.attr_list[i].attr_name
                } else {
                    finance += ",${capitalist.attr_list[i].attr_name}"
                }
                addresss?.add(capitalist.attr_list[i].attr_id)
            }
        }
        tvFleid.text=trade;
        tvAddress.text=address
        tvFinance.text=finance
        etOrganIntroduce.setText(capitalist.introduction)
        etCase.setText(capitalist.cases)
    }
}