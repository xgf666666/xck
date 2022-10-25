package com.example.xck.ui.person.activity

import android.Manifest
import android.content.Intent
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UtilsTransActivity
import com.example.xck.BuildConfig
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.common.Constants
import com.example.xck.ui.person.mvp.contract.InverstorMessageEditContract
import com.example.xck.ui.person.mvp.persenter.InverstorMessageEditPersenter
import com.xx.baseutilslibrary.common.ImageChooseHelper
import kotlinx.android.synthetic.main.activity_investor_message_edit.*
import kotlinx.android.synthetic.main.ic_title.*

class InvestorMessageEditActivity : BaseMvpActivity<InverstorMessageEditPersenter>(),InverstorMessageEditContract.View {
    private var imageChooseHelper: ImageChooseHelper? = null

    override fun getActivityLayoutId(): Int =R.layout.activity_investor_message_edit


    override fun initData() {
        tvTilte.text="投资人信息完善"
        initImageChooseHelper()
    }

    override fun initEvent() {
        ivPerson.setOnClickListener {
            showEditAvatarDialog()
//            imageChooseHelper!!.startImageChoose();
        }
    }

    override fun createPresenter(): InverstorMessageEditPersenter = InverstorMessageEditPersenter(this)

    private fun initImageChooseHelper(){
        imageChooseHelper = ImageChooseHelper.Builder()
            .setUpActivity(this)
            .setAuthority("${BuildConfig.APPLICATION_ID}.fileprovider")//设置文件提供者
            .setDirPath(Constants.DOWNLOAD_PATH)//设置文件存储路径
            .isCrop(true)//开启裁剪
            .setCompressQuality(100)//压缩质量[1,100]
            .setSize(120, 120)//裁剪尺寸
            .setOnFinishChooseAndCropImageListener { bitmap, file ->
                //                    显示选好得图片
//                iv_hand.setImageBitmap(bitmap)
                ivPerson.setImageBitmap(bitmap)
                showLoadingDialog()
//                getPresenter().setHandImageView(Constants.getToken(), EncodeUtils.base64Encode2String(file?.readBytes()))
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
//                    ChooseImageDialogWrapper(imageChooseHelper)
//                        .showChooseImage()
                    imageChooseHelper!!.startImageChoose();
                }

                override fun onDenied() {

                }
            })
            .rationale { utilsTransActivity: UtilsTransActivity, shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest -> shouldRequest.again(true)
            }
            .request()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageChooseHelper!!.onActivityResult(requestCode, resultCode, data)
    }
}