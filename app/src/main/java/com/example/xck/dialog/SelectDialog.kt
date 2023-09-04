package com.example.xck.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.example.xck.R
import com.example.xck.bean.Select
import com.example.xck.common.AppApi
import com.example.xck.databinding.DialogSelectBinding
import com.example.xck.extensions.ui
import com.example.xck.ui.home.adapter.DialogSelectAdapter

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
    private val mBinding by lazy { DialogSelectBinding.inflate(layoutInflater) }
    init {
        setContentView(mBinding.root);
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
        mBinding.rvSelect.layoutManager=LinearLayoutManager(context)
        selectAdapter=DialogSelectAdapter()
        mBinding.rvSelect.adapter=selectAdapter
        setData()
        mBinding.tvReset.setOnClickListener {
            for (i in 0 until datas!!.size){
               for (y in 0 until  datas!![i].children.size){
                   datas!![i].children[y].isSelect=false
               }
            }
            selectAdapter!!.notifyDataSetChanged()
        }
        mBinding.tvSure.setOnClickListener {
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