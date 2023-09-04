package com.example.xck.ui.person.activity

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.CountDownTimer
import android.util.Base64
import android.view.View
import androidx.viewbinding.ViewBinding
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.CodeImage
import com.example.xck.bean.VerifyPhone
import com.example.xck.common.Constants
import com.example.xck.databinding.ActivityInvestorDetailBinding
import com.example.xck.databinding.ActivityModifyPasswordBinding
import com.example.xck.ui.person.mvp.contract.ModifyPasswordContract
import com.example.xck.ui.person.mvp.persenter.ModifyPasswordPersenter


class ModifyPasswordActivity : BaseMvpActivity<ModifyPasswordPersenter>(),ModifyPasswordContract.View {
    private var iamge: CodeImage?=null
    private var verifyPhone: VerifyPhone?=null
    private var type=0
    private val mBinding by lazy { ActivityModifyPasswordBinding.inflate(layoutInflater) }


    override fun initData() {
        type=intent.getIntExtra("type",0)
        if (type==1){
            mBinding.tvTilte.text="忘记密码"
        }else{
            mBinding.tvTilte.text="修改密码"
        }
        getPresenter().getCodeImage()
    }

    override fun initEvent() {
        mBinding.tvRegister.setOnClickListener {
            if (type==0){
                getPresenter().verifyPhone(Constants.getToken(),"update", mBinding.etPhone.text.toString(), mBinding.etIconCode.text.toString(),iamge!!.key, mBinding.etNote.text.toString())

            }else{
                getPresenter().verifyPhone("","reset", mBinding.etPhone.text.toString(), mBinding.etIconCode.text.toString(),iamge!!.key, mBinding.etNote.text.toString())

            }
        }
        mBinding.tvSendLogin.setOnClickListener {
            if (type==0){
                getPresenter().modify(Constants.getToken(), mBinding.etPassword.text.toString(), mBinding.etPasswordAgain.text.toString(),verifyPhone!!.verify_token)
            }else{
                getPresenter().forgetPW( mBinding.etPassword.text.toString(), mBinding.etPasswordAgain.text.toString(),verifyPhone!!.verify_token)
            }
        }
        mBinding.ivIconCode.setOnClickListener {
            getPresenter().getCodeImage()
        }
        mBinding.ivSendNote.setOnClickListener {
            if (type==0){
                getPresenter().getCode("update", mBinding.etPhone.text.toString())
            }else{
                getPresenter().getCode("reset", mBinding.etPhone.text.toString())
            }
        }
    }

    override fun createPresenter(): ModifyPasswordPersenter = ModifyPasswordPersenter(this)
    override fun getViewBinding(): ViewBinding {
        return mBinding
    }

    override fun getCodeImage(image: CodeImage) {
        this.iamge=image
        var type=image.base64.split(",")
        val imgBase= Base64.decode(type[type.size-1], Base64.DEFAULT)
        mBinding.ivIconCode.setImageBitmap(BitmapFactory.decodeByteArray(imgBase,0,imgBase.size))
    }

    override fun getCode() {
        countDownTime()
    }

    override fun verifyPhone(verifyPhone: VerifyPhone) {
        this.verifyPhone=verifyPhone
        mBinding.llPhone.visibility=View.GONE
        mBinding.llPassword.visibility=View.VISIBLE
        mBinding.tvCodeRz.setTextColor(resources.getColor(R.color.text_333333))
        mBinding.tvNewPassdword.setTextColor(resources.getColor(R.color.text_FF6C2EA5))
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

}