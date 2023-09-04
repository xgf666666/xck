package com.example.xck.ui.person

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UtilsTransActivity
import com.bumptech.glide.Glide
import com.example.xck.BuildConfig
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.bean.Login
import com.example.xck.common.Constants
import com.example.xck.databinding.FragmentHomeBinding
import com.example.xck.databinding.FragmentPersonBinding
import com.example.xck.dialog.LoginOutDialog
import com.example.xck.ui.person.activity.*
import com.example.xck.ui.person.mvp.contract.PersonContract
import com.example.xck.ui.person.mvp.persenter.PersonPersenter
import com.example.xck.utils.loadImag
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.constants.EaseCommom
import com.xx.baseutilslibrary.common.ImageChooseHelper

/**
 *   author ： xiaogf
 *   time    ： 2022/10/8
 */
class PersonFragment:BaseMvpFragment<PersonPersenter,FragmentPersonBinding>(),PersonContract.View {
    var isHide=false;
    var message:String=""
    private var imageChooseHelper: ImageChooseHelper? = null
    override fun init(view: View?) {
        initImageChooseHelper()
        mBindingView!!.icPrePareLogin.tvToLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            this?.startActivity(intent)
        }
        mBindingView!!.icPrePareLogin.tvRegister.setOnClickListener {
            startActivity(Intent(context, RegisterActivity::class.java))
        }
/*
        ivPerson.setOnClickListener {
            val intent = Intent(context, PrepareLoginActivity::class.java)
            context?.startActivity(intent)
        }
*/
        mBindingView!!.llRoleIdentity.setOnClickListener {
            val intent = Intent(context, PrepareRoleIdentifyActivity::class.java)
            context?.startActivity(intent)
        }
        mBindingView!!.llHelp.setOnClickListener {
            startActivity(Intent(context,HelpActivity::class.java))
        }
        mBindingView!!.llAboutUs.setOnClickListener {
            startActivity(Intent(context,AboutUsActivity::class.java))
        }
        mBindingView!!.llMessage.setOnClickListener {
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
        mBindingView!!.llIdentifyPw.setOnClickListener {
            startActivity(Intent(context,ModifyPasswordActivity::class.java))
        }
        mBindingView!!.llLoginOut.setOnClickListener {
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
        mBindingView!!.ivPerson.setOnClickListener {
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
            mBindingView!!.icPrePareLogin.root.visibility=View.GONE
        }else{
            mBindingView!!.icPrePareLogin.root.visibility=View.VISIBLE
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
        mBindingView!!.ivPerson.loadImag(userInfo.avatar)
        if (userInfo.user_type_select==1){
            mBindingView!!.tvMessage.text="创业项目信息"
        }else if (userInfo.user_type_select==2){
            mBindingView!!.tvMessage.text="投资人信息"
        }
        mBindingView!!.tvPhone.text=userInfo.mobile_phone
        mBindingView!!.tvNames.text=userInfo.real_name
        mBindingView!!.tvQuota.text="额度："+userInfo.quota_num

        when( userInfo.proof_status){
           0->{mBindingView!!.tvRenz.visibility=View.VISIBLE
               mBindingView!!.ivRenz.visibility=View.VISIBLE
               mBindingView!!.llRoleIdentity.isClickable=true
               mBindingView!!.tvRenz.text="前往认证"
               message="需要等认证通过"
               mBindingView!!.ivRenMessage.visibility=View.GONE
           }
            1->{
                mBindingView!!.ivRenz.visibility=View.GONE
                mBindingView!!.tvRenz.visibility=View.VISIBLE
                mBindingView!!.tvRenz.text="审核中"
                mBindingView!!.llRoleIdentity.isClickable=false
                mBindingView!!.ivRenMessage.visibility=View.GONE
                message="需要等认证通过"
            }
            2->{
                mBindingView!!.ivRenz.visibility=View.GONE
                mBindingView!!.tvRenz.visibility=View.VISIBLE
                mBindingView!!.tvRenz.text="认证成功"
                mBindingView!!.llRoleIdentity.isClickable=false
                mBindingView!!.ivRenMessage.visibility=View.VISIBLE
                if (userInfo.complete_status==1){
                    message="已完善"
                }else{
                    message="完善的信息让机构了解您"
                }
            }
            3->{
                mBindingView!!.ivRenz.visibility=View.VISIBLE
                mBindingView!!.tvRenz.visibility=View.VISIBLE
                mBindingView!!.tvRenz.text="认证不通过，需重新认证"
                mBindingView!!.llRoleIdentity.isClickable=true
                message="需要等认证通过"
            }
        }
        mBindingView!!.tvRenMessage.text=message
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            imageChooseHelper!!.onActivityResult(requestCode,resultCode,data)
    }

    override fun getViewBinding(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPersonBinding {
        return FragmentPersonBinding.inflate(layoutInflater)
    }

}



