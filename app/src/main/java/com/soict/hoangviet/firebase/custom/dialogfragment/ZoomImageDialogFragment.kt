package com.soict.hoangviet.firebase.custom.dialogfragment

import com.soict.hoangviet.baseproject.extension.argument
import com.soict.hoangviet.baseproject.extension.loadImage
import com.soict.hoangviet.firebase.R
import kotlinx.android.synthetic.main.layout_dialog_fragment_zoom_image.*


class ZoomImageDialogFragment private constructor(): BaseDialogFragment(isStyleFullScreen = true) {
    private var imageUrl: String by argument()
    override val mLayoutRes: Int
        get() = R.layout.layout_dialog_fragment_zoom_image

    companion object {
        fun getInstance(imageUrl: String) = ZoomImageDialogFragment().apply {
            this.imageUrl = imageUrl
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun initViews() {
        loadImage()
    }

    private fun loadImage() {
        imv_zoom.loadImage(
            requireContext(),
            imageUrl,
            R.drawable.img_image_default,
            R.drawable.img_image_default
        )
    }

    override fun initListeners() {

    }

}