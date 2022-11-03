package com.example.xck.dialog

import android.app.Dialog
import android.app.slice.Slice
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.example.xck.R
import com.example.xck.common.AppApi
import com.example.xck.extensions.ui
import com.example.xck.ui.home.adapter.DialogSelectAdapter
import com.example.xck.utils.getScreenHeight
import com.example.xck.utils.getScreenWidth
import kotlinx.android.synthetic.main.dialog_select.*

/**
 *   author ： xiaogf
 *   time    ： 2022/10/14
 *   describe    ：
 */
class SelectDialog(context: Context) : Dialog(context){
    private var selectAdapter: DialogSelectAdapter? =null
    private var array=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_select)
        window!!.setGravity(Gravity.BOTTOM)
        var lp=window!!.attributes
        lp.width= getScreenWidth(context)
        lp.height= (getScreenHeight(context)*0.7).toInt()
        window!!.attributes=lp
        initView()
    }

    private fun initView() {
        rvSelect.layoutManager=LinearLayoutManager(context)
        selectAdapter=DialogSelectAdapter()
        rvSelect.adapter=selectAdapter
        setData()
    }
    fun setData(){
        AppApi.Api().attrList.ui({
//            selectAdapter?.data=it.data!!
//            selectAdapter?.notifyDataSetChanged()
            selectAdapter?.setNewData(it.data!!)
        },{
            ToastUtils.showShort(it.message)
        })
    }
/*
*   for (  i in 1..10){
                array.add(""+ i)
            }
            array.clear()
            for (  i in 1..10){
                array.add(""+ i)
            }


        selectAdapter?.data=array*/
}