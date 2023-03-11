package com.example.xck.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

/**
 *   author ： xiaogf
 *   time    ： 2023/3/11
 *   describe    ：
 */
 class HomeViewPagerAdapter(violist: MutableList<ImageView>) : PagerAdapter() {

    private var violist: MutableList<ImageView>? = violist
    /* fun setViewList(violist: MutableList<ImageView>){
         this.violist=violist
     }*/
    override fun getCount(): Int = violist?.size!!

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(violist?.get(position));
        return violist?.get(position)!!
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(violist?.get(position)!!)
    }
}