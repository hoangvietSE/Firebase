package com.soict.hoangviet.firebase.ui.view.impl;

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.soict.hoangviet.firebase.R;
import com.soict.hoangviet.firebase.adapter.InfoListImageAdapter
import com.soict.hoangviet.firebase.data.local.entity.PhotoProvider
import com.soict.hoangviet.firebase.extension.gridLayout
import com.soict.hoangviet.firebase.ui.view.InfoView
import com.soict.hoangviet.firebase.ui.presenter.InfoPresenter
import kotlinx.android.synthetic.main.activity_info.*
import javax.inject.Inject

class InfoActivity : BaseActivity(), InfoView {
    override val mLayoutRes: Int
        get() = R.layout.activity_info
    private lateinit var mInfoListImageAdapter: InfoListImageAdapter

    @Inject
    lateinit var mPresenter: InfoPresenter


    override fun initView() {
        mPresenter.onAttach(this)
        initToolbar()
        loadBanner()
        initListImageAdapter()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let { it.setDisplayHomeAsUpEnabled(true) }
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

    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}
