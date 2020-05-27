package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.os.Bundle
import com.soict.hoangviet.baseproject.extension.hasNetworkConnection
import com.soict.hoangviet.baseproject.extension.launchActivity
import com.soict.hoangviet.baseproject.extension.toast
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.UserAdapter
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.eventbus.NetworkEvent
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.ui.presenter.FriendsPresenter
import com.soict.hoangviet.firebase.ui.view.FriendsView
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.fragment_friends.recycler_view_user
import kotlinx.android.synthetic.main.fragment_friends.tv_no_internet
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class FriendsFragment : BaseFragment(), FriendsView {
    override val mLayoutRes: Int
        get() = R.layout.fragment_friends

    @Inject
    lateinit var mPresenter: FriendsPresenter
    private lateinit var mUserAdpter: UserAdapter

    companion object {
        fun getInstance(): FriendsFragment {
            val userFragment = FriendsFragment()
            val bundle = Bundle()
            userFragment.arguments = bundle
            return userFragment
        }
    }

    override fun initAdapter(mListUser: ArrayList<User>) {
        context?.let {
            mUserAdpter = UserAdapter(it)
            mUserAdpter.setOnWaveClickListener { position ->
                if (requireActivity().hasNetworkConnection()) {
                    val user = mUserAdpter.getItemPosition(position, User::class.java)
                    requireActivity().launchActivity<MessageActivity> {
                        putExtra(MessageActivity.EXTRA_USER_ID, user.id)
                        putExtra(MessageActivity.TOKEN_DEVICE_ID, user.deviceToken)
                        putExtra(MessageActivity.WAVE_HAND, true)
                    }
                } else {
                    toast(getString(R.string.splash_message_alert))
                }

            }
            recycler_view_user.setAdapter(mUserAdpter)
            recycler_view_user.setOnRefreshListener {
            }
            recycler_view_user.setLoadingMoreListener {
            }
            recycler_view_user.setOnItemClickListener { parent, viewType, view, position ->
                if (requireActivity().hasNetworkConnection()) {
                    val user = mUserAdpter.getItemPosition(position!!, User::class.java)
                    requireActivity().launchActivity<MessageActivity> {
                        putExtra(MessageActivity.EXTRA_USER_ID, user.id)
                        putExtra(MessageActivity.TOKEN_DEVICE_ID, user.deviceToken)
                    }
                } else {
                    toast(getString(R.string.splash_message_alert))
                }

            }
            mUserAdpter.addModels(mListUser, false)
            recycler_view_user.setLinearLayoutManager()
            recycler_view_user.disableRefreshing()
        }
    }

    override fun initView() {
        mPresenter.onAttach(this)
        EventBus.getDefault().register(this)
        checkNetwork()
        getAllUser()
    }

    private fun checkNetwork() {
        if (requireActivity().hasNetworkConnection()) tv_no_internet.gone() else tv_no_internet.visible()
    }

    private fun getAllUser() {
        mPresenter.getAllUser()
    }

    override fun initListener() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
        EventBus.getDefault().unregister(this)
    }

    // UI updates must run on MainThread
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEvent(event: NetworkEvent) {
        when {
            event.isNetworkConnection -> tv_no_internet.gone()
            else -> tv_no_internet.visible()
        }
    }
}