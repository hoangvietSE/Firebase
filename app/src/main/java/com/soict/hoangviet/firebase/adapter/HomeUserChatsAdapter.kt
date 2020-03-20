package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.network.response.HomeResponse
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.inflate
import com.soict.hoangviet.firebase.extension.loadImageUrl
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.utils.AppConstant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_home_user.*

class HomeUserChatsAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(context.inflate(R.layout.item_home_user, parent, false))

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        holder.bind(getItemPosition(position, HomeResponse::class.java))
    }

    class UserViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!),
        LayoutContainer {
        override fun <T> bind(data: T) {
            data as HomeResponse
            tv_name.text = data.user?.fullname
            imv_avatar.loadImageUrl(
                itemView.context,
                data.user?.avatar ?: "",
                R.drawable.ic_avatar,
                R.drawable.ic_avatar
            )
            if (data.user?.status == AppConstant.ONLINE) imv_online.visible() else imv_online.gone()
            tv_message.text = data.lastMessage
        }
    }
}