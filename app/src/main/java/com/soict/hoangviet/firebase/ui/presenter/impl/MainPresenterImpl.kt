package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.ui.interactor.MainInteractor
import com.soict.hoangviet.firebase.ui.presenter.MainPresenter
import com.soict.hoangviet.firebase.ui.view.MainView

class MainPresenterImpl(mView: MainView, mInteractor: MainInteractor) : BasePresenterImpl<MainView, MainInteractor>(mView, mInteractor), MainPresenter {
}