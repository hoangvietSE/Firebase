package com.soict.hoangviet.firebase.ui.view.impl

import android.os.Build
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.HomeAdapter
import com.soict.hoangviet.firebase.adapter.MainNavigationAdapter
import com.soict.hoangviet.firebase.adapter.RecyclerViewAdapter
import com.soict.hoangviet.firebase.custom.ItemMenuNav
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.ui.interactor.impl.MainInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.MainPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.MainPresenterImpl
import com.soict.hoangviet.firebase.ui.view.MainView
import com.soict.hoangviet.firebase.utils.AppConstant
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_nav.view.*
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.layout_navigation.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView,
    RecyclerViewAdapter.OnItemClickListener,
    RecyclerViewAdapter.OnItemPressListener, RecyclerViewAdapter.OnItemCancelListener {
    companion object {
        val TAG: String = MainActivity::class.java.simpleName
        const val ITEM_HOME = "home"
        const val ITEM_FRIEND = "friends"
        const val ITEM_PROFILE = "profile"
        const val ITEM_LOGOUT = "logout"
        const val ITEM_MAIN_HOME = 0
        const val ITEM_MAIN_FRIEND = 1
        const val ITEM_MAIN_PROFILE = 2
        const val DELAY_LOGOUT = 1000L
        const val DELAY_LOADING = 1600L
    }

    override val mLayoutRes: Int
        get() = R.layout.activity_main
    private var homeAdapter: HomeAdapter? = null
    private lateinit var mMainNavigationAdapter: MainNavigationAdapter
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var listItemNav: ArrayList<ItemMenuNav>

    @Inject
    lateinit var mPresenter: MainPresenter

    @Inject
    lateinit var mAppSharePreference: SharePreference

    override fun initView() {
        mPresenter.onAttach(this)
        showLoadingMain()
        setStatus(AppConstant.ONLINE)
        setToolbar()
        initListFragment()
        initNavigationDrawer()
    }

    private fun showLoadingMain() {
        startAnim()
        Handler(mainLooper).postDelayed({
            stopAnim()
        }, DELAY_LOADING)
    }

    private fun startAnim() {
        rl_loading.visible()
        avi.smoothToShow()
    }

    private fun stopAnim() {
        rl_loading.gone()
        avi.smoothToHide()
    }

    private fun setToolbar() {
        toolbar.setIconToolbar(R.drawable.ic_navigation)
        setMainNameToolbar("Home")
    }

    private fun initListFragment() {
        homeAdapter = HomeAdapter(supportFragmentManager)
        viewPager.adapter = homeAdapter
        viewPager.offscreenPageLimit = 2
        viewPager.currentItem = ITEM_MAIN_HOME
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                val data = mMainNavigationAdapter.getItemPosition(position, ItemMenuNav::class.java)
                bottom_bar.setActiveItem(position)
                dataSetChangeAdapter(data, position)
            }

        })
    }

    private fun setMainNameToolbar(name: String) {
        toolbar.setMainName(name)
    }

    private fun initNavigationDrawer() {
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawer_layout, R.string.nav_open, R.string.nav_close)
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        val itemHome = ItemMenuNav(
            R.drawable.ic_home_active,
            R.drawable.ic_home_disable,
            "Home",
            "home",
            false
        )
        val itemFriends = ItemMenuNav(
            R.drawable.ic_friends_active,
            R.drawable.ic_friends_disable,
            "Bạn bè",
            "friends",
            false
        )
        val itemProfile = ItemMenuNav(
            R.drawable.ic_profile_active,
            R.drawable.ic_profile_disable,
            "Cá nhân",
            "profile",
            false
        )
        val itemLogout = ItemMenuNav(
            R.drawable.ic_logout_active,
            R.drawable.ic_logout_disable,
            "Đăng xuất",
            "logout",
            false
        )
        listItemNav = arrayListOf()
        listItemNav.add(itemHome)
        listItemNav.add(itemFriends)
        listItemNav.add(itemProfile)
        listItemNav.add(itemLogout)
        mMainNavigationAdapter = MainNavigationAdapter(this)
        nav_list_item.setAdapter(mMainNavigationAdapter)
        mMainNavigationAdapter?.setOnItemPressListener(this)
        mMainNavigationAdapter?.setOnItemClickListener(this)
        mMainNavigationAdapter?.setOnItemCancelListener(this)
        nav_list_item.addModels(listItemNav, false)
        nav_list_item.setLinearLayoutManager()
        nav_list_item.disableRefreshing()
    }

    override fun initListener() {
        bottom_bar.setActiveItem(0)
        bottom_bar.onItemSelected = {
            viewPager.currentItem = it
        }
        imv_function.setOnClickListener {
            openDrawer()
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onItemPress(view: View, position: Int?) {
        val data = mMainNavigationAdapter.getItemPosition(position!!, ItemMenuNav::class.java)
        handleEventTouch(view, data, true)
    }

    override fun onItemCancel(view: View, position: Int?) {
        val data = mMainNavigationAdapter.getItemPosition(position!!, ItemMenuNav::class.java)
        handleEventTouch(view, data, false)
    }

    override fun onItemClick(parent: ViewGroup, viewType: Int, view: View, position: Int?) {
        val data = mMainNavigationAdapter.getItemPosition(position!!, ItemMenuNav::class.java)
        handleEventTouch(view, data, false)
        dataSetChangeAdapter(data, position)
        closeDrawer()
        when (data.code) {
            ITEM_LOGOUT -> {
                logoutApplication()
            }
        }
    }

    private fun logoutApplication() {
        showLoading()
        mAppSharePreference.clearAllPreference()
        Handler().postDelayed({
            FirebaseAuth.getInstance().signOut()
            hideLoading()
            startActivity(LoginActivity::class.java)
            finish()
        }, DELAY_LOGOUT)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun closeDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    private fun openDrawer() {
        drawer_layout.openDrawer(GravityCompat.START)
    }

    private fun dataSetChangeAdapter(data: ItemMenuNav, position: Int?) {
        when (data.code) {
            ITEM_HOME -> {
                viewPager.currentItem = ITEM_MAIN_HOME
            }
            ITEM_FRIEND -> {
                viewPager.currentItem = ITEM_MAIN_FRIEND
            }
            ITEM_PROFILE -> {
                viewPager.currentItem = ITEM_MAIN_PROFILE
            }
        }
        for ((index, item) in listItemNav.withIndex()) {
            item.selected = index == position
        }
        mMainNavigationAdapter?.notifyDataSetChanged()
        setMainNameToolbar(data.description)
    }

    private fun handleEventTouch(view: View, data: ItemMenuNav, hover: Boolean) {
        if (hover) {
            view.setBackgroundResource(R.drawable.bg_item_nav_selected)
            view.imv_item_nav.setImageResource(data.iconSelected)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.tv_item_nav.setTextColor(resources.getColor(R.color.colorPrimary, null))
            } else {
                view.tv_item_nav.setTextColor(resources.getColor(R.color.colorPrimary))
            }
        } else {
            view.setBackgroundResource(R.drawable.bg_item_nav_unselected)
            view.imv_item_nav.setImageResource(data.iconDefault)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.tv_item_nav.setTextColor(resources.getColor(R.color.colorAccent, null))
            } else {
                view.tv_item_nav.setTextColor(resources.getColor(R.color.colorAccent))
            }
        }
    }

    private fun setStatus(status: Int) {
        mPresenter.setStatus(status)
    }

    override fun onDestroy() {
        super.onDestroy()
        setStatus(AppConstant.OFFLINE)
    }
}