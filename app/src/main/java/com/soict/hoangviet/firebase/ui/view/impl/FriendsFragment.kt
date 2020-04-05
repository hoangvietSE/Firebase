package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.RecyclerViewAdapter
import com.soict.hoangviet.firebase.adapter.UserAdapter
import com.soict.hoangviet.firebase.builder.DatabaseFirebase
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.presenter.FriendsPresenter
import com.soict.hoangviet.firebase.ui.view.FriendsView
import kotlinx.android.synthetic.main.fragment_friends.*
import javax.inject.Inject

class FriendsFragment : BaseFragment(), FriendsView,
        RecyclerViewAdapter.OnItemClickListener {
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
            recycler_view_user.setOnItemClickListener(this)
            mUserAdpter?.addModels(mListUser, false)
            recycler_view_user.setLinearLayoutManager()
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

    override fun onItemClick(parent: ViewGroup, viewType: Int, view: View, position: Int?) {
        val user = mUserAdpter?.getItemPosition(position!!, User::class.java)
        startActivity(Intent(parentActivity?.let { it }, MessageActivity::class.java).apply {
            putExtra(MessageActivity.EXTRA_USER_ID, user?.id)
            putExtra(MessageActivity.TOKEN_DEVICE_ID, user?.deviceToken)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }
}