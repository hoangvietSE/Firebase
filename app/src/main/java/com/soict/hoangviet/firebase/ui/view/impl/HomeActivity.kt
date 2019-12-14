package com.soict.hoangviet.firebase.ui.view.impl

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.HomeAdapter
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.impl.HomeInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.HomePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.HomePresenterImpl
import com.soict.hoangviet.firebase.ui.view.HomeView
import com.soict.hoangviet.firebase.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity<HomePresenter>(), HomeView {
    companion object {
        val TAG: String = HomeActivity::class.java.simpleName
    }

    override val mLayoutRes: Int
        get() = R.layout.activity_home
    private var listFragment: ArrayList<Fragment> = arrayListOf()
    private var homeAdapter: HomeAdapter? = null

    override fun getPresenter(): HomePresenter {
        return HomePresenterImpl(this, HomeInteractorImpl())
    }

    override fun initView() {
        setToolbar()
        getCurrentUser()
        initListFragment()
    }

    private fun setToolbar() {
        toolbar.hideLeftButton()
        toolbar.setTitle(resources.getString(R.string.home_toolbar_title))
    }

    private fun initListFragment() {
        val chatsFragment = ChatsFragment.getInstance()
        val userFragment = UserFragment.getInstance()
        listFragment.add(chatsFragment)
        listFragment.add(userFragment)
        homeAdapter = HomeAdapter(supportFragmentManager, listFragment, arrayListOf("Chats", "User"))
        viewPager.adapter = homeAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun getCurrentUser() {
        getCurrentUser(FirebaseAuth.getInstance().currentUser!!.uid, object : RealTimeDatabaseListener<User> {
            override fun onLoadSuccess(data: User) {
                ToastUtil.show("Welcome ${data.username}")
            }

            override fun onLoadError() {
            }

        })
    }

    override fun initListener() {
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}