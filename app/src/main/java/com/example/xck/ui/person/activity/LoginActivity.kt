package com.example.xck.ui.person.activity

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.StringUtils
import com.example.xck.App
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.Login
import com.example.xck.common.Constants
import com.example.xck.databinding.ActivityLoginBinding
import com.example.xck.ui.person.mvp.contract.LoginContract
import com.example.xck.ui.person.mvp.persenter.LoginPersenter


class LoginActivity : BaseMvpActivity<LoginPersenter>(),LoginContract.View {



    override fun createPresenter(): LoginPersenter =LoginPersenter(this)

    override fun initData() {
        (application as App).addActivity(this)
        mBinding.etPhone.setText(Constants.getKeyUserPhone())
        mBinding.etPassword.setText(Constants.getKeyUserPwd())
        if (!Constants.isShow()){
            mBinding.ivHide.setImageResource(R.mipmap.hide)
            mBinding.etPassword.transformationMethod=PasswordTransformationMethod.getInstance()
        }else{
            mBinding.ivHide.setImageResource(R.mipmap.look)
            mBinding.etPassword.transformationMethod=HideReturnsTransformationMethod.getInstance()
        }
    }

    override fun initEvent() {
        mBinding.tvRegister.setOnClickListener {
            var intent=Intent(this,RegisterActivity::class.java)
            this.startActivity(intent)
        }
        mBinding.tvForgetPw.setOnClickListener {
            var intent=Intent(this,ModifyPasswordActivity::class.java)
            intent.putExtra("type",1)
            this.startActivity(intent)
        }
        mBinding.tvToLogin.setOnClickListener {
                getPresenter().login(mBinding.etPhone.text.toString(),mBinding.etPassword.text.toString())
        }
        mBinding.ivDelete.setOnClickListener {
            mBinding.etPassword.setText("")
        }
        mBinding.ivHide.setOnClickListener {
           if (!Constants.isShow()){
               Constants.setShow(true)
               mBinding.ivHide.setImageResource(R.mipmap.look)
               mBinding.etPassword.transformationMethod=HideReturnsTransformationMethod.getInstance()
               val pos= mBinding.etPassword.getText().length
               mBinding.etPassword.setSelection(pos)
           }else{
               Constants.setShow(false)
               mBinding.ivHide.setImageResource(R.mipmap.hide)
               mBinding.etPassword.transformationMethod=PasswordTransformationMethod.getInstance()
               val pos= mBinding.etPassword.getText().length
               mBinding.etPassword.setSelection(pos)
           }
        }
        mBinding.etPassword.addTextChangedListener {
            if (StringUtils.isEmpty(mBinding.etPassword.text.toString())){
                mBinding.ivDelete.visibility= View.GONE
            }else if (mBinding.ivDelete.visibility == View.GONE){
                mBinding.ivDelete.visibility=View.VISIBLE
            }
        }
    }

    override fun login(login: Login) {

//        showToast("登录成功")
        Constants.putPersonal(login.user_info)
        Constants.putToken(login.access_token)
        Constants.login()
//        startActivity(Intent(this, MainActivity::class.java))
//        (application as App).cleanActivity()

    }
    override fun onDestroy() {
        super.onDestroy()
        (application as App).deleteActivity(this)
    }
    private val mBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun getViewBinding(): ViewBinding {
        return mBinding
    }
}