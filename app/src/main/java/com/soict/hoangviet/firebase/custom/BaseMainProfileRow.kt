package com.soict.hoangviet.firebase.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.widget.BaseCustomViewRelativeLayout

class BaseMainProfileRow(context: Context?, attrs: AttributeSet?) : BaseCustomViewRelativeLayout(context, attrs) {
    override val styleRes: IntArray?
        get() = R.styleable.BaseRowMainProfile
    override val layoutRes: Int
        get() = R.layout.row_main_profile
    private lateinit var mImage: ImageView
    private lateinit var mDetail: TextView
    private lateinit var mDivider: View
    private lateinit var mNavigate: ImageView

    override fun initView() {
        mImage = view.findViewById(R.id.imv_icon)
        mDetail = view.findViewById(R.id.tv_detail)
        mDivider = view.findViewById(R.id.divider)
        mNavigate = view.findViewById(R.id.imv_navigate)
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initDataFromStyleable(ta: TypedArray) {
        val mainImage = ta.getResourceId(R.styleable.BaseRowMainProfile_rowImage, R.drawable.ic_canhan)
        setImage(mainImage)
        val detail = ta.getString(R.styleable.BaseRowMainProfile_rowItemDetail)
        setDetail(detail)
        val enableDivider = ta.getBoolean(R.styleable.BaseRowMainProfile_rowItemEnableDivider, true)
        setDivider(enableDivider)
        val enableNavigate = ta.getBoolean(R.styleable.BaseRowMainProfile_rowItemEnableNavigate, true)
        setNavigate(enableNavigate)
    }

    fun setImage(@DrawableRes resId: Int) {
        mImage.setImageResource(resId)
    }

    fun setImage(res: Drawable) {
        mImage.setImageDrawable(res)
    }

    fun setDivider(enableDivider: Boolean) {
        if (enableDivider) mDivider.visible() else mDivider.gone()
    }

    fun setNavigate(enableNavigate: Boolean) {
        if (enableNavigate) mNavigate.visible() else mNavigate.gone()
    }

    fun setDetail(detail: String?) {
        if (!detail.isNullOrEmpty()) {
            mDetail.text = detail
        }
    }

}