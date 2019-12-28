package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.network.response.HomeResponse
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.inflate
import com.soict.hoangviet.firebase.extension.loadImageUrl
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.utils.AppConstant

class HomeUserChatsAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    override fun initLoadingViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        return LoadingViewHolder(context.inflate(R.layout.layout_loadmore))
    }

    override fun onBindLoadingViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHoler? {
        return UserViewHolder(context.inflate(R.layout.item_home_user, parent, false))
    }

    override fun bindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userViewHoler = holder as UserViewHolder
        val data = getItemPosition(position, HomeResponse::class.java)
        userViewHoler.tvName.text = data.user?.fullname
        userViewHoler.imvAvatar.loadImageUrl(
            context,
            data.user?.avatar ?: "",
            R.drawable.ic_avatar,
            R.drawable.ic_avatar
        )
        if (data.user?.status == AppConstant.ONLINE) userViewHoler.imvOnline.visible() else userViewHoler.imvOnline.gone()
        userViewHoler.tvMessage.text = data.lastMessage
    }

    class UserViewHolder(itemView: View) : NormalViewHoler(itemView) {
        val imvAvatar: ImageView = itemView.findViewById(R.id.imv_avatar)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvMessage: TextView = itemView.findViewById(R.id.tv_message)
        val imvOnline: ImageView = itemView.findViewById(R.id.imv_online)
    }
}