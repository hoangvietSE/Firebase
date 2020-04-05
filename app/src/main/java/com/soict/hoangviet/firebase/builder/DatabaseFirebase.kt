package com.soict.hoangviet.firebase.builder

import com.google.firebase.database.*

class DatabaseFirebase private constructor() {
    data class Builder(
        private var referencePath: String? = null,
        private var childPath: String? = null,
        private var childChildPath: String? = null,
        private var onDataChange: ((DataSnapshot) -> Unit)? = null,
        private var onCancelled: ((DatabaseError) -> Unit)? = null
    ) {
        fun reference(referencePath: String) = apply { this.referencePath = referencePath }
        fun child(childPath: String) = apply {
            this.childPath?.let {
                this.childChildPath = childPath
            } ?: apply {
                this.childPath = childPath
            }
        }

        fun onDataChange(onDataChange: (DataSnapshot) -> Unit) =
            apply { this.onDataChange = onDataChange }

        fun onCancelled(onCancelled: (DatabaseError) -> Unit) =
            apply { this.onCancelled = onCancelled }

        fun build(): Pair<DatabaseReference, ValueEventListener> {
            val databaseReference =
                FirebaseDatabase.getInstance()
                    .getReference(referencePath ?: "")
                    .child(childPath ?: "")
                    .child(childChildPath ?: "")
            val valueListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    onDataChange?.let { it.invoke(dataSnapshot) }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    onCancelled?.let { it.invoke(databaseError) }
                }
            }
            databaseReference.addValueEventListener(valueListener)
            return Pair(databaseReference, valueListener)
        }
    }
}