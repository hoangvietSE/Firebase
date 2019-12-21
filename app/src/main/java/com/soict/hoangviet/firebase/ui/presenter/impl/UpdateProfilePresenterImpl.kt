package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.ui.interactor.UpdateProfileInteractor
import com.soict.hoangviet.firebase.ui.presenter.UpdateProfilePresenter
import com.soict.hoangviet.firebase.ui.view.UpdateProfileView

class UpdateProfilePresenterImpl(mView: UpdateProfileView, mInteractor: UpdateProfileInteractor) :
        BasePresenterImpl<UpdateProfileView, UpdateProfileInteractor>(mView, mInteractor), UpdateProfilePresenter {
}