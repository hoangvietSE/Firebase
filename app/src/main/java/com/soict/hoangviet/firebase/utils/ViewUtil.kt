package com.soict.hoangviet.firebase.utils

import android.content.res.Resources

object ViewUtil {
    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi
        return px / (densityDpi / 160f)
    }

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().getDisplayMetrics().density
        return Math.round(dp * density)
    }
}