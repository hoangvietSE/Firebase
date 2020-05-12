package com.soict.hoangviet.firebase.extension

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.gridLayout(context: Context, spanCount: Int = 2) {
    layoutManager = GridLayoutManager(context, spanCount)
}

fun RecyclerView.linearLayout(
    context: Context,
    orientation: Int = LinearLayoutManager.VERTICAL,
    reverseLayout: Boolean = false
) {
    layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
}