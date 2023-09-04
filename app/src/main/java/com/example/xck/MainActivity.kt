package com.example.xck

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.ContextMenu
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.xck.databinding.ActivityMainBinding
import com.example.xck.ui.home.HomeFragment
import com.example.xck.ui.message.MessageFragment
import com.example.xck.ui.person.PersonFragment
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity


class MainActivity : BaseMvpViewActivity() {
    private var mFragments: Array<Fragment?> = arrayOf(HomeFragment(), null, null)
    private val mMenuItemId = arrayOf(R.id.item0, R.id.item1, R.id.item2)
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun getViewBinding(): ViewBinding {
        return mBinding
    }

    override fun initData() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.ll_fragment, mFragments[0]!!)
            .commit()
//        BottomNavigationViewUtils.disableShiftMode(bottomNavigationView)
        mBinding.bottomNavigationView.setOnItemSelectedListener {
            selectedFragemnt(it.itemId)
            true
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.navigation_main,menu)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initEvent() {
    }
    /**
     * Fragemnt切换
     */
    fun  selectedFragemnt(itemId: Int) {
        Log.i("itemId", "" + itemId)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        for (i in mFragments.indices) {
            val fragment = mFragments[i]
            if (mMenuItemId[i] == itemId) {
                //先检查Fragment是否创建
                if (fragment == null) {
                    when (i) {
                        0 -> mFragments[i] = HomeFragment()
                        1 -> mFragments[i] = MessageFragment()
                        2 -> mFragments[i] = PersonFragment()
                    }
                    //添加到管理类
                    fragmentTransaction.add(R.id.ll_fragment, mFragments[i]!!)
                }
//                mFragments[i]?.arguments = bundle
                fragmentTransaction.show(mFragments[i]!!)
            } else {
                if (fragment != null) {
                    fragmentTransaction.hide(fragment)
                }
            }
        }
        fragmentTransaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==2&&resultCode==Activity.RESULT_OK){
            mFragments[2]?.onActivityResult(requestCode,resultCode,data)
        }
    }


}