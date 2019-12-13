package com.soict.hoangviet.firebase.ui.view.impl

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
import com.soict.hoangviet.firebase.ui.interactor.impl.UserInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.UserPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.UserPresenterImpl
import com.soict.hoangviet.firebase.ui.view.UserView
import com.soict.hoangviet.firebase.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : BaseFragment<UserPresenter>(), UserView, EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
    RecyclerViewAdapter.OnItemClickListener, BaseRecyclerView.BaseSwipeRefreshListener {
    override val mLayoutRes: Int
        get() = R.layout.fragment_user

    private var mUserAdpter: UserAdapter? = null
    private var mListUser: ArrayList<User> = arrayListOf()
    private lateinit var userReference: DatabaseReference
    private var userListener: ValueEventListener? = null

    companion object {
        fun getInstance(): UserFragment {
            val userFragment = UserFragment()
            val bundle = Bundle()
            userFragment.arguments = bundle
            return userFragment
        }
    }

    override fun getPresenter(): UserPresenter {
        return UserPresenterImpl(this, UserInteractorImpl())
    }

    override fun initView() {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        userReference = FirebaseDatabase.getInstance().getReference("Users")
        userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
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
                Log.w(HomeActivity.TAG, "loadPost:onCancelled", databaseError.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        userReference.addValueEventListener(userListener as ValueEventListener)
    }

    private fun initAdapter() {
        mUserAdpter = UserAdapter(context!!)
        recycler_view_user.setLoadingMoreListner(this)
        recycler_view_user.setOnItemClickListener(this)
        recycler_view_user.setOnRefreshingListener(this)
        recycler_view_user.setAdapter(mUserAdpter!!)
        mUserAdpter?.addModels(mListUser, false)
        recycler_view_user.setLinearLayoutManager()
    }

    override fun initListener() {
    }

    override fun onLoadMore() {
    }

    override fun onItemClick(parent: ViewGroup, viewType: Int, view: View, position: Int?) {
    }

    override fun onSwipeRefresh() {
    }
}