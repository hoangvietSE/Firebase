package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.local.entity.TutorialEntity
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.loadImageDrawable
import com.soict.hoangviet.firebase.extension.visible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tutorial.*
import kotlinx.android.synthetic.main.item_tutorial.view.*

class TutorialAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    private var onStartClickListener: (() -> Unit)? = null
    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder? {
        val view = parent.inflate(R.layout.item_tutorial)
        view.btn_start.setOnClickListener {
            onStartClickListener?.invoke()
        }
        return TutorialViewHolder(view)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        holder.bind(getItemPosition(position, TutorialEntity::class.java))
    }

    fun setOnStartClickListener(func: (() -> Unit)) {
        onStartClickListener = func
    }

    class TutorialViewHolder(override val containerView: View?) :
        NormalViewHolder(containerView!!),
        LayoutContainer {

        override fun <T> bind(data: T) {
            data as TutorialEntity
            imv_tutorial.loadImageDrawable(
                itemView.context,
                data.image,
                R.drawable.img_image_default,
                R.drawable.img_image_default
            )
            btn_start.gone()
            when (adapterPosition) {
                0 -> {
                    csl_tutorial.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorTutorialOne
                        )
                    )
                }
                1 -> {
                    csl_tutorial.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorTutorialTwo
                        )
                    )
                }
                2 -> {
                    csl_tutorial.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorTutorialThree
                        )
                    )
                    btn_start.visible()
                }
            }
        }
    }
}