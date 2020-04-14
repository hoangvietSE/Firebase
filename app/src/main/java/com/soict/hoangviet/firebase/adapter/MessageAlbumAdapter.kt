package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.baseproject.extension.loadImageListener
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_album.*

class MessageAlbumAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {

    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder? {
        return AlbumViewHolder(parent.inflate(R.layout.item_album))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        holder.bind(getItemPosition(position, String::class.java))
    }

    class AlbumViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!),
        LayoutContainer {
        override fun <T> bind(data: T) {
            data as String
            progress_loading_album.visible()
            if(!data.contains("content://")){
                imv_album.loadImageListener(
                    itemView.context,
                    data,
                    {
                        progress_loading_album.gone()
                    },
                    {
                        progress_loading_album.visible()
                    }
                )
            }
        }

    }
}