package com.soict.hoangviet.firebase.ui.view.impl

import android.annotation.TargetApi
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.soict.hoangviet.firebase.R
import kotlinx.android.synthetic.main.activity_base_web_view.*


abstract class BaseWebView : BaseActivity() {
    override val mLayoutRes: Int?
        get() = R.layout.activity_base_web_view

    protected abstract val webViewUrl: String

    override fun initView() {
        showLoading()
        web_view.settings.javaScriptEnabled = true // enable javascript
        web_view.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView,
                req: WebResourceRequest,
                rerr: WebResourceError
            ) { // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(
                    view,
                    rerr.errorCode,
                    rerr.description.toString(),
                    req.url.toString()
                )
            }
        }
        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                hideLoading()
            }
        }
        web_view.loadUrl(webViewUrl)
    }
}