package com.example.xck.ui.person.activity

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.blankj.utilcode.util.StringUtils
import com.example.xck.App
import com.example.xck.MainActivity
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.Login
import com.example.xck.common.Constants
import com.example.xck.ui.person.mvp.contract.LoginContract
import com.example.xck.ui.person.mvp.persenter.LoginPersenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseMvpActivity<LoginPersenter>(),LoginContract.View {



    override fun createPresenter(): LoginPersenter =LoginPersenter(this)
    override fun getActivityLayoutId(): Int = R.layout.activity_login

    override fun initData() {
        (application as App).addActivity(this)
        if (!Constants.isShow()){
            ivHide.setImageResource(R.mipmap.hide)
            etPassword.transformationMethod=PasswordTransformationMethod.getInstance()
        }else{
            ivHide.setImageResource(R.mipmap.look)
            etPassword.transformationMethod=HideReturnsTransformationMethod.getInstance()
        }
    }

    override fun initEvent() {
        tvRegister.setOnClickListener {
            var intent=Intent(this,RegisterActivity::class.java)
            this.startActivity(intent)
        }
        tvForgetPw.setOnClickListener {
            var intent=Intent(this,ModifyPasswordActivity::class.java)
            intent.putExtra("type",1)
            this.startActivity(intent)
        }
        tvToLogin.setOnClickListener {
                getPresenter().login(etPhone.text.toString(),etPassword.text.toString())
        }
        ivDelete.setOnClickListener {
            etPassword.setText("")
        }
        ivHide.setOnClickListener {
           if (!Constants.isShow()){
               Constants.setShow(true)
               ivHide.setImageResource(R.mipmap.look)
               etPassword.transformationMethod=HideReturnsTransformationMethod.getInstance()
               val pos= etPassword.getText().length
               etPassword.setSelection(pos)
           }else{
               Constants.setShow(false)
               ivHide.setImageResource(R.mipmap.hide)
               etPassword.transformationMethod=PasswordTransformationMethod.getInstance()
               val pos= etPassword.getText().length
               etPassword.setSelection(pos)
           }
        }
        etPassword.addTextChangedListener {
            if (StringUtils.isEmpty(etPassword.text.toString())){
                ivDelete.visibility= View.GONE
            }else if (ivDelete.visibility == View.GONE){
                ivDelete.visibility=View.VISIBLE
            }
        }
    }

    override fun login(login: Login) {
        showToast("登录成功")
        Constants.putPersonal(login.user_info)
        Constants.putToken(login.access_token)
        Constants.login()
//        startActivity(Intent(this, MainActivity::class.java))
        (application as App).cleanActivity()

    }
    override fun onDestroy() {
        super.onDestroy()
        (application as App).deleteActivity(this)
    }
}