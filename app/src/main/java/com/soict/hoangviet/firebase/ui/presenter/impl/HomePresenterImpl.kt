package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.ui.interactor.HomeInteractor
import com.soict.hoangviet.firebase.ui.presenter.HomePresenter
import com.soict.hoangviet.firebase.ui.view.HomeView

class HomePresenterImpl(mView: HomeView, mInteractor: HomeInteractor) :
        BasePresenterImpl<HomeView, HomeInteractor>(mView, mInteractor), HomePresenter {
}