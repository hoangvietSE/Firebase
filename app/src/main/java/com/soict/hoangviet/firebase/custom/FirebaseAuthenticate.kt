package com.soict.hoangviet.firebase.custom

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.soict.hoangviet.firebase.data.network.request.LoginRequest
import com.soict.hoangviet.firebase.data.network.request.RegisterRequest

class FirebaseAuthenticate(val mListener: AuthenticateCallBack) {
    companion object {
        val TAG = FirebaseAuthenticate::class.java.simpleName
    }

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var reference: DatabaseReference? = null

    fun register(registerRequest: RegisterRequest) {
        auth.createUserWithEmailAndPassword(registerRequest.email, registerRequest.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val userId = user?.uid
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!);
                    val userRecored = mutableMapOf<String, String>()
                    userRecored.put("id", userId)
                    userRecored.put("fullname", registerRequest.fullName)
                    userRecored.put("email", registerRequest.email)
                    userRecored.put("phone", registerRequest.phoneNumber)
                    reference?.setValue(userRecored)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            mListener.onAuthSuccess()
                        }
                    }
                } else {
                    mListener.onAuthError()
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)

                }
            }
            .addOnFailureListener {
                mListener.onAuthError()
                Log.w(TAG, "createUserWithEmail:exception", it)
            }
    }

    fun login(loginRequest: LoginRequest) {
        auth.signInWithEmailAndPassword(loginRequest.email, loginRequest.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    mListener.onAuthSuccess()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", it.exception)
                    mListener.onAuthError()
                }
                // ...
            }
            .addOnFailureListener {
                Log.w(TAG, "createUserWithEmail:exception", it)
                mListener.onAuthError()
            }
    }

    fun logout() {
        auth.signOut()
    }
}