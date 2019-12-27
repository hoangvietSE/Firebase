package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.BaseRecyclerView
import com.soict.hoangviet.firebase.adapter.EndlessLoadingRecyclerViewAdapter
import com.soict.hoangviet.firebase.adapter.RecyclerViewAdapter
import com.soict.hoangviet.firebase.adapter.UserAdapter
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.impl.FriendsInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.FriendsPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.FriendsPresenterImpl
import com.soict.hoangviet.firebase.ui.view.FriendsView
import kotlinx.android.synthetic.main.fragment_friends.*

class FriendsFragment : BaseFragment<FriendsPresenter>(), FriendsView, EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        RecyclerViewAdapter.OnItemClickListener, BaseRecyclerView.BaseSwipeRefreshListener {
    override val mLayoutRes: Int
        get() = R.layout.fragment_friends

    private var mUserAdpter: UserAdapter? = null
    private var mListUser: ArrayList<User> = arrayListOf()
    private lateinit var userReference: DatabaseReference
    private var userListener: ValueEventListener? = null

    companion object {
        fun getInstance(): FriendsFragment {
            val userFragment = FriendsFragment()
            val bundle = Bundle()
            userFragment.arguments = bundle
            return userFragment
        }
    }

    override fun getPresenter(): FriendsPresenter {
        return FriendsPresenterImpl(this, FriendsInteractorImpl())
    }

    override fun initView() {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        userReference = FirebaseDatabase.getInstance().getReference("Users")
        userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mListUser.clear()
                for (snapshot in dataSnapshot.children) {
                    // Get Post object and use the values to update the UI
                    val user = snapshot.getValue(User::class.java)
                    // [START_EXCLUDE]
                    if (!user?.id?.equals(currentUser?.uid)!!) {
                        mListUser.add(user)
                    }
                    // [END_EXCLUDE]
                }
                initAdapter()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "loadPost:onCancelled", databaseError.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        userReference.addValueEventListener(userListener as ValueEventListener)
    }

    private fun initAdapter() {
        context?.let {
            mUserAdpter = UserAdapter(it)
            recycler_view_user.setAdapter(mUserAdpter!!)
            recycler_view_user.setLoadingMoreListner(this)
            recycler_view_user.setOnItemClickListener(this)
            recycler_view_user.setOnRefreshingListener(this)
            mUserAdpter?.addModels(mListUser, false)
            recycler_view_user.setLinearLayoutManager()
        }
    }

    override fun initListener() {
    }

    override fun onLoadMore() {
    }

    override fun onItemClick(parent: ViewGroup, viewType: Int, view: View, position: Int?) {
        val user = mUserAdpter?.getItemPosition(position!!, User::class.java)
        startActivity(Intent(parentActivity?.let { it }, MessageActivity::class.java).apply {
            putExtra(MessageActivity.EXTRA_USER_ID, user?.id)
        })
    }

    override fun onSwipeRefresh() {
    }
}