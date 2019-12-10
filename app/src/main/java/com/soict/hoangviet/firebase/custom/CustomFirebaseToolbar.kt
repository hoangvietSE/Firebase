package com.soict.hoangviet.firebase.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.widget.BaseCustomViewConstrainLayout

class CustomFirebaseToolbar : BaseCustomViewConstrainLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var imvBack: ImageView? = null
    private var toolbarTitle: TextView? = null

    override val layoutRes: Int
        get() = R.layout.layout_toolbar
    override val styleRes: IntArray?
        get() = R.styleable.BaseToolbar


    override fun initView() {
        imvBack = findViewById(R.id.imv_back)
        toolbarTitle = findViewById(R.id.toolbar_title)
    }

    override fun initDataFromStyleable(typeArray: TypedArray?) {
        val title = typeArray?.getString(R.styleable.BaseToolbar_bt_title)
        setTitle(title)
    }

    fun setTitle(title: String?) {
        title?.let {
            toolbarTitle?.text = title
        }
    }

    override fun initData() {

    }

    override fun initListener() {
    }
}