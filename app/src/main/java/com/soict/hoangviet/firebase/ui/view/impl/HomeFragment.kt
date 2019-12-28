package com.soict.hoangviet.firebase.ui.view.impl

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.BaseRecyclerView
import com.soict.hoangviet.firebase.adapter.EndlessLoadingRecyclerViewAdapter
import com.soict.hoangviet.firebase.adapter.HomeUserChatsAdapter
import com.soict.hoangviet.firebase.adapter.RecyclerViewAdapter
import com.soict.hoangviet.firebase.data.network.response.HomeResponse
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.impl.HomeInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.HomePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.HomePresenterImpl
import com.soict.hoangviet.firebase.ui.view.HomeView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomePresenter>(), HomeView,
    EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
    RecyclerViewAdapter.OnItemClickListener, BaseRecyclerView.BaseSwipeRefreshListener {
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

    private var mHomeUserChatsAdapter: HomeUserChatsAdapter? = null

    override fun getPresenter(): HomePresenter {
        return HomePresenterImpl(this, HomeInteractorImpl())
    }

    override fun initView() {
        getAllChatUsers()
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
            recycler_view_user.setLoadingMoreListner(this)
            recycler_view_user.setOnItemClickListener(this)
            recycler_view_user.setOnRefreshingListener(this)
            mHomeUserChatsAdapter?.addModels(mListUserChat, false)
            recycler_view_user.setLinearLayoutManager()
        }
    }

    override fun notifyChange(position: Int) {
        mHomeUserChatsAdapter?.notifyItemChanged(position)
    }

    override fun onLoadMore() {
    }

    override fun onItemClick(parent: ViewGroup, viewType: Int, view: View, position: Int?) {

    }

    override fun onSwipeRefresh() {
    }
}