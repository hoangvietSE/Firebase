package com.soict.hoangviet.firebase.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.widget.BaseCustomViewConstrainLayout

class CustomFirebaseToolbar : BaseCustomViewConstrainLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var imvFunction: ImageView? = null
    private var imvRightOne: ImageView? = null
    private var toolbarTitle: TextView? = null
    private var toolbarMainName: TextView? = null

    override val layoutRes: Int
        get() = R.layout.layout_toolbar
    override val styleRes: IntArray?
        get() = R.styleable.BaseToolbar


    override fun initView() {
        imvFunction = findViewById(R.id.imv_function)
        imvRightOne = findViewById(R.id.imv_right_one)
        toolbarTitle = findViewById(R.id.toolbar_title)
        toolbarMainName = findViewById(R.id.toolbar_main_name)
        imvFunction?.setOnClickListener {
        }
    }

    override fun initDataFromStyleable(typeArray: TypedArray?) {
        val imageIcon = typeArray?.getInt(R.styleable.BaseToolbar_bt_icon, -1)
        val title = typeArray?.getString(R.styleable.BaseToolbar_bt_title)
        setTitle(title)
        setIconToolbar(imageIcon ?: -1)
    }

    fun setIconToolbar(@DrawableRes icon: Int) {
        if (icon != -1) {
            imvFunction?.let { it.setImageResource(icon) }
        }
    }

    fun setTitle(title: String?) {
        title?.let { toolbarTitle?.text = it }
    }

    fun setMainName(name: String?) {
        name?.let { toolbarMainName?.text = it }
    }

    fun hideLeftButton() {
        imvFunction?.let { it.gone() }
    }

    fun hideMainName() {
        toolbarMainName?.let { it.gone() }
    }

    override fun initData() {

    }

    override fun initListener() {
    }
}