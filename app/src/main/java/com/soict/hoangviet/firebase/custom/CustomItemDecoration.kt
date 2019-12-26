package com.soict.hoangviet.firebase.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecoration(val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.apply {
                top = spacing
                right = spacing
                left = spacing
                bottom = spacing
            }
        } else {
            outRect.apply {
                right = spacing
                left = spacing
                bottom = spacing
            }
        }
    }
}