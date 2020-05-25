package com.soict.hoangviet.firebase.custom.dialogfragment

import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.soict.hoangviet.baseproject.extension.completableTimer
import com.soict.hoangviet.baseproject.extension.onAvoidDoubleClick
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.utils.AppConstant
import com.soict.hoangviet.firebase.utils.LanguageSharePreference
import kotlinx.android.synthetic.main.layout_dialog_fragment_language.*

class LanguageDialogFragment private constructor() : BaseDialogFragment(isStyleFullScreen = false) {
    override val mLayoutRes: Int
        get() = R.layout.layout_dialog_fragment_language
    private lateinit var listFlagLanguage: ArrayList<RelativeLayout>
    private var onClickLanguageListener: (() -> Unit)? = null

    companion object {
        fun getInstance(): LanguageDialogFragment {
            val languageDialogFragment = LanguageDialogFragment()
            return languageDialogFragment
        }
    }

    override fun initViews() {
        initListFlag()
        initLanguage()
    }

    private fun initListFlag() {
        listFlagLanguage = arrayListOf(rl_vn, rl_en)
    }

    private fun initLanguage() {
        defaultBackground(LanguageSharePreference.getLanguage())
    }

    override fun initListeners() {
        rl_vn.onAvoidDoubleClick {
            defaultBackground(AppConstant.Language.VIETNAMESE)
            LanguageSharePreference.putLanguage(AppConstant.Language.VIETNAMESE)
            completableTimer({
                dismiss()
                onClickLanguageListener?.invoke()
            }, 400L)
        }
        rl_en.onAvoidDoubleClick {
            defaultBackground(AppConstant.Language.ENGLISH)
            LanguageSharePreference.putLanguage(AppConstant.Language.ENGLISH)
            completableTimer({
                dismiss()
                onClickLanguageListener?.invoke()
            }, 400L)
        }
    }

    private fun defaultBackground(lang: String) {
        listFlagLanguage.forEach {
            it.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.md_white_1000))
        }
        when (lang) {
            AppConstant.Language.ENGLISH -> {
                rl_en.setBackgroundResource(R.drawable.bg_language)
            }
            else -> {
                rl_vn.setBackgroundResource(R.drawable.bg_language)
            }
        }
    }

    fun setOnClickLanguageListener(func: () -> Unit) {
        onClickLanguageListener = func
    }
}