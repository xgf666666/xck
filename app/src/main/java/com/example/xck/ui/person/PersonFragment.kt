package com.example.xck.ui.person

import android.content.Intent
import android.view.View
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.bean.Login
import com.example.xck.common.Constants
import com.example.xck.ui.person.activity.*
import com.example.xck.ui.person.mvp.contract.PersonContract
import com.example.xck.ui.person.mvp.persenter.PersonPersenter
import kotlinx.android.synthetic.main.activity_prepare_login.*
import kotlinx.android.synthetic.main.fragment_person.*
import kotlinx.android.synthetic.main.fragment_person.iv_person

/**
 *   author ： xiaogf
 *   time    ： 2022/10/8
 */
class PersonFragment:BaseMvpFragment<PersonPersenter>(),PersonContract.View {
    var isHide=false;

    override fun getFragmentLayoutId(): Int = R.layout.fragment_person
    override fun init(view: View?) {
        tvToLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            this?.startActivity(intent)
        }
        tvRegister.setOnClickListener {
            startActivity(Intent(context, RegisterActivity::class.java))
        }
        iv_person.setOnClickListener {
            val intent = Intent(context, PrepareLoginActivity::class.java)
            context?.startActivity(intent)
        }
        llRoleIdentity.setOnClickListener {
            val intent = Intent(context, PrepareRoleIdentifyActivity::class.java)
            context?.startActivity(intent)
        }
        llMessage.setOnClickListener {
            var intent:Intent?=null
            if (Constants.getPersonal().user_type_select==1){
                 intent = Intent(context, ProjectMessageEditActivity::class.java)
            }else if (Constants.getPersonal().user_type_select==2){
                 intent = Intent(context, InvestorMessageEditActivity::class.java)
            }
            context?.startActivity(intent)
        }
        ll_identifyPw.setOnClickListener {
            startActivity(Intent(context,ModifyPasswordActivity::class.java))
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
        tvPhone.text=userInfo.mobile_phone
        tvNames.text=userInfo.real_name
        tvQuota.text="额度："+userInfo.quota_num
        when( userInfo.proof_status){
           0->{tvRenz.visibility=View.VISIBLE
               ivRenz.visibility=View.VISIBLE
               llRoleIdentity.isClickable=true
               tvRenz.text="前往认证"
               tvRenMessage.text="需要等认证通过"
               ivRenMessage.visibility=View.GONE
           }
            1->{
                ivRenz.visibility=View.GONE
                tvRenz.visibility=View.VISIBLE
                tvRenz.text="审核中"
                llRoleIdentity.isClickable=false
                ivRenMessage.visibility=View.GONE
                tvRenMessage.text="需要等认证通过"
            }
            2->{
                ivRenz.visibility=View.GONE
                tvRenz.visibility=View.VISIBLE
                tvRenz.text="认证成功"
                llRoleIdentity.isClickable=false
                ivRenMessage.visibility=View.VISIBLE
                tvRenMessage.text="完善的信息让机构了解您"
            }
            3->{
                ivRenz.visibility=View.VISIBLE
                tvRenz.visibility=View.VISIBLE
                tvRenz.text="认证不通过，需重新认证"
                llRoleIdentity.isClickable=true
                tvRenMessage.text="需要等认证通过"
            }
        }
    }

}


