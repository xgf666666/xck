package com.example.xck.ui.person

import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.bean.Login
import com.example.xck.common.Constants
import com.example.xck.ui.person.activity.*
import com.example.xck.ui.person.mvp.contract.PersonContract
import com.example.xck.ui.person.mvp.persenter.PersonPersenter
import com.example.xck.utils.changeKm
import com.example.xck.utils.loadImag
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.activity_prepare_login.*
import kotlinx.android.synthetic.main.fragment_person.*

/**
 *   author ： xiaogf
 *   time    ： 2022/10/8
 */
class PersonFragment:BaseMvpFragment<PersonPersenter>(),PersonContract.View {
    var isHide=false;
    var message:String=""
    override fun getFragmentLayoutId(): Int = R.layout.fragment_person
    override fun init(view: View?) {
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
                }else{
                    ToastUtils.showShort(message)
                    return@setOnClickListener
                }

            }else if (Constants.getPersonal().user_type_select==2){
                if (Constants.getPersonal().proof_status==2){
                    intent = Intent(context, InvestorMessageEditActivity::class.java)
                }else{
                    ToastUtils.showShort(message)
                    return@setOnClickListener
                }
            }
            intent?.putExtra("complete_status",Constants.getPersonal().complete_status)
            context?.startActivity(intent)
        }
        ll_identifyPw.setOnClickListener {
            startActivity(Intent(context,ModifyPasswordActivity::class.java))
        }
        llLoginOut.setOnClickListener {
            Constants.loginOut()
            Constants.putToken("")
            Constants.putPersonal(Login.UserInfoBean())
            startActivity(Intent(context,LoginActivity::class.java))
            EMClient.getInstance().logout(true)
            ToastUtils.showShort("退出登录!")
        }
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

}



