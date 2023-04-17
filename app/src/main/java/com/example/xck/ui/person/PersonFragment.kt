package com.example.xck.ui.person

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UtilsTransActivity
import com.bumptech.glide.Glide
import com.example.xck.BuildConfig
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.bean.Login
import com.example.xck.common.Constants
import com.example.xck.dialog.LoginOutDialog
import com.example.xck.ui.person.activity.*
import com.example.xck.ui.person.mvp.contract.PersonContract
import com.example.xck.ui.person.mvp.persenter.PersonPersenter
import com.example.xck.utils.changeKm
import com.example.xck.utils.filechoose.FxFileDialogArgs
import com.example.xck.utils.filechoose.FxHelp
import com.example.xck.utils.loadImag
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.constants.EaseCommom
import com.xx.baseutilslibrary.common.ImageChooseHelper
import kotlinx.android.synthetic.main.activity_prepare_login.*
import kotlinx.android.synthetic.main.activity_project_message_edit.*
import kotlinx.android.synthetic.main.fragment_person.*
import kotlinx.android.synthetic.main.fragment_person.ivPerson
import java.io.File

/**
 *   author ： xiaogf
 *   time    ： 2022/10/8
 */
class PersonFragment:BaseMvpFragment<PersonPersenter>(),PersonContract.View {
    var isHide=false;
    var message:String=""
    private var imageChooseHelper: ImageChooseHelper? = null
    override fun getFragmentLayoutId(): Int = R.layout.fragment_person
    override fun init(view: View?) {
        initImageChooseHelper()
        tvToLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            this?.startActivity(intent)
        }
         tvRegister.setOnClickListener {
            startActivity(Intent(context, RegisterActivity::class.java))
        }
/*
        ivPerson.setOnClickListener {
            val intent = Intent(context, PrepareLoginActivity::class.java)
            context?.startActivity(intent)
        }
*/
        llRoleIdentity.setOnClickListener {
            val intent = Intent(context, PrepareRoleIdentifyActivity::class.java)
            context?.startActivity(intent)
        }
        llHelp.setOnClickListener {
            startActivity(Intent(context,HelpActivity::class.java))
        }
        llAboutUs.setOnClickListener {
            startActivity(Intent(context,AboutUsActivity::class.java))
        }
        llMessage.setOnClickListener {
            var intent:Intent?=null
            if (Constants.getPersonal().user_type_select==1){
                if (Constants.getPersonal().proof_status==2){
                    intent = Intent(context, ProjectMessageEditActivity::class.java)
                    intent?.putExtra("complete_status",Constants.getPersonal().complete_status)
                    context?.startActivity(intent)
                }else{
                    ToastUtils.showShort(message)
                    return@setOnClickListener
                }
            }else if (Constants.getPersonal().user_type_select==2){
                if (Constants.getPersonal().proof_status==2){
                    intent = Intent(context, InvestorMessageEditActivity::class.java)
                    intent?.putExtra("complete_status",Constants.getPersonal().complete_status)
                    context?.startActivity(intent)
                }else{
                    ToastUtils.showShort(message)
                    return@setOnClickListener
                }
            }
        }
        ll_identifyPw.setOnClickListener {
            startActivity(Intent(context,ModifyPasswordActivity::class.java))
        }
        llLoginOut.setOnClickListener {
            var loginOutDialog = context?.let { it1 -> LoginOutDialog(it1) }
            loginOutDialog?.setLoginOutClickListener(object :LoginOutDialog.LoginOutClickListener{
                override fun sure() {
                    Thread(Runnable {
                        Constants.loginOut()
                        Constants.putToken("")
                        Constants.putPersonal(Login.UserInfoBean())
                        EMClient.getInstance().logout(true)
                    }).start()
                    ToastUtils.showShort("退出登录!")
                    startActivity(Intent(context,LoginActivity::class.java))
                }
            })
            loginOutDialog?.show()


        }
        ivPerson.setOnClickListener {
            showEditAvatarDialog()
        }
    }
    private fun initImageChooseHelper(){
        imageChooseHelper = ImageChooseHelper.Builder()
            .setUpActivity(activity)
            .setAuthority("${BuildConfig.APPLICATION_ID}.fileprovider")//设置文件提供者
            .setDirPath(Constants.DOWNLOAD_PATH)//设置文件存储路径
            .isCrop(false)//开启裁剪
            .setCompressQuality(100)//压缩质量[1,100]
            .setSize(120, 120)//裁剪尺寸
            .setOnFinishChooseAndCropImageListener { bitmap, file ->
                //                    显示选好得图片
//                iv_hand.setImageBitmap(bitmap)
//                ivPerson.setImageBitmap(bitmap)
                showLoadingDialog()
                getPresenter().upload(file)
            }
            .setOnFinishChooseImageListener { uri, file ->
//                ivPerson.setImageURI(uri)
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
    override fun onHiddenChanged(hidden: Boolean) {
        isHide=hidden
       if (!hidden&&Constants.isLogin()){
           getPresenter().getUserInfo()
       }
       if (!hidden)
        setVisLogin()
    }
    private fun setVisLogin(){
        if (Constants.isLogin()){
            icPrePareLogin.visibility=View.GONE
        }else{
            icPrePareLogin.visibility=View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isHide){
            setVisLogin()
            if (Constants.isLogin()){
                getPresenter().getUserInfo()
            }
        }
    }

    override fun createPresenter(): PersonPersenter = PersonPersenter(this)
    override fun userInfo(userInfo: Login.UserInfoBean) {
        Constants.putPersonal(userInfo)
        EaseCommom.getInstance().avatar=userInfo.avatar
        ivPerson.loadImag(userInfo.avatar)
        if (userInfo.user_type_select==1){
            tvMessage.text="创业项目信息"
        }else if (userInfo.user_type_select==2){
            tvMessage.text="投资人信息"
        }
        tvPhone.text=userInfo.mobile_phone
        tvNames.text=userInfo.real_name
        tvQuota.text="额度："+userInfo.quota_num

        when( userInfo.proof_status){
           0->{tvRenz.visibility=View.VISIBLE
               ivRenz.visibility=View.VISIBLE
               llRoleIdentity.isClickable=true
               tvRenz.text="前往认证"
               message="需要等认证通过"
               ivRenMessage.visibility=View.GONE
           }
            1->{
                ivRenz.visibility=View.GONE
                tvRenz.visibility=View.VISIBLE
                tvRenz.text="审核中"
                llRoleIdentity.isClickable=false
                ivRenMessage.visibility=View.GONE
                message="需要等认证通过"
            }
            2->{
                ivRenz.visibility=View.GONE
                tvRenz.visibility=View.VISIBLE
                tvRenz.text="认证成功"
                llRoleIdentity.isClickable=false
                ivRenMessage.visibility=View.VISIBLE
                if (userInfo.complete_status==1){
                    message="已完善"
                }else{
                    message="完善的信息让机构了解您"
                }
            }
            3->{
                ivRenz.visibility=View.VISIBLE
                tvRenz.visibility=View.VISIBLE
                tvRenz.text="认证不通过，需重新认证"
                llRoleIdentity.isClickable=true
                message="需要等认证通过"
            }
        }
        tvRenMessage.text=message
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            imageChooseHelper!!.onActivityResult(requestCode,resultCode,data)
    }

}



