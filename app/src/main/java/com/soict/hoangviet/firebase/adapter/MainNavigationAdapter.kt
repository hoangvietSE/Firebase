package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.custom.ItemMenuNav
import com.soict.hoangviet.firebase.extension.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_nav.*

class MainNavigationAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder? {
        return NavigationViewHolder(parent.inflate(R.layout.item_nav))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        holder.bind(getItemPosition(position, ItemMenuNav::class.java))
    }

    class NavigationViewHolder(override val containerView: View?) :
            NormalViewHolder(containerView!!), LayoutContainer {
        override fun <T> bind(data: T) {
            data as ItemMenuNav
            tv_item_nav.text = data.description
            if (data.selected) {
                tv_item_nav.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
                imv_item_nav.setImageResource(data.iconSelected)
                tv_item_nav.setBackgroundResource(R.drawable.bg_item_nav_selected)
            } else {
                tv_item_nav.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
                imv_item_nav.setImageResource(data.iconDefault)
                tv_item_nav.setBackgroundResource(R.drawable.bg_item_nav_unselected)
            }

        }
    }
}