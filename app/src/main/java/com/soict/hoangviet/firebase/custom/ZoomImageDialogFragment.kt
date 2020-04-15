package com.soict.hoangviet.firebase.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.soict.hoangviet.baseproject.extension.loadImage
import com.soict.hoangviet.firebase.R
import kotlinx.android.synthetic.main.layout_dialog_fragment_zoom_image.*


class ZoomImageDialogFragment : DialogFragment() {
    private lateinit var mUrl: String

    companion object {
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get url
        mUrl = arguments?.getString(EXTRA_URL)!!
        //Set  fullscreen
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_dialog_fragment_zoom_image, container, false)
    }

    override fun onStart() {
        super.onStart()
        imv_zoom.loadImage(
            requireContext(),
            mUrl,
            R.drawable.img_image_default,
            R.drawable.img_image_default
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {

    }

}