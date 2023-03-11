package com.example.xck.ui.person.activity


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Base64
import com.example.xck.App
import com.example.xck.MainActivity
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.CodeImage
import com.example.xck.bean.Login
import com.example.xck.bean.Register
import com.example.xck.common.Constants
import com.example.xck.ui.person.mvp.contract.RegisterContract
import com.example.xck.ui.person.mvp.persenter.RegisterPersenter
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : BaseMvpActivity<RegisterPersenter>(),RegisterContract.View {
    private var iamge:CodeImage?=null

    override fun getActivityLayoutId(): Int =R.layout.activity_register
    override fun initData() {
        (application as App).addActivity(this)
        getPresenter().getCodeImage()
    }

    override fun initEvent() {
        ivIconCode.setOnClickListener {
            getPresenter().getCodeImage()
        }
        ivSendNote.setOnClickListener {
            getPresenter().getCode("reg",etPhone.text.toString())
        }
        tv_agreement.setOnClickListener {
            startActivity(Intent(this,ServiceActivity::class.java))
        }
        tvRegister.setOnClickListener {
            if (!cb_select.isChecked){
                showToast("请勾选用户服务协议")
                return@setOnClickListener
            }
            getPresenter().register(etPhone.text.toString(),etIconCode.text.toString(),etNote.text.toString(),iamge!!.key,etPassword.text.toString(),etPasswordAgain.text.toString())
        }
        tvToLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            (application as App).cleanActivity()
        }
    }

    override fun createPresenter(): RegisterPersenter =RegisterPersenter(this)
    override fun getCodeImage(image: CodeImage) {
        this.iamge=image
        var type=image.base64.split(",")
        val imgBase= Base64.decode(type[type.size-1],Base64.DEFAULT)
        ivIconCode.setImageBitmap(BitmapFactory.decodeByteArray(imgBase,0,imgBase.size))
    }

    override fun getCode() {
        countDownTime()
    }

    override fun register(register: Register) {
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
                ivSendNote.text = (millisUntilFinished / 1000).toString() + "s"
            }

            override fun onFinish() {
                ivSendNote.isEnabled = true
                ivSendNote.text = "获取验证码"
                cancel()
            }
        }
        mTimer.start()
        ivSendNote.isEnabled = false
    }
    override fun onDestroy() {
        super.onDestroy()
        (application as App).deleteActivity(this)
    }
}