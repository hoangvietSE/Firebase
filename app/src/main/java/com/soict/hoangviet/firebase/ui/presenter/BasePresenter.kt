package com.soict.hoangviet.firebase.ui.presenter

import com.soict.hoangviet.firebase.ui.interactor.BaseInteractor
import com.soict.hoangviet.firebase.ui.view.BaseView

interface BasePresenter<V : BaseView, I : BaseInteractor> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?
}