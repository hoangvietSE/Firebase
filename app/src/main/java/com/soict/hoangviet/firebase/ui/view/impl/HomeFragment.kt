package com.soict.hoangviet.firebase.ui.view.impl

import android.os.Bundle
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.ui.interactor.impl.HomeInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.HomePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.HomePresenterImpl
import com.soict.hoangviet.firebase.ui.view.HomeView

class HomeFragment : BaseFragment<HomePresenter>(), HomeView {
    override val mLayoutRes: Int
        get() = R.layout.fragment_home

    companion object {
        fun getInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    override fun getPresenter(): HomePresenter {
        return HomePresenterImpl(this, HomeInteractorImpl())
    }

    override fun initView() {

    }

    override fun initListener() {

    }
}