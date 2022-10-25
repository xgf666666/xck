package com.example.xck.ui.person

import android.content.Intent
import android.view.View
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.ui.person.activity.InvestorMessageEditActivity
import com.example.xck.ui.person.activity.PrepareLoginActivity
import com.example.xck.ui.person.mvp.contract.PersonContract
import com.example.xck.ui.person.mvp.persenter.PersonPersenter
import com.example.xck.ui.person.activity.PrepareRoleIdentifyActivity
import com.example.xck.ui.person.activity.ProjectMessageEditActivity
import kotlinx.android.synthetic.main.fragment_person.*

/**
 *   author ： xiaogf
 *   time    ： 2022/10/8
 */
class PersonFragment:BaseMvpFragment<PersonPersenter>(),PersonContract.View {
    override fun getFragmentLayoutId(): Int = R.layout.fragment_person
    override fun init(view: View?) {
        iv_person.setOnClickListener {
            val intent = Intent(context, PrepareLoginActivity::class.java)
            context?.startActivity(intent)


        }
        llRoleIdentity.setOnClickListener {
            val intent = Intent(context, PrepareRoleIdentifyActivity::class.java)
            context?.startActivity(intent)
        }
        llMessage.setOnClickListener {
             val intent = Intent(context, ProjectMessageEditActivity::class.java)
//             val intent = Intent(context, InvestorMessageEditActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun createPresenter(): PersonPersenter = PersonPersenter(this)

}


