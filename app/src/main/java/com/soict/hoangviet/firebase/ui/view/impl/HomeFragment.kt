package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.os.Bundle
import com.soict.hoangviet.baseproject.extension.hasNetworkConnection
import com.soict.hoangviet.baseproject.extension.toast
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.HomeUserChatsAdapter
import com.soict.hoangviet.firebase.data.network.response.HomeResponse
import com.soict.hoangviet.firebase.eventbus.NetworkEvent
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.ui.presenter.HomePresenter
import com.soict.hoangviet.firebase.ui.view.HomeView
import com.soict.hoangviet.firebase.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class HomeFragment : BaseFragment(), HomeView {
    override val mLayoutRes: Int
        get() = R.layout.fragment_home

    @Inject
    lateinit var mPresenter: HomePresenter

    companion object {
        fun getInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    private var mHomeUserChatsAdapter: HomeUserChatsAdapter? = null

    override fun initView() {
        mPresenter.onAttach(this)
        EventBus.getDefault().register(this)
        checkNetwork()
        getAllChatUsers()
    }

    private fun checkNetwork() {
        if (requireActivity().hasNetworkConnection()) tv_no_internet.gone() else tv_no_internet.visible()
    }

    private fun getAllChatUsers() {
        mPresenter.getAllChatUsers()
    }

    override fun initListener() {

    }

    override fun showAllChatUsers(mListUserChat: ArrayList<HomeResponse>) {
        context?.let {
            mHomeUserChatsAdapter = HomeUserChatsAdapter(it)
            recycler_view_user.setAdapter(mHomeUserChatsAdapter!!)
            recycler_view_user.setLoadingMoreListener {

            }
            recycler_view_user.setOnRefreshListener {

            }
            recycler_view_user.setOnItemClickListener { parent, viewType, view, position ->
                if (requireActivity().hasNetworkConnection()) {
                    val user =
                        mHomeUserChatsAdapter?.getItemPosition(position!!, HomeResponse::class.java)
                    startActivity(
                        Intent(
                            parentActivity?.let { it },
                            MessageActivity::class.java
                        ).apply {
                            putExtra(MessageActivity.EXTRA_USER_ID, user?.user?.id)
                            putExtra(MessageActivity.TOKEN_DEVICE_ID, user?.user?.deviceToken)
                            LogUtil.d(user?.user?.fullname!!)
                            LogUtil.d(user?.user?.deviceToken!!)
                        })
                } else {
                    toast(getString(R.string.splash_message_alert))
                }
            }
            mHomeUserChatsAdapter?.addModels(mListUserChat, false)
            recycler_view_user.setLinearLayoutManager()
            recycler_view_user.disableRefreshing()
        }
    }

    override fun notifyChange(position: Int) {
        mHomeUserChatsAdapter?.notifyItemChanged(position)
    }

    override fun onDestroy() {
        super.onDestroy()
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