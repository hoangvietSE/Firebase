package com.soict.hoangviet.firebase.adapter

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallBack(
    val lastModel: MutableList<RecyclerViewAdapter.WrapperModel>,
    val newModel: MutableList<RecyclerViewAdapter.WrapperModel>
) : DiffUtil.Callback() {
    override fun getOldListSize() = lastModel.size

    override fun getNewListSize() = newModel.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = lastModel.get(oldItemPosition).id == newModel.get(newItemPosition).id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = lastModel[oldItemPosition] == newModel[newItemPosition]
}