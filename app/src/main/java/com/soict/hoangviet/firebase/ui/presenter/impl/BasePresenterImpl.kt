package com.soict.hoangviet.firebase.ui.presenter.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.BaseInterator
import com.soict.hoangviet.firebase.ui.presenter.BasePresenter
import com.soict.hoangviet.firebase.ui.view.BaseView
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity
import com.soict.hoangviet.firebase.utils.AppConstant
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenterImpl<V : BaseView, I : BaseInterator>
internal constructor(
    protected var mInteractor: I?,
    protected var mAppSharePreference: SharePreference?
) : BasePresenter<V, I> {
    protected var mView: V? = null
    protected var mCompositeDisposable: CompositeDisposable? = null
    protected val isAttached get() = mView != null
    private lateinit var userReference: DatabaseReference
    private var userListener: ValueEventListener? = null
    protected var datebaseRef: FirebaseDatabase = FirebaseDatabase.getInstance()
    protected val currentId =
        mAppSharePreference?.get(AppConstant.SharePreference.USER, User::class.java)?.id

    override fun onAttach(view: V?) {
        mCompositeDisposable = CompositeDisposable()
        view?.let {
            mView = it
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        mCompositeDisposable?.let { it.add(disposable) }
    }

    override fun onDetach() {
        mView = null
        mInteractor = null
        mCompositeDisposable?.let { it.clear() }
        userListener?.let {
            userReference.removeEventListener(it)
        }
    }

    override fun getView(): V? {
        return mView
    }

    protected fun getCurrentUser(success: (User) -> Unit, error: () -> Unit) {
        userReference = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
        userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(MainActivity.TAG, "load:success")
                // Get Post object and use the values to update the UI
                val user = dataSnapshot.getValue(User::class.java)
                // [START_EXCLUDE]
                user?.let { success.invoke(it) }
                // [END_EXCLUDE]
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "load:onCancelled", databaseError.toException())
                error.invoke()
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        userReference.addValueEventListener(userListener as ValueEventListener)
    }

    protected fun removeValueListener() {
        userReference.removeEventListener(userListener as ValueEventListener)
    }

    interface RealTimeDatabaseListener<T> {
        fun onLoadSuccess(data: T)
        fun onLoadError()
    }
}