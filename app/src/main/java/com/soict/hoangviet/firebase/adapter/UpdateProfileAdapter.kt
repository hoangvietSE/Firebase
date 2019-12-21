package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.extension.inflate
import kotlinx.android.synthetic.main.item_spinner_update_profile.view.*

class UpdateProfileAdapter(val context: Context, val listItem: ArrayList<String>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = context.inflate(R.layout.item_spinner_update_profile, parent!!, false)
        view.tv_gender.text = listItem[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = context.inflate(R.layout.item_spinner_update_profile_dropdown, parent!!, false)
        view.tv_gender.text = listItem[position]
        return view
    }


    override fun getItem(position: Int): Any {
        return listItem[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listItem.size
    }
}