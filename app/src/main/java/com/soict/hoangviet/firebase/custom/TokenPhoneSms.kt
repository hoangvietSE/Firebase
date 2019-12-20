package com.soict.hoangviet.firebase.custom

import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class TokenPhoneSms(var activity: AppCompatActivity, var listener: SendTokenSmsListener) {

    private val mActivity = activity
    private val mSendTokenSmsListener = listener

    // SET DATA PHONE SMS
    //[START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var mUserPhoneNumber: String = ""
    private var tvCode: TextView? = null
    //END SET DATA PHONE SMS

    companion object {
        private const val TAG = "PhoneAuthActivity"
        private const val KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress"
        private const val STATE_INITIALIZED = 1
        private const val STATE_VERIFY_FAILED = 3
        private const val STATE_VERIFY_SUCCESS = 4
        private const val STATE_CODE_SENT = 2
        private const val STATE_SIGNIN_FAILED = 5
        private const val STATE_SIGNIN_SUCCESS = 6
    }

    //begin handle confirm token phone sms
    fun confirmTokenPhoneSms(tvCodeUser: EditText) {
        tvCode = tvCodeUser;
        val code = tvCodeUser.text.toString()
        verifyPhoneNumberWithCode(storedVerificationId, code)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]

        signInWithPhoneAuthCredential(credential)
    }

    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        //START FUNCTION WHEN LOGIN SUCCESS
                        mSendTokenSmsListener.onVerifySmsSuccess()
                        //END FUNCTION WHEN LOGIN SUCCESS
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            // [START_EXCLUDE silent]
                            tvCode!!.error = "Mã code không tồn tại!"
                            mSendTokenSmsListener.onVerifySmsFailed()
                            // [END_EXCLUDE]
                        } else {

                        }
                    }
                }
    }
    // [END sign_in_with_phone]

    fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,      // Phone number to verify
                60,               // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                mActivity,             // Activity (for callback binding)
                callbacks
        ) // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]
        verificationInProgress = true
    }

    fun resendPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,      // Phone number to verify
                60,               // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                mActivity,             // Activity (for callback binding)
                callbacks,
                resendToken
        ) // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]
        verificationInProgress = true
    }

    fun initDataPhoneSms() {
        // [START initialize_auth]
        // Initialize Firebase Auth
        FirebaseApp.initializeApp(mActivity)
        auth = FirebaseAuth.getInstance()
        // [END initialize_auth]
        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                var code: String = credential.smsCode!!
                Log.d(TAG, "onVerificationCompleted:$credential")
                // [START_EXCLUDE silent]
                verificationInProgress = false
                // [END_EXCLUDE]
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)
                // [START_EXCLUDE silent]
                verificationInProgress = false
                // [END_EXCLUDE]

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    // [END_EXCLUDE]
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
//                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
//                        Snackbar.LENGTH_SHORT).show()
                    // [END_EXCLUDE]
                }
                mSendTokenSmsListener.onSendSmsFailed()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId!!)

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
                mSendTokenSmsListener.onSendSmsSuccess()
            }
        }
        // [END phone_auth_callbacks]
    }
    //end handle confirm token phone sms

    interface SendTokenSmsListener {
        fun onVerifySmsSuccess()
        fun onVerifySmsFailed()
        fun onSendSmsSuccess()
        fun onSendSmsFailed()
    }
}