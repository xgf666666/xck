package com.example.xck.ui.person.activity

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.CountDownTimer
import android.util.Base64
import android.view.View
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.CodeImage
import com.example.xck.bean.VerifyPhone
import com.example.xck.common.Constants
import com.example.xck.ui.person.mvp.contract.ModifyPasswordContract
import com.example.xck.ui.person.mvp.persenter.ModifyPasswordPersenter
import kotlinx.android.synthetic.main.activity_modify_password.*
import kotlinx.android.synthetic.main.activity_modify_password.etIconCode
import kotlinx.android.synthetic.main.activity_modify_password.etNote
import kotlinx.android.synthetic.main.activity_modify_password.etPhone
import kotlinx.android.synthetic.main.activity_modify_password.ivIconCode
import kotlinx.android.synthetic.main.activity_modify_password.ivSendNote
import kotlinx.android.synthetic.main.activity_modify_password.tvRegister

class ModifyPasswordActivity : BaseMvpActivity<ModifyPasswordPersenter>(),ModifyPasswordContract.View {
    private var iamge: CodeImage?=null
    private var verifyPhone: VerifyPhone?=null
    private var type=0

    override fun getActivityLayoutId(): Int =R.layout.activity_modify_password


    override fun initData() {
        type=intent.getIntExtra("type",0)
        if (type==1){
            tvTilte.text="忘记密码"
        }else{
            tvTilte.text="修改密码"
        }
        getPresenter().getCodeImage()
    }

    override fun initEvent() {
        tvRegister.setOnClickListener {
            if (type==0){
                getPresenter().verifyPhone(Constants.getToken(),"update",etPhone.text.toString(),etIconCode.text.toString(),iamge!!.key,etNote.text.toString())

            }else{
                getPresenter().verifyPhone("","reset",etPhone.text.toString(),etIconCode.text.toString(),iamge!!.key,etNote.text.toString())

            }
        }
        tvSendLogin.setOnClickListener {
            if (type==0){
                getPresenter().modify(Constants.getToken(),etPassword.text.toString(),etPasswordAgain.text.toString(),verifyPhone!!.verify_token)
            }else{
                getPresenter().forgetPW(etPassword.text.toString(),etPasswordAgain.text.toString(),verifyPhone!!.verify_token)
            }
        }
        ivIconCode.setOnClickListener {
            getPresenter().getCodeImage()
        }
        ivSendNote.setOnClickListener {
            if (type==0){
                getPresenter().getCode("update",etPhone.text.toString())
            }else{
                getPresenter().getCode("reset",etPhone.text.toString())
            }
        }
    }

    override fun createPresenter(): ModifyPasswordPersenter = ModifyPasswordPersenter(this)
    override fun getCodeImage(image: CodeImage) {
        this.iamge=image
        var type=image.base64.split(",")
        val imgBase= Base64.decode(type[type.size-1], Base64.DEFAULT)
        ivIconCode.setImageBitmap(BitmapFactory.decodeByteArray(imgBase,0,imgBase.size))
    }

    override fun getCode() {
        countDownTime()
    }

    override fun verifyPhone(verifyPhone: VerifyPhone) {
        this.verifyPhone=verifyPhone
        llPhone.visibility=View.GONE
        llPassword.visibility=View.VISIBLE
        tvCodeRz.setTextColor(resources.getColor(R.color.text_333333))
        tvNewPassdword.setTextColor(resources.getColor(R.color.text_FF6C2EA5))
//        tvRegister.setText("提交%登录")
    }

    override fun modify() {
        finish()
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

}