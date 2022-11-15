package com.example.xck.ui.person.activity

import android.Manifest
import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UtilsTransActivity
import com.example.xck.BuildConfig
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.Select
import com.example.xck.bean.UpLoadFile
import com.example.xck.common.Constants
import com.example.xck.dialog.SelectDialog
import com.example.xck.ui.person.mvp.contract.InverstorMessageEditContract
import com.example.xck.ui.person.mvp.persenter.InverstorMessageEditPersenter
import com.xx.baseutilslibrary.common.ImageChooseHelper
import kotlinx.android.synthetic.main.activity_investor_message_edit.*
import kotlinx.android.synthetic.main.activity_investor_message_edit.ivPerson
import kotlinx.android.synthetic.main.activity_investor_message_edit.tvSend
import kotlinx.android.synthetic.main.ic_title.*

class InvestorMessageEditActivity : BaseMvpActivity<InverstorMessageEditPersenter>(),InverstorMessageEditContract.View,
    View.OnClickListener {
    private var cardUri: String?=null
    private var imageUri: String?=null
    private var imageChooseHelper: ImageChooseHelper? = null
    private var selectDialog:SelectDialog?=null
    private var card=false
    private var filids: ArrayList<Int>?=null
    private var fanances: ArrayList<Int>?=null
    private var addresss: ArrayList<Int>?=null

    override fun getActivityLayoutId(): Int =R.layout.activity_investor_message_edit


    override fun initData() {
        tvTilte.text="投资人信息完善"
        initImageChooseHelper()
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
            getPresenter().setCapitalist(Constants.getToken(),etName.text.toString(),etPerson.text.toString(),etPosition.text.toString(),
                "${etlessMoney.text.toString()}-${etMoreMoney.text.toString()}",imageUri!!,etOrganIntroduce.text.toString(),
                etCase.text.toString(),cardUri!!,
                filids!!.toArray() as Array<Int>,fanances!!.toArray() as Array<Int>,addresss!!.toArray() as Array<Int>)

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
                ivPerson.setImageBitmap(bitmap)
                showLoadingDialog()
                getPresenter().upload(file)
            }
            .setOnFinishChooseImageListener { uri, file ->
                if (card){
                    iv_card.setImageURI(uri)
                }else{
                    ivPerson.setImageURI(uri)
                }
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
                filids =ArrayList()
                fanances =ArrayList()
                addresss =ArrayList()
                var field=""
                var fananceStr=""
                var addressStr=""
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
                    addresss!!.add(filid[i].id)
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
        }else{
            imageUri=upLoadFile.url
        }
    }
}