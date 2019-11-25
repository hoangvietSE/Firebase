package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.ui.interactor.BaseInterator
import com.soict.hoangviet.firebase.ui.presenter.BasePresenter
import com.soict.hoangviet.firebase.ui.view.BaseView
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenterImpl<V : BaseView, I : BaseInterator>(mView: V, mInteractor: I) : BasePresenter {
    protected var mView: V? = mView
    protected var mInterator: I? = mInteractor
    protected var mCompositeDisposable = CompositeDisposable()
    protected val isAttached get() = mView != null

    override fun onAttach() {
        mView?.let {
            it.initView()
        }
    }

    override fun onDetach() {
        mView = null
        mInterator = null
        mCompositeDisposable.clear()
    }
}