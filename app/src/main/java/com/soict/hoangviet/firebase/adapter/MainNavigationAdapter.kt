package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.custom.ItemMenuNav
import com.soict.hoangviet.firebase.extension.inflate
import kotlinx.android.synthetic.main.item_nav.view.*

class MainNavigationAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    override fun initLoadingViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        return LoadingViewHolder(context.inflate(R.layout.layout_loadmore))
    }

    override fun onBindLoadingViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHoler? {
        return NavigationViewHolder(context.inflate(R.layout.item_nav, parent, false))
    }

    override fun bindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val navigationViewHolder = holder as NavigationViewHolder
        val data = getItemPosition(position, ItemMenuNav::class.java)
        navigationViewHolder.itemText.text = data.description
        if (data.selected) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                navigationViewHolder.itemText.setTextColor(context.resources.getColor(R.color.colorPrimary, null))
            } else {
                navigationViewHolder.itemText.setTextColor(context.resources.getColor(R.color.colorPrimary))
            }
            navigationViewHolder.itemIcon.setImageResource(data.iconSelected)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                navigationViewHolder.itemText.setTextColor(context.resources.getColor(R.color.md_black_1000, null))
            } else {
                navigationViewHolder.itemText.setTextColor(context.resources.getColor(R.color.md_black_1000))
            }
            navigationViewHolder.itemIcon.setImageResource(data.iconDefault)
        }
    }

    class NavigationViewHolder(itemView: View) : NormalViewHoler(itemView) {
        val itemIcon: ImageView = itemView.imv_item_nav
        val itemText: TextView = itemView.tv_item_nav
    }
}