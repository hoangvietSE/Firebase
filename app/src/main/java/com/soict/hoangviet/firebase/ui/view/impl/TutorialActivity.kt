package com.soict.hoangviet.firebase.ui.view.impl;

import androidx.viewpager2.widget.ViewPager2
import com.soict.hoangviet.baseproject.extension.launchActivity
import com.soict.hoangviet.firebase.R;
import com.soict.hoangviet.firebase.adapter.TutorialAdapter
import com.soict.hoangviet.firebase.data.local.entity.TutorialEntity
import com.soict.hoangviet.firebase.ui.view.TutorialView
import com.soict.hoangviet.firebase.ui.presenter.TutorialPresenter
import kotlinx.android.synthetic.main.activity_tutorial.*
import javax.inject.Inject

class TutorialActivity : BaseActivity(), TutorialView {
    private lateinit var mTutorialAdapter: TutorialAdapter

    override val mLayoutRes: Int
        get() = R.layout.activity_tutorial

    @Inject
    lateinit var mPresenter: TutorialPresenter

    override fun initView() {
        mPresenter.onAttach(this)
        initTutorialAdapter()
    }

    private fun initTutorialAdapter() {
        mTutorialAdapter = TutorialAdapter(this)
        mTutorialAdapter.addModels(
            listOf(
                TutorialEntity(R.drawable.tutorial_one),
                TutorialEntity(R.drawable.tutorial_two),
                TutorialEntity(R.drawable.tutorial_three)
            ), false
        )
        mTutorialAdapter.setOnStartClickListener {
            launchActivity<LoginActivity>()
            finish()
        }
        vp_tutorial.adapter = mTutorialAdapter
        vp_tutorial.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        ci_tutorial.setViewPager(vp_tutorial)
    }

    override fun initListener() {

    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }
}
