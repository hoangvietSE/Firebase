package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.custom.ItemMenuNav
import com.soict.hoangviet.firebase.extension.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_nav.*

class MainNavigationAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder? {
        return NavigationViewHolder(context.inflate(R.layout.item_nav, parent, false))
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_item_nav.setTextColor(
                        itemView.context.resources.getColor(
                            R.color.colorPrimary,
                            null
                        )
                    )
                } else {
                    tv_item_nav.setTextColor(itemView.context.resources.getColor(R.color.colorPrimary))
                }
                imv_item_nav.setImageResource(data.iconSelected)
                tv_item_nav.setBackgroundResource(R.drawable.bg_item_nav_selected)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_item_nav.setTextColor(
                        itemView.context.getColor(R.color.colorAccent)
                    )
                } else {
                    tv_item_nav.setTextColor(itemView.context.resources.getColor(R.color.colorAccent))
                }
                imv_item_nav.setImageResource(data.iconDefault)
                tv_item_nav.setBackgroundResource(R.drawable.bg_item_nav_unselected)
            }

        }
    }
}