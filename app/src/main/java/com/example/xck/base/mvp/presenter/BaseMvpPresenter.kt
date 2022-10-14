package com.example.xck.base.mvp.presenter

import com.example.xck.base.mvp.contract.BaseMvpView
import org.jetbrains.annotations.NotNull
import java.lang.ref.WeakReference

/**
 * BaseMvpPresenter
 * (。・∀・)ノ
 * Describe：
 * Created by 雷小星🍀 on 2017/10/31 9:37.
 */

abstract class BaseMvpPresenter<M, V : BaseMvpView>(view:V) :BasePersenter{
    init {
        attachView(view)
    }

    private var vWeakReference: WeakReference<V>? = null
    private var model: M? = null

    /**
     * 接口加载状态
     */
    var isLoading: Boolean? = false

    override fun attachView(view: BaseMvpView) {
        view as V
        vWeakReference = WeakReference(view)
    }


    override fun detachView() {
        if (vWeakReference != null) {
            vWeakReference!!.clear()
            vWeakReference = null
        }
    }

    /**
     * 获取V实例
     */
    protected fun getView(): V? {
        return if (vWeakReference == null) null else vWeakReference!!.get()
    }

    protected fun getModel(): M {
        return model ?: createModel()
    }


    @NotNull
    protected abstract fun createModel(): M
}
