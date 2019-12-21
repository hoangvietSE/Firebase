package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.os.Bundle
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.impl.ProfileInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.ProfilePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.ProfilePresenterImpl
import com.soict.hoangviet.firebase.ui.view.ProfileView
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<ProfilePresenter>(), ProfileView {
    override val mLayoutRes: Int
        get() = R.layout.fragment_profile
    private lateinit var mUser: User

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
        mPresenter.getCurrentUser()
    }

    override fun initListener() {
        row_information.setOnClickListener {
            val intent = Intent(context, UpdateProfileActivity::class.java).apply {
                putExtra(UpdateProfileActivity.EXTRA_USER, mUser)
            }
            startActivity(intent)
        }
    }

    override fun showUserInfo(user: User) {
        mUser = user
        tv_name.text = user.fullname
        row_email.setDetail(user.email)
        row_phone.setDetail(user.phone)
    }

}