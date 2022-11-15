package com.example.xck.ui.home.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.Capitalist
import com.example.xck.common.Constants
import com.example.xck.ui.home.adapter.HomeTypeAdapter
import com.example.xck.ui.home.mvp.contract.InvestorDetailContract
import com.example.xck.ui.home.mvp.contract.ProjectDetailContract
import com.example.xck.ui.home.mvp.persenter.InvestorDetailPersenter
import com.example.xck.ui.home.mvp.persenter.ProjectDetailPersenter
import kotlinx.android.synthetic.main.activity_investor_detail.*
import kotlinx.android.synthetic.main.ic_title.*

/**
 *   author ： xiaogf
 *   time    ： 2022/10/13
 *   describe    ：
 */
class InvestorDetailActivity :BaseMvpActivity<InvestorDetailPersenter>(),InvestorDetailContract.View{
    override fun getActivityLayoutId(): Int = R.layout.activity_investor_detail

    override fun initData() {
        tvTilte.text="详情"
        getPresenter().getInverstorDetail(Constants.getToken(),intent.getIntExtra("capitalist_id",0))
    }

    override fun initEvent() {
    }

    override fun createPresenter(): InvestorDetailPersenter = InvestorDetailPersenter(this)
    override fun getInverstorDetail(capitalist: Capitalist) {
        icLoading.visibility=View.GONE
        var inverstor=""
        var address=""
        var datas:ArrayList<Capitalist.AttrListBean> =ArrayList()
        for (i in 0 until capitalist.attr_list.size){
            if (capitalist.attr_list[i].attr_parent_id==3){
                inverstor+=capitalist.attr_list[i].attr_name+"  "
            }else if (capitalist.attr_list[i].attr_parent_id==2){
                address+=capitalist.attr_list[i].attr_name+"  "
            }else if (capitalist.attr_list[i].attr_parent_id==1){
                datas.add(capitalist.attr_list[i])
            }
        }

        tvName.text=capitalist.contact_name
        tvCompany.text=capitalist.capitalist_name+"|"+capitalist.position
        tvOrgan.text=capitalist.introduction
        rcType.visibility= View.VISIBLE
        var layoutManager= LinearLayoutManager(this)
        layoutManager.orientation= LinearLayoutManager.HORIZONTAL
        rcType.layoutManager=layoutManager
        var adapter= HomeTypeAdapter()
        rcType.adapter=adapter
        adapter.data=datas
        tvCase.text="投资案例:"+capitalist.cases
        tvMoeny.text=capitalist.single_amount
        tvInverstor.text=inverstor
        tvAddress.text=address
    }

}