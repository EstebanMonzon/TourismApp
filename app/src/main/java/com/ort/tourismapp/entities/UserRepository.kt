package com.ort.tourismapp.entities

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.database.FirebaseSingleton

class UserRepository {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var favActivityList : MutableList<Activity> = mutableListOf()
    //TODO hacer que retorne un usuario que se pueda trabajar y extraer datos, mañana jueves lo termino :P
    /*fun getUserData(userId: String?): Query {
        return database.collection("usuarios").whereEqualTo("uid", userId).get("name")
    }*/

    //TODO hacer que traiga todas las actividades favoritas de un usuario
    fun getFavouritesActivities(user: User): MutableList<Activity>{
        database.collection("actividades")
            .orderBy("rate", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    favActivityList.add(document.toObject(Activity::class.java))
                }
            }
        return favActivityList
    }

}