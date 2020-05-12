package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.soict.hoangviet.baseproject.extension.displayMetrics
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.module.GlideApp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_info.*

class InfoListImageAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder? {
        return ListImageViewHolder(parent.inflate(R.layout.item_info))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        holder.bind(getItemPosition(position, String::class.java))
    }

    class ListImageViewHolder(override val containerView: View?) :
        NormalViewHolder(containerView!!), LayoutContainer {
        override fun <T> bind(data: T) {
            data as String
            GlideApp
                .with(itemView)
                .load("${data}?w=360")
                .placeholder(R.drawable.ic_image_place_holder)
                .error(R.drawable.ic_broken_image)
                .transform(CenterCrop())
                .into(imv_item_info)
            val width = itemView.context.displayMetrics.widthPixels / 3
            itemView.layoutParams = RelativeLayout.LayoutParams(width, width)
        }

    }
}