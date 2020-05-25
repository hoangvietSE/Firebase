package com.soict.hoangviet.firebase.ui.view.impl;

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import com.soict.hoangviet.baseproject.extension.loadImageCircle
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.InfoListImageAdapter
import com.soict.hoangviet.firebase.data.local.entity.PhotoProvider
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.gridLayout
import com.soict.hoangviet.firebase.extension.invisible
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.ui.presenter.InfoPresenter
import com.soict.hoangviet.firebase.ui.view.InfoView
import kotlinx.android.synthetic.main.activity_info.*
import javax.inject.Inject
import kotlin.math.abs


class InfoActivity : BaseActivity(), InfoView {
    override val mLayoutRes: Int
        get() = R.layout.activity_info
    private lateinit var mInfoListImageAdapter: InfoListImageAdapter
    private val userInfo: User by lazy {
        intent.getParcelableExtra(EXTRA_USERS) as User
    }

    companion object {
        const val EXTRA_USERS = "extra_user"
    }

    @Inject
    lateinit var mPresenter: InfoPresenter


    override fun initView() {
        mPresenter.onAttach(this)
        initToolbar()
        loadBanner()
        initListImageAdapter()
        initData()
    }

    private fun initData() {
        imv_avatar.loadImageCircle(
            this,
            userInfo.avatar
        )
        imv_profile.loadImageCircle(
            this,
            userInfo.avatar
        )
        tv_name.text = userInfo.fullname
        tv_name_below.text = userInfo.fullname
    }

    private fun initToolbar() {
        imv_back.setOnClickListener {
            finish()
        }
    }

    private fun loadBanner() {
        Glide.with(this)
            .load(R.drawable.banner)
            .placeholder(R.drawable.img_image_default)
            .error(R.drawable.img_image_default)
            .skipMemoryCache(true)//always load
            .diskCacheStrategy(DiskCacheStrategy.NONE)//Not write image into disk and memory
            .into(imv_banner)
    }

    private fun initListImageAdapter() {
        mInfoListImageAdapter = InfoListImageAdapter(this)
        rcv_album.adapter = mInfoListImageAdapter
        mInfoListImageAdapter.addModels(PhotoProvider().photos, false)
        rcv_album.gridLayout(this, 3)
    }

    override fun initListener() {
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage =
                abs(verticalOffset).toFloat() / maxScroll.toFloat()
            if (percentage >= 0.99f) {
                imv_avatar.visible()
                tv_name.visible()
                imv_profile.invisible()
                tv_name_below.invisible()
            } else {
                imv_avatar.gone()
                tv_name.gone()
                imv_profile.visible()
                tv_name_below.visible()
            }
        })
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}
