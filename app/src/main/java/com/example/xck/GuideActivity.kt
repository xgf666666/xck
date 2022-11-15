package com.example.xck

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.xck.common.Constants
import com.example.xck.ui.person.activity.PrepareLoginActivity
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity

/**
 *   author ： xiaogf
 *   time    ： 2022/11/1
 *   describe    ：
 */
class GuideActivity :BaseMvpViewActivity() {
    override fun getActivityLayoutId(): Int =R.layout.activity_guide
    var handler= Handler(Looper.getMainLooper(),Handler.Callback {
//       if (Constants.isLogin()){
           startActivity(Intent(this,MainActivity::class.java))
//       }else{
//           startActivity(Intent(this,PrepareLoginActivity::class.java))
//       }
        finish()
        true
    })

    override fun initData() {
        handler.sendEmptyMessageDelayed(1,2000)
    }

    override fun initEvent() {

    }
}


