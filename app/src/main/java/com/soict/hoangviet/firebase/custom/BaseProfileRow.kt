package com.soict.hoangviet.firebase.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.widget.BaseCustomViewRelativeLayout

class BaseProfileRow(context: Context?, attrs: AttributeSet?) : BaseCustomViewRelativeLayout(context, attrs) {
    override val styleRes: IntArray?
        get() = R.styleable.BaseRowProfile
    override val layoutRes: Int
        get() = R.layout.row_profile
    private lateinit var mTitle: TextView
    private lateinit var mDetail: TextView
    private lateinit var mDivider: View

    override fun initView() {
        mTitle = view.findViewById(R.id.tv_title)
        mDetail = view.findViewById(R.id.tv_detail)
        mDivider = view.findViewById(R.id.divider)
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initDataFromStyleable(ta: TypedArray) {
        val title = ta.getString(R.styleable.BaseRowProfile_rowTitle)
        setTitle(title)
        val detail = ta.getString(R.styleable.BaseRowProfile_rowDetail)
        setDetail(detail)
        val enable = ta.getBoolean(R.styleable.BaseRowProfile_rowEnableDivider, true)
        setDivider(enable)
    }

    fun setDivider(enable: Boolean) {
        if(enable) mDivider.visible() else mDivider.gone()
    }

    fun setTitle(title: String?) {
        if (!title.isNullOrEmpty()) {
            mTitle.text = title
        }
    }

    fun setDetail(detail: String?) {
        if (!detail.isNullOrEmpty()) {
            mDetail.text = detail
        }
    }

}