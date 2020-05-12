package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.firebase.R
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
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(100, 100) // resize does not respect aspect ratio
            val options = BitmapFactory.Options()
            options.inSampleSize = 8
            val mBitmap = BitmapFactory.decodeStream(itemView.context.assets.open("emoji/${childName}/${data}"), null, options)
            Glide.with(itemView.context).load(mBitmap).apply(requestOptions)
                .into(imv_item_emoji)
        }
    }
}