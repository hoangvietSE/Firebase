package com.soict.hoangviet.firebase.ui.view.impl

import android.os.Bundle
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.ui.interactor.impl.ProfileInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.ProfilePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.ProfilePresenterImpl
import com.soict.hoangviet.firebase.ui.view.ProfileView

class ProfileFragment : BaseFragment<ProfilePresenter>(), ProfileView {
    override val mLayoutRes: Int
        get() = R.layout.fragment_profile

    companion object {
        fun getInstance(): ProfileFragment {
            val profileFragment = ProfileFragment()
            val bundle = Bundle()
            profileFragment.arguments = bundle
            return profileFragment
        }
    }

    override fun getPresenter(): ProfilePresenter {
        return ProfilePresenterImpl(this, ProfileInteractorImpl())
    }

    override fun initView() {
    }

    override fun initListener() {
    }
}