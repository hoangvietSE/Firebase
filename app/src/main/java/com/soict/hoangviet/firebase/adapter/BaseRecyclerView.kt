package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.widget.BaseCustomViewRelativeLayout
import kotlinx.android.synthetic.main.layout_base_recycler_view.view.*

class BaseRecyclerView(context: Context?, attrs: AttributeSet?) :
    BaseCustomViewRelativeLayout(context, attrs) {
    private var mBaseAdapter: EndlessLoadingRecyclerViewAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override val layoutRes: Int
        get() = R.layout.layout_base_recycler_view

    override fun initView() {
        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
    }

    override fun initData() {

    }

    override fun initListener() {
    }

    fun setAdapter(adapter: EndlessLoadingRecyclerViewAdapter) {
        mBaseAdapter = adapter
        recycler_view_actual.adapter = mBaseAdapter
    }

    fun setLinearLayoutManager() {
        mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler_view_actual.layoutManager = mLayoutManager
    }

    fun setLinearLayoutManagerMessage() {
        val mLinearLayoutManager = LinearLayoutManager(context)
        recycler_view_actual.layoutManager = mLinearLayoutManager
        mLinearLayoutManager.stackFromEnd = true
    }

    fun setGridLayoutManager(numberColumn: Int) {
        mLayoutManager = GridLayoutManager(context, numberColumn)
        recycler_view_actual.layoutManager = mLayoutManager
    }

    fun setOnItemClickListener(listener: RecyclerViewAdapter.OnItemClickListener) {
        mBaseAdapter?.let {
            it.setOnItemClickListener(listener)
        }
    }

    fun setOnRefreshListener(listener: () -> Unit) {
        swipe_refresh_layout.setOnRefreshListener {
            listener.invoke()
        }
    }

    fun showLoadingMoreProgress() {
        mBaseAdapter?.let {
            it.showLoadingMoreProgress()
        }
    }

    fun setIsLoading(isLoading: Boolean) {
        mBaseAdapter?.let {
            it.setIsLoading(isLoading)
        }
    }

    fun hideLoadingMoreProgress() {
        mBaseAdapter?.let {
            it.hideLoadingMoreProgress()
        }
    }

    fun enableLoadingMore(enable: Boolean) {
        mBaseAdapter?.let {
            it.enableLoadingMore(enable)
        }
    }

    fun setLoadingMoreListener(listener: () -> Unit) {
        mBaseAdapter?.let {
            it.setLoadingMoreListener(listener)
        }
    }

    fun clear() {
        mBaseAdapter?.let {
            it.clear()
        }
    }

    fun addModels(listModel: List<Any?>, isScroolToLast: Boolean) {
        mBaseAdapter?.let {
            it.addModels(listModel, isScroolToLast)
        }
    }

    fun addItemDecoration(itemDecoration: RecyclerView.ItemDecoration) {
        recycler_view_actual.addItemDecoration(itemDecoration)
    }

    fun hideRefreshing() {
        swipe_refresh_layout.isRefreshing = false
    }

    fun showRefreshing() {
        swipe_refresh_layout.isRefreshing = true
    }

    fun disableRefreshing() {
        swipe_refresh_layout.isEnabled = false
    }

    fun enableRefreshing() {
        swipe_refresh_layout.isEnabled = true
    }
}