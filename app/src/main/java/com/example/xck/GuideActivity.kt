package com.example.xck

import android.Manifest
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.UtilsTransActivity
import com.example.xck.common.Constants
import com.example.xck.databinding.ActivityGuideBinding
import com.example.xck.databinding.ActivityProjectDetailBinding
import com.example.xck.ui.person.activity.PrepareLoginActivity
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity

/**
 *   author ： xiaogf
 *   time    ： 2022/11/1
 *   describe    ：
 */
class GuideActivity :BaseMvpViewActivity() {
    var handler= Handler(Looper.getMainLooper(),Handler.Callback {
//       if (Constants.isLogin()){
           startActivity(Intent(this,MainActivity::class.java))
//       }else{
//           startActivity(Intent(this,PrepareLoginActivity::class.java))
//       }
        finish()
        true
    })
    private val mBinding by lazy { ActivityGuideBinding.inflate(layoutInflater) }
    override fun getViewBinding(): ViewBinding {
        return mBinding
    }

    override fun initData() {
        showEditAvatarDialog()

    }

    override fun initEvent() {

    }
    private fun showEditAvatarDialog() {
        //请求相机和内存读取权限
        PermissionUtils.permission(
//            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE)
            .callback(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    //被给予权限,调起选图弹窗
                    handler.sendEmptyMessageDelayed(1,2000)
                }
                override fun onDenied() {

                }
            })
            .rationale { utilsTransActivity: UtilsTransActivity, shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest -> shouldRequest.again(true)
            }
            .request()
    }

}


