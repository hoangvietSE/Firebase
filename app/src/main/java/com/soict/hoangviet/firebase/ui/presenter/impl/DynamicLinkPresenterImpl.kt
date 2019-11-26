package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.ui.interactor.BaseInterator
import com.soict.hoangviet.firebase.ui.interactor.DynamicLinkInteractor
import com.soict.hoangviet.firebase.ui.presenter.DynamicLinkPresenter
import com.soict.hoangviet.firebase.ui.view.BaseView
import com.soict.hoangviet.firebase.ui.view.DynamicLinkView

class DynamicLinkPresenterImpl(mView: DynamicLinkView, mInteractor: DynamicLinkInteractor) :
    BasePresenterImpl<DynamicLinkView, DynamicLinkInteractor>(mView, mInteractor), DynamicLinkPresenter {

}