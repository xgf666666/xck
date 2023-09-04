package com.example.xck.ui.home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.example.xck.databinding.FragmentHomeBinding
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
import com.hyphenate.easeui.constants.EaseCommom



class HomeFragment : BaseMvpFragment<HomePersenter,FragmentHomeBinding>(),HomeContract.View,
    SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private var homeAdapter: HomeAdapter? =null
    private var homeProjectAdapter: HomeProjectAdapter? =null
    private var  isProject=true
    private var page=1
    private var keyword=""
    private var attr=""
    private val selectDialog by lazy {SelectDialog(requireContext()) }


    @SuppressLint("SuspiciousIndentation")
    override fun init(view: View?) {
        mBindingView!!.rvHome.layoutManager=LinearLayoutManager(this.context)
        if (Constants.getPersonal()!=null){
            EaseCommom.getInstance().isProject = Constants.getPersonal().user_type_select == 1
            EaseCommom.getInstance().avatar=Constants.getPersonal().avatar
            if (Constants.getPersonal().user_type_select==1){
                isProject=false
            }else if (Constants.getPersonal().user_type_select==2){
                isProject=true
            }
        }
        homeProjectAdapter= HomeProjectAdapter()
        homeProjectAdapter?.loadMoreModule?.setOnLoadMoreListener(this)
        homeProjectAdapter?.loadMoreModule?.isAutoLoadMore=true
        homeProjectAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent=Intent(this.context, ProjectDetailActivity::class.java)
            intent.putExtra("project_id", homeProjectAdapter!!.data[position].id)
            intent.putExtra("user_id", homeProjectAdapter!!.data[position].user_id)
            this.startActivity(intent)
        }

        homeAdapter= HomeAdapter()
        homeAdapter?.loadMoreModule?.setOnLoadMoreListener(this)
        homeAdapter?.loadMoreModule?.isAutoLoadMore=true
        homeAdapter?.setOnItemClickListener { adapter, view, position ->
            var intent=Intent(this.context,InvestorDetailActivity::class.java)
            intent.putExtra("capitalist_id", homeAdapter!!.data[position].id)
            intent.putExtra("user_id", homeAdapter!!.data[position].user_id)
            this.startActivity(intent)
        }

        if (isProject){
            mBindingView!!.rvHome.adapter=homeProjectAdapter
           showLoadingDialog()
            getPresenter().getProjects(Constants.getToken(),attr,keyword,page,10)
            getPresenter().getBanner("project_banner")
        }else{
            mBindingView!!.rvHome.adapter=homeAdapter
            showLoadingDialog()
            getPresenter().getCapitalists(Constants.getToken(),attr,keyword,page,10)
            getPresenter().getBanner("capitalist_banner")
        }
        mBindingView!!.srHome.setOnRefreshListener(this)
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
        mBindingView!!.ivScreen.setOnClickListener {
            selectDialog.show()
        }
        mBindingView!!.etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            if (Constants.getPersonal()!=null){
                if (Constants.getPersonal().user_type_select==1){
                    isProject=false
                }else if (Constants.getPersonal().user_type_select==2){
                    isProject=true
                }
            }
            page=1
            if (isProject){
                mBindingView!!.rvHome.adapter=homeProjectAdapter
                getPresenter().getProjects(Constants.getToken(),attr,keyword,page,10)
                getPresenter().getBanner("project_banner")
            }else{
                mBindingView!!.rvHome.adapter=homeAdapter
                getPresenter().getCapitalists(Constants.getToken(),attr,keyword,page,10)
                getPresenter().getBanner("capitalist_banner")
            }

        }
    }

    override fun createPresenter(): HomePersenter =HomePersenter(this)


    override fun onRefresh() {
        page=1
        if (isProject){
                homeProjectAdapter?.loadMoreModule?.loadMoreComplete()
            getPresenter().getProjects(Constants.getToken(),attr,keyword,page,10)
        }else{
                homeAdapter?.loadMoreModule?.loadMoreComplete()
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
        if ( mBindingView!!.srHome.isRefreshing){
            mBindingView!!.srHome.isRefreshing=false
            homeProjectAdapter?.data=projects.toMutableList()
        }else if(homeProjectAdapter?.loadMoreModule?.isLoading==true){
            homeProjectAdapter?.addData(projects)
            homeProjectAdapter?.loadMoreModule?.loadMoreComplete()
        }else{
            homeProjectAdapter?.data=projects.toMutableList()
        }
        if (projects.size<10){
            homeProjectAdapter?.loadMoreModule?.loadMoreEnd(false)
        }
        homeProjectAdapter?.notifyDataSetChanged()

    }

    override fun getCapitalist(projects: List<Capitalist>) {
        if ( mBindingView!!.srHome.isRefreshing){
            mBindingView!!.srHome.isRefreshing=false
            homeAdapter?.data=projects.toMutableList()
        }else if(homeAdapter?.loadMoreModule?.isLoading==true){
            homeAdapter?.addData(projects)
            homeAdapter?.loadMoreModule?.loadMoreComplete()
        }else{
            homeAdapter?.data=projects.toMutableList()
        }
        if (projects.size<10){
            homeAdapter?.loadMoreModule?.loadMoreEnd(false)
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
        mBindingView!!.VpHome.adapter=HomeViewPagerAdapter(data)
    }

    override fun getViewBinding(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.bind(
            inflater!!.inflate(
                R.layout.fragment_home,
                null,
                false
            )
        )
    }

}