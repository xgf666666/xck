package com.example.xck.base.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/*import kotlinx.android.synthetic.main.layout_tool_bar.**/


/**
 * BaseFragment
 * (。・∀・)ノ
 * Describe：
 * Created by 雷小星🍀 on 2017/11/1 9:29.
 */
abstract class BaseFragment<V : ViewBinding> : Fragment() {
    var mBindingView: V?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val arguments = arguments
        handlerArguments(arguments)
    }

    open fun handlerArguments(arguments: Bundle?){

    }
    abstract fun getViewBinding(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): V
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View
        mBindingView= getViewBinding(inflater, container, savedInstanceState)
        if (mBindingView!=null){
            view= mBindingView!!.root
            return view
        }else{
            return super.onCreateView(inflater, container, savedInstanceState);
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        (activity as AppCompatActivity).setSupportActionBar(toolBar)
        init(view)
    }


    /**
     * 初始化
     */
    protected abstract fun init(view: View?)


}