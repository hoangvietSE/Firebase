package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.soict.hoangviet.baseproject.extension.toast
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.ui.interactor.impl.DynamicLinkInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.ConfirmPresenter
import com.soict.hoangviet.firebase.ui.presenter.DynamicLinkPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.DynamicLinkPresenterImpl
import com.soict.hoangviet.firebase.ui.view.DynamicLinkView
import kotlinx.android.synthetic.main.activity_dynamiclink.*
import javax.inject.Inject

class DynamicLinkActivity : BaseActivity(), DynamicLinkView {
    companion object {
        val TAG = DynamicLinkActivity::class.java.simpleName
    }

    @Inject
    lateinit var mPresenter: DynamicLinkPresenter

    override val mLayoutRes: Int
        get() = R.layout.activity_dynamiclink

    override fun initView() {
        mPresenter.onAttach(this)
    }

    override fun initListener() {
        btn_share.setOnClickListener {
            createDynamicLink()
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    fun createDynamicLink() {
        // [START create_link_basic]
        showLoading()
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://soict.page.link?id=18091998"))
                .setDomainUriPrefix("https://soict.page.link")
                // Open links with this app on Android
                .setAndroidParameters(
                        DynamicLink.AndroidParameters.Builder("com.soict.hoangviet.firebase")
                                .setFallbackUrl(Uri.parse("https://www.soict.hust.edu.vn/"))
                                .setMinimumVersion(0)
                                .build()
                )
                .setSocialMetaTagParameters(
                        DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Firebase DynamicLink Github Quick-Start")
                                .setDescription("This link works whether the app is installed or not!")
                                .setImageUrl(Uri.parse("https://is3-ssl.mzstatic.com/image/thumb/Purple123/v4/bb/51/8c/bb518cbe-5f88-d8e9-543b-959e63f949c9/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-85-220.jpeg/1200x630wa.png"))
                                .build()
                )
                .buildShortDynamicLink()
                .addOnSuccessListener {
                    hideLoading()
                    // Short link created
                    val shortLink = it.shortLink
                    shareLink(shortLink)
                }
                .addOnFailureListener {
                    hideLoading()
                    toast(resources.getString(R.string.error_happen))
                }
        // [END create_link_basic]
    }

    private fun shareLink(shortLink: Uri?) {
        // [START ddl_share_link]
        val intentShareSocial = Intent()
        intentShareSocial.action = Intent.ACTION_SEND
        intentShareSocial.type = "text/plain"
        intentShareSocial.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
        startActivity(Intent.createChooser(intentShareSocial, resources.getString(R.string.share_option)));
        // [END ddl_share_link]
    }

    override fun onResume() {
        super.onResume()
        receiveDynamicLink()
    }

    private fun receiveDynamicLink() {
        // [START get_deep_link]
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { pendingDynamicLinkData ->
                    // Get deep link from result (may be null if no link is found)
                    var deepLink: Uri? = null
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link
                    }

                    // Handle the deep link. For example, open the linked
                    // content, or apply promotional credit to the user's
                    // account.
                    // ...

                    // [START_EXCLUDE]
                    deepLink?.let {
                        val id = deepLink.getQueryParameter("id");
                        id?.let { Snackbar.make(findViewById(android.R.id.content), "Found deep link with id = ${id}", Snackbar.LENGTH_LONG).show() }
                    } ?: run {
                        // Log.d(TAG, "getDynamicLink: no link found")
                    }
                    // [END_EXCLUDE]
                }
                .addOnFailureListener(this) { e -> Log.w(TAG, "getDynamicLink:onFailure", e) }
        // [END get_deep_link]
    }
}