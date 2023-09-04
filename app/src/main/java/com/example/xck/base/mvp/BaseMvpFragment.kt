package com.example.xck.base.mvp

import androidx.viewbinding.ViewBinding
import com.example.xck.base.mvp.contract.BaseMvpView
import com.example.xck.base.mvp.presenter.BaseMvpPresenter
import com.example.xck.base.mvp.presenter.BasePersenter
import org.jetbrains.annotations.NotNull


/**
 * BaseMvpFragment
 * (。・∀・)ノ
 * Describe：
 * Created by 雷小星🍀 on 2017/11/1 9:53.
 */
abstract class BaseMvpFragment< P : BasePersenter,V : ViewBinding> : BaseMvpViewFragment<V>() {

    private var presenter: P? = null

    /**
     * 创建P层
     *
     * @return P层对象
     */
    @NotNull
    protected abstract fun createPresenter(): P

    /**
     * 获取P层实例
     *
     * @return P层实例
     */
    protected fun getPresenter(): P {
        if (presenter == null) {
            presenter = createPresenter()
        }

/*
        if (this is BaseMvpView) {
            //依附V
            presenter!!.attachView(this as V)
        }
*/
        return presenter as P
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter().detachView()
    }
}