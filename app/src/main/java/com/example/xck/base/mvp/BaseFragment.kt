package com.example.xck.base.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
/*import kotlinx.android.synthetic.main.layout_tool_bar.**/


/**
 * BaseFragment
 * (。・∀・)ノ
 * Describe：
 * Created by 雷小星🍀 on 2017/11/1 9:29.
 */
abstract class BaseFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val arguments = arguments
        handlerArguments(arguments)
    }

    open fun handlerArguments(arguments: Bundle?){

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        (activity as AppCompatActivity).setSupportActionBar(toolBar)
        init(view)
    }

    /**
     * 获取Fragment的布局资源文件id
     */
    protected abstract fun getFragmentLayoutId(): Int

    /**
     * 初始化
     */
    protected abstract fun init(view: View?)


}