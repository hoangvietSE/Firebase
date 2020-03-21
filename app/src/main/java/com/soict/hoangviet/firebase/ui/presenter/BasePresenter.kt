package com.soict.hoangviet.firebase.ui.presenter

import com.soict.hoangviet.firebase.ui.interactor.BaseInterator
import com.soict.hoangviet.firebase.ui.view.BaseView

interface BasePresenter<V : BaseView, I : BaseInterator> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?
}