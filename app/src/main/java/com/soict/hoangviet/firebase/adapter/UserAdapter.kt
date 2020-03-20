package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.inflate
import com.soict.hoangviet.firebase.extension.loadImageUrl
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.utils.AppConstant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder? {
        return UserViewHolder(context.inflate(R.layout.item_user, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        holder.bind(getItemPosition(position, User::class.java))
    }

    class UserViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        val tvName: TextView = itemView.findViewById(R.id.tv_username)
        val imvAvatar : ImageView = itemView.findViewById(R.id.imv_avatar)
        val imvOnline : ImageView = itemView.findViewById(R.id.imv_online)
        override fun <T> bind(data: T) {
            data as User
            tv_username.text = data.fullname
            imv_avatar.loadImageUrl(
                itemView.context,
                data.avatar,
                R.drawable.ic_avatar,
                R.drawable.ic_avatar
            )
            if (data.status == AppConstant.ONLINE) imv_online.visible() else imv_online.gone()

        }
    }
}