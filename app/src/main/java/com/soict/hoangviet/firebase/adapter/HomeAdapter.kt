package com.soict.hoangviet.firebase.adapter

import androidx.fragment.app.FragmentManager
import com.soict.hoangviet.firebase.ui.view.impl.FriendsFragment
import com.soict.hoangviet.firebase.ui.view.impl.HomeFragment
import com.soict.hoangviet.firebase.ui.view.impl.ProfileFragment

class HomeAdapter(mFragmentManager: FragmentManager) : BaseFragmentPagerAdapter(
    mFragmentManager = mFragmentManager,
    mListFragment = arrayListOf(
        HomeFragment.getInstance(),
        FriendsFragment.getInstance(),
        ProfileFragment.getInstance()
    ),
    mListTitle = arrayListOf("Home", "Friends", "Profile")
)