package com.example.xck.ui.person.activity


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.CountDownTimer
import android.util.Base64
import androidx.viewbinding.ViewBinding
import com.example.xck.App
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.CodeImage
import com.example.xck.bean.Login
import com.example.xck.common.Constants
import com.example.xck.databinding.ActivityRegisterBinding
import com.example.xck.ui.person.mvp.contract.RegisterContract
import com.example.xck.ui.person.mvp.persenter.RegisterPersenter



class RegisterActivity : BaseMvpActivity<RegisterPersenter>(),RegisterContract.View {
    private var iamge:CodeImage?=null
    private val mBinding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    override fun initData() {
        (application as App).addActivity(this)
        getPresenter().getCodeImage()
    }

    override fun initEvent() {
        mBinding.ivIconCode.setOnClickListener {
            getPresenter().getCodeImage()
        }
        mBinding.ivSendNote.setOnClickListener {
            getPresenter().getCode("reg",mBinding.etPhone.text.toString())
        }
        mBinding.tvAgreement.setOnClickListener {
            startActivity(Intent(this,ServiceActivity::class.java))
        }
        mBinding.tvRegister.setOnClickListener {
            if (!mBinding.cbSelect.isChecked){
                showToast("请勾选用户服务协议")
                return@setOnClickListener
            }
            getPresenter().register(mBinding.etPhone.text.toString(),mBinding.etIconCode.text.toString(),mBinding.etNote.text.toString(),iamge!!.key,mBinding.etPassword.text.toString(),mBinding.etPasswordAgain.text.toString())
        }
        mBinding.tvToLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            (application as App).cleanActivity()
        }
    }

    override fun createPresenter(): RegisterPersenter =RegisterPersenter(this)
    override fun getCodeImage(image: CodeImage) {
        this.iamge=image
        var type=image.base64.split(",")
        val imgBase= Base64.decode(type[type.size-1],Base64.DEFAULT)
        mBinding.ivIconCode.setImageBitmap(BitmapFactory.decodeByteArray(imgBase,0,imgBase.size))
    }

    override fun getCode() {
        countDownTime()
    }

    override fun register(register: Login) {
    }

    override fun registerIM() {

    }

    override fun login(login: Login) {
        showToast("注册成功")
        Constants.putPersonal(login.user_info)
        Constants.putToken(login.access_token)
        Constants.login()
//        startActivity(Intent(this,MainActivity::class.java))
        (application as App).cleanActivity()
    }

    private fun countDownTime() {
        //用安卓自带的CountDownTimer实现
        val mTimer: CountDownTimer = object : CountDownTimer(60 * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                mBinding.ivSendNote.text = (millisUntilFinished / 1000).toString() + "s"
            }

            override fun onFinish() {
                mBinding.ivSendNote.isEnabled = true
                mBinding.ivSendNote.text = "获取验证码"
                cancel()
            }
        }
        mTimer.start()
        mBinding.ivSendNote.isEnabled = false
    }
    override fun onDestroy() {
        super.onDestroy()
        (application as App).deleteActivity(this)
    }

    override fun getViewBinding(): ViewBinding {
        return mBinding
    }
}