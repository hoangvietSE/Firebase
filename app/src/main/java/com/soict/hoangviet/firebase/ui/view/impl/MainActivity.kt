package com.soict.hoangviet.firebase.ui.view.impl

import android.os.Build
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
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.impl.MainInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.MainPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.MainPresenterImpl
import com.soict.hoangviet.firebase.ui.view.MainView
import com.soict.hoangviet.firebase.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_nav.view.*
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.layout_navigation.*

class MainActivity : BaseActivity<MainPresenter>(), MainView, RecyclerViewAdapter.OnItemClickListener,
        RecyclerViewAdapter.OnItemPressListener {
    companion object {
        val TAG: String = MainActivity::class.java.simpleName
        const val ITEM_HOME = "home"
        const val ITEM_FRIEND = "friends"
        const val ITEM_PROFILE = "profile"
        const val ITEM_MAIN_HOME = 0;
        const val ITEM_MAIN_FRIEND = 1;
        const val ITEM_MAIN_PROFILE = 2;
    }

    override val mLayoutRes: Int
        get() = R.layout.activity_main
    private var listFragment: ArrayList<Fragment> = arrayListOf()
    private var homeAdapter: HomeAdapter? = null
    private lateinit var mMainNavigationAdapter: MainNavigationAdapter
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun getPresenter(): MainPresenter {
        return MainPresenterImpl(this, MainInteractorImpl())
    }

    override fun initView() {
        setToolbar()
        getCurrentUser()
        initListFragment()
        initNavigationDrawer()
    }

    private fun setToolbar() {
    }

    private fun initListFragment() {
        val homeFragment = HomeFragment.getInstance()
        val friendsFragment = FriendsFragment.getInstance()
        val profileFragment = ProfileFragment.getInstance()
        listFragment.add(homeFragment)
        listFragment.add(friendsFragment)
        listFragment.add(profileFragment)
        homeAdapter = HomeAdapter(supportFragmentManager, listFragment, arrayListOf("Home", "Friends", "Profile"))
        viewPager.adapter = homeAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                bottom_bar.setActiveItem(position)
            }

        })
    }

    private fun initNavigationDrawer() {
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout, R.string.nav_open, R.string.nav_close)
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        val listItemNav: ArrayList<ItemMenuNav> = arrayListOf()
        val itemHome = ItemMenuNav(
                R.drawable.ic_home_active,
                R.drawable.ic_home,
                "Home",
                "home",
                false
        )
        val itemFriends = ItemMenuNav(
                R.drawable.ic_friends_active,
                R.drawable.ic_priends,
                "Bạn bè",
                "friends",
                false
        )
        val itemProfile = ItemMenuNav(
                R.drawable.ic_profile_active,
                R.drawable.ic_profile,
                "Cá nhân",
                "profile",
                false
        )
        listItemNav.add(itemHome)
        listItemNav.add(itemFriends)
        listItemNav.add(itemProfile)
        mMainNavigationAdapter = MainNavigationAdapter(this)
        nav_list_item.setAdapter(mMainNavigationAdapter)
        mMainNavigationAdapter?.setOnItemPressListener(this)
        mMainNavigationAdapter?.setOnItemClickListener(this)
        nav_list_item.addModels(listItemNav, false)
        nav_list_item.setLinearLayoutManager()
        nav_list_item.disableRefreshing()
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
        bottom_bar.setActiveItem(0)
        bottom_bar.onItemSelected = {
            viewPager.currentItem = it
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onItemPress(view: View, position: Int?) {
        val data = mMainNavigationAdapter.getItemPosition(position!!, ItemMenuNav::class.java)
        view.setBackgroundResource(R.drawable.bg_item_nav_selected)
        view.imv_item_nav.setImageResource(data.iconSelected)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.tv_item_nav.setTextColor(resources.getColor(R.color.colorPrimary, null))
        } else {
            view.tv_item_nav.setTextColor(resources.getColor(R.color.colorPrimary))
        }
    }

    override fun onItemClick(parent: ViewGroup, viewType: Int, view: View, position: Int?) {
        val data = mMainNavigationAdapter.getItemPosition(position!!, ItemMenuNav::class.java)
        view.setBackgroundResource(R.drawable.bg_item_nav_unselected)
        view.imv_item_nav.setImageResource(data.iconDefault)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.tv_item_nav.setTextColor(resources.getColor(R.color.md_black_1000, null))
        } else {
            view.tv_item_nav.setTextColor(resources.getColor(R.color.md_black_1000))
        }
        closeDrawer()
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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        closeDrawer()
    }

    private fun closeDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    private fun openDrawer() {
        drawer_layout.openDrawer(GravityCompat.START)
    }
}