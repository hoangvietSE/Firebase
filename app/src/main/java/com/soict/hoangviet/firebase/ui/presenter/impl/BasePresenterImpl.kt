package com.soict.hoangviet.firebase.ui.presenter.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.soict.hoangviet.firebase.application.BaseApplication
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.data.sharepreference.AppSharePreference
import com.soict.hoangviet.firebase.ui.interactor.BaseInterator
import com.soict.hoangviet.firebase.ui.presenter.BasePresenter
import com.soict.hoangviet.firebase.ui.view.BaseView
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenterImpl<V : BaseView, I : BaseInterator>(mView: V, mInteractor: I) :
    BasePresenter {
    protected var mView: V? = mView
    protected var mInterator: I? = mInteractor
    protected var mCompositeDisposable: CompositeDisposable? = null
    protected val isAttached get() = mView != null
    private lateinit var userReference: DatabaseReference
    private var userListener: ValueEventListener? = null
    protected var datebaseRef: FirebaseDatabase = FirebaseDatabase.getInstance()
    protected val currentId = AppSharePreference.getInstance(BaseApplication.instance).getUser()?.id

    override fun onAttach() {
        mCompositeDisposable = CompositeDisposable()
        mView?.let {
            it.initView()
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        mCompositeDisposable?.let { it.add(disposable) }
    }

    override fun onDetach() {
        mView = null
        mInterator = null
        mCompositeDisposable?.let { it.clear() }
        userListener?.let {
            userReference.removeEventListener(it)
        }
    }

    protected fun getCurrentUser(listener: RealTimeDatabaseListener<User>) {
        userReference = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
        userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(MainActivity.TAG, "load:success")
                // Get Post object and use the values to update the UI
                val user = dataSnapshot.getValue(User::class.java)
                // [START_EXCLUDE]
                user?.let { listener.onLoadSuccess(it) }
                // [END_EXCLUDE]
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "load:onCancelled", databaseError.toException())
                listener.onLoadError()
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        userReference.addValueEventListener(userListener as ValueEventListener)
    }

    interface RealTimeDatabaseListener<T> {
        fun onLoadSuccess(data: T)
        fun onLoadError()
    }
}