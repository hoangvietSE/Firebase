package com.soict.hoangviet.firebase.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

open class BaseFragmentPagerAdapter(
    mFragmentManager: FragmentManager,
    val mListFragment: ArrayList<Fragment>,
    val mListTitle: ArrayList<String>
) :
    FragmentPagerAdapter(mFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = mListFragment[position]

    override fun getCount() = mListFragment.size

    override fun getPageTitle(position: Int) = mListTitle[position]
}