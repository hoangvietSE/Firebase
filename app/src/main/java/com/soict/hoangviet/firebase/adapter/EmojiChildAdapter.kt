package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.local.Emoji
import com.soict.hoangviet.firebase.module.GlideApp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_emoji_child.*

class EmojiChildAdapter(context: Context, val childName: String) :
    EndlessLoadingRecyclerViewAdapter(context) {
    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder? {
        return EmojiViewHolder(parent.inflate(R.layout.item_emoji_child), childName = childName)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        holder.bind(getItemPosition(position, String::class.java))
    }

    class EmojiViewHolder(override val containerView: View?, val childName: String) :
        NormalViewHolder(containerView!!), LayoutContainer {
        override fun <T> bind(data: T) {
            data as String
            imv_item_emoji.setImageBitmap(BitmapFactory.decodeStream(itemView.context.assets.open("emoji/${childName}/${data}")))
        }
    }
}