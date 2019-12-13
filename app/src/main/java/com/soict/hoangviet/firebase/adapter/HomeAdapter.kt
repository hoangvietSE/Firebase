package com.soict.hoangviet.firebase.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class HomeAdapter(
        mFragmentManager: FragmentManager,
        mListFragment: ArrayList<Fragment>,
        mListTitle: ArrayList<String>
) : BaseFragmentPagerAdapter(mFragmentManager, mListFragment, mListTitle) {
}