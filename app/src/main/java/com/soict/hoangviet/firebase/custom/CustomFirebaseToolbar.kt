package com.soict.hoangviet.firebase.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import com.soict.hoangviet.baseproject.extension.onAvoidDoubleClick
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.widget.BaseCustomViewConstrainLayout
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class CustomFirebaseToolbar : BaseCustomViewConstrainLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var mOnBackClickListener: (() -> Unit)? = null

    override val layoutRes: Int
        get() = R.layout.layout_toolbar
    override val styleRes: IntArray?
        get() = R.styleable.CustomFirebaseToolbar


    override fun initView() {

    }

    override fun initListener() {
        imv_back?.onAvoidDoubleClick {
            mOnBackClickListener?.invoke()
        }
    }

    override fun initDataFromStyleable(typeArray: TypedArray?) {
        val imageIcon = typeArray?.getDrawable(R.styleable.CustomFirebaseToolbar_cft_icon)
        val title = typeArray?.getString(R.styleable.CustomFirebaseToolbar_cft_title)
        setTitle(title)
        setIconToolbar(imageIcon)
    }

    fun setIconToolbar(icon: Drawable?) {
        icon?.let {
            imv_back?.setImageDrawable(icon)
        }
    }

    fun setIconToolbar(@DrawableRes icon: Int) {
        if (icon != -1) {
            imv_back?.let { it.setImageResource(icon) }
        }
    }

    fun setTitle(title: String?) {
        title?.let { toolbar_title?.text = it }
    }

    fun setMainName(name: String?) {
        name?.let { toolbar_title?.text = it }
    }

    fun hideLeftButton() {
        imv_back?.let { it.gone() }
    }

    fun hideMainName() {
        toolbar_title?.let { it.gone() }
    }

    fun setOnBackClickListener(func: () -> Unit) {
        mOnBackClickListener = func
    }

    override fun initData() {

    }
}