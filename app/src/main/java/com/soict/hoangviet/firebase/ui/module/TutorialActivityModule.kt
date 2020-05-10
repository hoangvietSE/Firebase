package com.soict.hoangviet.firebase.ui.module;

import com.soict.hoangviet.firebase.ui.interactor.TutorialInteractor;
import com.soict.hoangviet.firebase.ui.interactor.impl.TutorialInteractorImpl;
import com.soict.hoangviet.firebase.ui.presenter.TutorialPresenter;
import com.soict.hoangviet.firebase.ui.presenter.impl.TutorialPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
class TutorialActivityModule {
    @Provides
    internal fun provideTutorialInteractor(mInteractor: TutorialInteractorImpl): TutorialInteractor =
        mInteractor

    @Provides
    internal fun provideTutorialPresenter(mPresenter: TutorialPresenterImpl): TutorialPresenter =
        mPresenter
}
