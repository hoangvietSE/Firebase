package com.soict.hoangviet.firebase.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.soict.hoangviet.firebase.ui.view.impl.BaseFragment

open class BaseFragmentPagerAdapter(
    mFragmentManager: FragmentManager,
    val mListFragment: ArrayList<BaseFragment>,
    val mListTitle: ArrayList<String>
) :
    FragmentPagerAdapter(mFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = mListFragment[position] as Fragment

    override fun getCount() = mListFragment.size

    override fun getPageTitle(position: Int) = mListTitle[position]
}