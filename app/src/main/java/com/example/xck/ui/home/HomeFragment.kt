package com.example.xck.ui.home


import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.dialog.SelectDialog
import com.example.xck.ui.home.activity.InvestorDetailActivity
import com.example.xck.ui.home.adapter.HomeAdapter
import com.example.xck.ui.home.mvp.contract.HomeContract
import com.example.xck.ui.home.mvp.persenter.HomePersenter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseMvpFragment<HomePersenter>(),HomeContract.View,
    SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private var homeAdapter: HomeAdapter? =null
    private var array=ArrayList<String>()
    override fun getFragmentLayoutId(): Int = R.layout.fragment_home

    override fun init(view: View?) {
        rvHome.layoutManager=LinearLayoutManager(this.context)
        homeAdapter= HomeAdapter()
        rvHome.adapter=homeAdapter
        homeAdapter?.loadMoreModule?.setOnLoadMoreListener(this)
        homeAdapter?.loadMoreModule?.isAutoLoadMore=true
        srHome.setOnRefreshListener(this)
        setData(0)
        homeAdapter?.setOnItemClickListener { adapter, view, position ->
//            var intent=Intent(this.context,ProjectDetailActivity::class.java)
            var intent=Intent(this.context,InvestorDetailActivity::class.java)
            this.startActivity(intent)
        }
        iv_screen.setOnClickListener {
            var selectDialog=SelectDialog(this.requireContext())
            selectDialog.show()
        }
    }
    fun setData(type:Int ){
        if (type==0){
            for (  i in 1..10){
                array.add(""+ i)
            }
        }else{
            array.clear()
            for (  i in 1..10){
                array.add(""+ i)
            }
        }

        homeAdapter?.data=array
    }

    override fun createPresenter(): HomePersenter =HomePersenter(this)


    override fun onRefresh() {
        setData(1)
        srHome.isRefreshing = false
    }

    override fun onLoadMore() {
        setData(0)
        homeAdapter?.loadMoreModule?.loadMoreComplete()
    }

}