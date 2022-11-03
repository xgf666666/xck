package com.example.xck.ui.home


import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.bean.Capitalist
import com.example.xck.bean.Project
import com.example.xck.common.Constants
import com.example.xck.dialog.SelectDialog
import com.example.xck.ui.home.activity.InvestorDetailActivity
import com.example.xck.ui.home.activity.ProjectDetailActivity
import com.example.xck.ui.home.adapter.HomeAdapter
import com.example.xck.ui.home.adapter.HomeProjectAdapter
import com.example.xck.ui.home.mvp.contract.HomeContract
import com.example.xck.ui.home.mvp.persenter.HomePersenter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseMvpFragment<HomePersenter>(),HomeContract.View,
    SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private var homeAdapter: HomeAdapter? =null
    private var homeProjectAdapter: HomeProjectAdapter? =null
    private var  isProject=false
    private var page=1
    override fun getFragmentLayoutId(): Int = R.layout.fragment_home

    @SuppressLint("SuspiciousIndentation")
    override fun init(view: View?) {
        rvHome.layoutManager=LinearLayoutManager(this.context)
        if (isProject){
            homeProjectAdapter= HomeProjectAdapter()
            rvHome.adapter=homeProjectAdapter
            homeProjectAdapter?.loadMoreModule?.setOnLoadMoreListener(this)
            homeProjectAdapter?.loadMoreModule?.isAutoLoadMore=true
            homeProjectAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent=Intent(this.context, ProjectDetailActivity::class.java)
                this.startActivity(intent)
            }
           showLoadingDialog()
            getPresenter().getProjects(Constants.getToken(),page,10)

        }else{
            homeAdapter= HomeAdapter()
            rvHome.adapter=homeAdapter
            homeAdapter?.loadMoreModule?.setOnLoadMoreListener(this)
            homeAdapter?.loadMoreModule?.isAutoLoadMore=true
            homeAdapter?.setOnItemClickListener { adapter, view, position ->
                var intent=Intent(this.context,InvestorDetailActivity::class.java)
                this.startActivity(intent)
            }
            showLoadingDialog()
            getPresenter().getCapitalists(Constants.getToken())
        }
        srHome.setOnRefreshListener(this)

        iv_screen.setOnClickListener {
            var selectDialog=SelectDialog(this.requireContext())
            selectDialog.show()
        }
    }

    override fun createPresenter(): HomePersenter =HomePersenter(this)


    override fun onRefresh() {
        page==1
        if (isProject){
            getPresenter().getProjects(Constants.getToken(),page,10)
        }else{
            getPresenter().getCapitalists(Constants.getToken())
        }

    }

    override fun onLoadMore() {
        page++
        if (isProject){
            getPresenter().getProjects(Constants.getToken(),page,10)
        }else{
//            getPresenter().getCapitalists(Constants.getToken())
            homeAdapter?.loadMoreModule?.loadMoreComplete()
            homeAdapter?.loadMoreModule?.loadMoreEnd(true)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun getProject(projects: List<Project>) {
        if (srHome.isRefreshing){
            srHome.isRefreshing=false
            homeProjectAdapter?.data=projects.toMutableList()
        }else if(homeProjectAdapter?.loadMoreModule?.isLoading==true){
            homeProjectAdapter?.addData(projects)
            homeProjectAdapter?.loadMoreModule?.loadMoreComplete()
        }else{
            homeProjectAdapter?.data=projects.toMutableList()
        }
        if (projects.size<10){
            homeProjectAdapter?.loadMoreModule?.loadMoreEnd(true)
        }
        homeProjectAdapter?.notifyDataSetChanged()

    }

    override fun getCapitalist(projects: List<Capitalist>) {
        if (srHome.isRefreshing){
            srHome.isRefreshing=false
            homeAdapter?.data=projects.toMutableList()
        }else if(homeAdapter?.loadMoreModule?.isLoading==true){
            if (projects.size==0){
                homeAdapter?.loadMoreModule?.loadMoreEnd(true)
            }
            homeAdapter?.addData(projects)
        }else{
            homeAdapter?.data=projects.toMutableList()
        }
        homeAdapter?.notifyDataSetChanged()
    }

}