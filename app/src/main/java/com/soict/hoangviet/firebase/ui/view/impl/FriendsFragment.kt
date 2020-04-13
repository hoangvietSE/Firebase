package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.os.Bundle
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.UserAdapter
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.presenter.FriendsPresenter
import com.soict.hoangviet.firebase.ui.view.FriendsView
import kotlinx.android.synthetic.main.fragment_friends.*
import javax.inject.Inject

class FriendsFragment : BaseFragment(), FriendsView {
    override val mLayoutRes: Int
        get() = R.layout.fragment_friends

    @Inject
    lateinit var mPresenter: FriendsPresenter
    private var mUserAdpter: UserAdapter? = null

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
            recycler_view_user.setAdapter(mUserAdpter!!)
            recycler_view_user.setOnRefreshListener {
            }
            recycler_view_user.setLoadingMoreListener {
            }
            recycler_view_user.setOnItemClickListener { parent, viewType, view, position ->
                val user = mUserAdpter?.getItemPosition(position!!, User::class.java)
                startActivity(
                    Intent(
                        parentActivity?.let { it },
                        MessageActivity::class.java
                    ).apply {
                        putExtra(MessageActivity.EXTRA_USER_ID, user?.id)
                        putExtra(MessageActivity.TOKEN_DEVICE_ID, user?.deviceToken)
                    })
            }
            mUserAdpter?.addModels(mListUser, false)
            recycler_view_user.setLinearLayoutManager()
            recycler_view_user.disableRefreshing()
        }
    }

    override fun initView() {
        mPresenter.onAttach(this)
        getAllUser()
    }

    private fun getAllUser() {
        mPresenter.getAllUser()
    }

    override fun initListener() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }
}