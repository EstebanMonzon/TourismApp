package com.ort.tourismapp.entities

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class UserRepository {
    /*private lateinit var database: DatabaseReference

    database = Firebase.database.reference

    //TODO hacer que traiga datos de Firebase
    //getUser(id) {}
    //updateUser (User o id ?) {}
    private fun updateUser(User user) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        val key = database.child("users").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for posts")
            return
        }

        val post = Post(userId, username, title, body)
        val postValues = post.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/posts/$key" to postValues,
            "/user-posts/$userId/$key" to postValues
        )

        database.updateChildren(childUpdates)
    }
    //getFavoriteList(){}*/

}