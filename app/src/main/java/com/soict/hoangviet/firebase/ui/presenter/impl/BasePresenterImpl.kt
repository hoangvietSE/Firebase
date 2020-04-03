package com.soict.hoangviet.firebase.ui.presenter.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.soict.hoangviet.firebase.data.network.ApiConstant
import com.soict.hoangviet.firebase.data.network.ApiError
import com.soict.hoangviet.firebase.data.network.api.ApiException
import com.soict.hoangviet.firebase.data.network.api.NetworkConnectionInterceptor
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.BaseInterator
import com.soict.hoangviet.firebase.ui.presenter.BasePresenter
import com.soict.hoangviet.firebase.ui.view.BaseView
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity
import com.soict.hoangviet.firebase.utils.AppConstant
import com.soict.hoangviet.firebase.utils.ToastUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException
import java.lang.IllegalStateException
import java.net.SocketException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

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
    protected var currentId: String? = null

    override fun onAttach(view: V?) {
        currentId = mAppSharePreference?.get(AppConstant.SharePreference.USER, User::class.java)?.id
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

    private fun <T> gsonFromJson(json: String?, classOfT: Class<T>): T {
        return Gson().fromJson(json, classOfT) ?: throw Exception()
    }

    protected fun handleThrowable(throwable: Throwable) {
        var apiError: ApiError? = null
        var apiException: ApiException? = null
        when (throwable) {
            is NetworkConnectionInterceptor.NoConnectivityException -> {
                apiError = ApiError(throwable.message.toString())
            }
            is HttpException -> {
                //convert to apiError
                try {
                    apiError = gsonFromJson(
                        throwable.response().errorBody().toString(),
                        ApiError::class.java
                    )
                } catch (jfe: JsonParseException) {
                    apiError = ApiError(ApiConstant.HttpMessage.TIME_OUT)
                } catch (ioe: IOException) {
                    apiError = ApiError(ApiConstant.HttpMessage.TIME_OUT)
                } catch (ile: IllegalStateException) {
                    apiError = ApiError(ApiConstant.HttpMessage.TIME_OUT)
                }
            }
            is TimeoutException, is SocketException, is UnknownHostException -> {
                apiError = ApiError(ApiConstant.HttpMessage.TIME_OUT)
            }
        }
        apiError?.let {
            apiException = it.getApiException()
        } ?: kotlin.run {
            apiException = ApiException()
        }
        ToastUtil.show(apiException?.message ?: ApiConstant.HttpMessage.ERROR_TRY_AGAIN)
    }
}