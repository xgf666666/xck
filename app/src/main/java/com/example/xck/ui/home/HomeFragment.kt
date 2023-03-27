package com.example.xck.ui.home


import android.annotation.SuppressLint
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.example.xck.R
import com.example.xck.base.mvp.BaseMvpFragment
import com.example.xck.bean.Banner
import com.example.xck.bean.Capitalist
import com.example.xck.bean.Project
import com.example.xck.bean.Select
import com.example.xck.common.Constants
import com.example.xck.dialog.SelectDialog
import com.example.xck.ui.home.activity.InvestorDetailActivity
import com.example.xck.ui.home.activity.ProjectDetailActivity
import com.example.xck.ui.home.adapter.HomeAdapter
import com.example.xck.ui.home.adapter.HomeProjectAdapter
import com.example.xck.ui.home.adapter.HomeViewPagerAdapter
import com.example.xck.ui.home.mvp.contract.HomeContract
import com.example.xck.ui.home.mvp.persenter.HomePersenter
import com.example.xck.utils.SoftKeyboardUtils
import com.example.xck.utils.loadImag
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseMvpFragment<HomePersenter>(),HomeContract.View,
    SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private var homeAdapter: HomeAdapter? =null
    private var homeProjectAdapter: HomeProjectAdapter? =null
    private var  isProject=true
    private var page=1
    private var keyword=""
    private var attr=""
    private val selectDialog by lazy {SelectDialog(this.requireContext()) }
    override fun getFragmentLayoutId(): Int = R.layout.fragment_home

    @SuppressLint("SuspiciousIndentation")
    override fun init(view: View?) {
        rvHome.layoutManager=LinearLayoutManager(this.context)
        if (Constants.getPersonal()!=null){
            if (Constants.getPersonal().user_type_select==1){
                isProject=false
            }else if (Constants.getPersonal().user_type_select==2){
                isProject=true
            }
        }
        if (isProject){
            homeProjectAdapter= HomeProjectAdapter()
            rvHome.adapter=homeProjectAdapter
            homeProjectAdapter?.loadMoreModule?.setOnLoadMoreListener(this)
            homeProjectAdapter?.loadMoreModule?.isAutoLoadMore=true
            homeProjectAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent=Intent(this.context, ProjectDetailActivity::class.java)
                intent.putExtra("project_id", homeProjectAdapter!!.data[position].id)
                this.startActivity(intent)
            }
           showLoadingDialog()
            getPresenter().getProjects(Constants.getToken(),attr,keyword,page,10)
            getPresenter().getBanner("project_banner")
        }else{
            homeAdapter= HomeAdapter()
            rvHome.adapter=homeAdapter
            homeAdapter?.setOnItemClickListener { adapter, view, position ->
                var intent=Intent(this.context,InvestorDetailActivity::class.java)
                intent.putExtra("capitalist_id", homeAdapter!!.data[position].id)
                this.startActivity(intent)
            }
            showLoadingDialog()
            getPresenter().getCapitalists(Constants.getToken(),attr,keyword,page,10)
            getPresenter().getBanner("capitalist_banner")
        }
        srHome.setOnRefreshListener(this)
        selectDialog.setOnSureListener(object : SelectDialog.OnSureListener{
            override fun onSure(
                filid: ArrayList<Select.ChildrenBean>,
                fanance: ArrayList<Select.ChildrenBean>,
                address: ArrayList<Select.ChildrenBean>
            ) {
                attr=""
                for (i in filid!!.indices){
                    if (attr.isBlank()){
                        attr="${filid[i].id}"
                    }else{
                        attr+=",${filid[i].id}"
                    }
                }
                for (i in fanance!!.indices){
                    if (attr.isBlank()){
                        attr="${fanance[i].id}"
                    }else{
                        attr+=",${fanance[i].id}"
                    }
                }

                for (i in address!!.indices){
                    if (attr.isBlank()){
                        attr="${address[i].id}"
                    }else{
                        attr+=",${address[i].id}"
                    }
                }
                if (isProject){
                    getPresenter().getProjects(Constants.getToken(),attr,keyword,page,10)
                }else{
                    getPresenter().getCapitalists(Constants.getToken(),attr,keyword,page,10)
                }
            }

        })
        iv_screen.setOnClickListener {
            selectDialog.show()
        }
        et_search.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            page==1
            keyword=v.text.toString()
            if (SoftKeyboardUtils.isSoftShowing(activity)){
                SoftKeyboardUtils.hideSoftKeyboard(activity)
            }
            if (isProject){
                getPresenter().getProjects(Constants.getToken(),attr,keyword,page,10)
            }else{
                getPresenter().getCapitalists(Constants.getToken(),attr,keyword,page,10)
            }
             true
        })
    }

    override fun createPresenter(): HomePersenter =HomePersenter(this)


    override fun onRefresh() {
        page==1
        if (isProject){
            getPresenter().getProjects(Constants.getToken(),attr,keyword,page,10)
        }else{
            getPresenter().getCapitalists(Constants.getToken(),attr,keyword,page,10)
        }

    }

    override fun onLoadMore() {
        page++
        if (isProject){
            getPresenter().getProjects(Constants.getToken(),attr,keyword,page,10)
        }else{
            getPresenter().getCapitalists(Constants.getToken(),attr,keyword,page,10)
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
            homeAdapter?.addData(projects)
            homeAdapter?.loadMoreModule?.loadMoreComplete()
        }else{
            homeAdapter?.data=projects.toMutableList()
        }
        if (projects.size<10){
            homeAdapter?.loadMoreModule?.loadMoreEnd(true)
        }

        homeAdapter?.notifyDataSetChanged()
    }

    override fun getBanner(banner: Banner) {
        var data= mutableListOf<ImageView>()
        banner.banner_url.forEach {
            var image=ImageView(activity)
            image.scaleType=ImageView.ScaleType.FIT_XY
            image.loadImag(it)
            data.add(image)
        }
        VpHome.adapter=HomeViewPagerAdapter(data)
    }

}