package com.example.xck.dialog

import android.app.Dialog
import android.app.slice.Slice
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.example.xck.R
import com.example.xck.bean.Select
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
    private var datas:MutableList<Select>?=null
    private var onSureListener:OnSureListener?=null
    var filid:ArrayList<Select.ChildrenBean>?=null
    var fanance:ArrayList<Select.ChildrenBean>?=null
    var address:ArrayList<Select.ChildrenBean>?=null
    init {
        setContentView(R.layout.dialog_select)
        window!!.setGravity(Gravity.BOTTOM)
        var lp=window!!.attributes
        lp.width= WindowManager.LayoutParams.MATCH_PARENT
        lp.height= WindowManager.LayoutParams.MATCH_PARENT
        window!!.attributes=lp
        initView()
    }
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }*/

    private fun initView() {
        rvSelect.layoutManager=LinearLayoutManager(context)
        selectAdapter=DialogSelectAdapter()
        rvSelect.adapter=selectAdapter
        setData()
        tvReset.setOnClickListener {
            for (i in 0 until datas!!.size){
               for (y in 0 until  datas!![i].children.size){
                   datas!![i].children[y].isSelect=false
               }
            }
            selectAdapter!!.notifyDataSetChanged()
        }
        tvSure.setOnClickListener {
            for ( i in 0 until datas!!.size){
                when(i){
                    0->{
                        filid= ArrayList<Select.ChildrenBean>()
                       for (y in 0 until  datas!![i].children.size){
                           if (datas!![i].children[y].isSelect){
                               filid?.add(datas!![i].children[y])
                           }
                       }

                    }
                    1->{
                        fanance= ArrayList<Select.ChildrenBean>()
                        for (y in 0 until  datas!![i].children.size){
                            if (datas!![i].children[y].isSelect){
                                fanance?.add(datas!![i].children[y])

                            }
                        }

                    }
                    2->{
                        address= ArrayList<Select.ChildrenBean>()
                        for (y in 0 until  datas!![i].children.size){
                            if (datas!![i].children[y].isSelect){
                                address?.add(datas!![i].children[y])

                            }
                        }

                    }


                }

        }
            onSureListener?.onSure(
                filid!!, fanance!!, address!!)
            dismiss()
        }
    }
    fun setData(){
        AppApi.Api().attrList.ui({
            datas=it.data!!
//            selectAdapter?.data=it.data!!
//            selectAdapter?.notifyDataSetChanged()
            setDatas()
        },{
            ToastUtils.showShort(it.message)
        })
    }

    private fun setDatas(){
       for (i in datas!!.indices){
          var childrenBean=Select.ChildrenBean()
           childrenBean.attr_name="全部"
           datas!![i].children.add(0,childrenBean)
       }
        selectAdapter?.setNewInstance(datas)
    }
    public fun setOnSureListener(onSureListener: OnSureListener){
        this.onSureListener=onSureListener
    }
    interface OnSureListener{
        fun onSure(filid:ArrayList<Select.ChildrenBean>,fanance:ArrayList<Select.ChildrenBean>,address:ArrayList<Select.ChildrenBean>)
    }
}