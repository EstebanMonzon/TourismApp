package com.ort.tourismapp.database

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseSingleton {
    companion object {
        private var instance: FirebaseSingleton? = null
        fun getInstance(): FirebaseSingleton {
            if (instance == null) {
                instance = FirebaseSingleton()
            }
            return instance!!
        }
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getDatabase(): FirebaseFirestore {
        return firebaseDatabase
    }
}