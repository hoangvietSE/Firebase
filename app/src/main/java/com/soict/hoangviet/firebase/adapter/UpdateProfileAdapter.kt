package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.extension.inflate
import kotlinx.android.synthetic.main.item_spinner_update_profile.view.*

class UpdateProfileAdapter(val context: Context) : BaseAdapter() {
    private var listItem: ArrayList<String> = arrayListOf(context.getString(R.string.update_profile_adapter_male), context.getString(
            R.string.update_profile_adapter_female))

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = parent!!.inflate(R.layout.item_spinner_update_profile)
        view.tv_gender.text = listItem[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = context.inflate(R.layout.item_spinner_update_profile_dropdown, parent!!, false)
        view.tv_gender.text = listItem[position]
        return view
    }


    override fun getItem(position: Int) = listItem[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = listItem.size
}