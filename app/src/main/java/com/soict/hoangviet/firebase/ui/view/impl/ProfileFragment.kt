package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.os.Bundle
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.extension.loadImageUrl
import com.soict.hoangviet.firebase.ui.interactor.impl.ProfileInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.ProfilePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.ProfilePresenterImpl
import com.soict.hoangviet.firebase.ui.view.ProfileView
import kotlinx.android.synthetic.main.fragment_profile.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
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

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun showUserInfo(user: User) {
        mUser = user
        tv_name.text = user.fullname
        row_email.setDetail(user.email)
        row_phone.setDetail(user.phone)
        row_birthday.setDetail(user.birthday)
        row_gender.setDetail(if (user.gender == 0) "Nam" else "Nữ")
        imv_avatar.loadImageUrl(
            context!!,
            user.avatar,
            R.drawable.ic_avatar,
            R.drawable.ic_avatar
        )
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onUpdateInfo(mUser: User) {
        showUserInfo(mUser)
    }


}