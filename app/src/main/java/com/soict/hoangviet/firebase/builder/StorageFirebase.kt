package com.soict.hoangviet.firebase.builder

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class StorageFirebase private constructor() {
    data class Builder(
        private var childPath: String? = null,
        private var childChildPath: String? = null,
        private var putFile: Uri? = null,
        private var onCompleteListener: ((Task<Uri>) -> Unit)? = null,
        private var onFailureListener: ((Exception) -> Unit)? = null
    ) {
        fun child(childPath: String) = apply {
            this.childPath?.let {
                this.childChildPath = childPath
            } ?: apply {
                this.childPath = childPath
            }
        }

        fun putFile(putFile: Uri) =
            apply { this.putFile = putFile }

        fun onCompleteListener(onCompleteListener: (Task<Uri>) -> Unit) =
            apply { this.onCompleteListener = onCompleteListener }

        fun onFailureListener(onFailureListener: (Exception) -> Unit) =
            apply { this.onFailureListener = onFailureListener }

        fun build() {
            val fileReference = FirebaseStorage.getInstance().reference
                .child(childPath!!)
            fileReference.putFile(putFile!!)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@continueWithTask fileReference.downloadUrl
                }
                .addOnCompleteListener {
                    onCompleteListener?.invoke(it)
                }
                .addOnFailureListener {
                    onFailureListener?.invoke(it)
                }
        }
    }
}