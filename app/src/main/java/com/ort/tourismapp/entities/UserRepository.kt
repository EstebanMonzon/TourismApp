package com.ort.tourismapp.entities

import android.util.Log
import com.google.firebase.firestore.Query
import com.ort.tourismapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class UserRepository {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var favActivityList : MutableList<Activity> = mutableListOf()
    //TODO hacer que retorne un usuario que se pueda trabajar y extraer datos
    /*fun getUserData(userId: String?): Query {
        return database.collection("usuarios").whereEqualTo("uid", userId).get("name")
    }*/

    //TODO hacer que traiga todas las actividades favoritas de un usuario
    //falta la logica para que a partir de activity iud traiga la actividad en si
    suspend fun getFavouritesActivities(user: User): MutableList<Activity>{
        try{
            val data = database.collection("usuarios")
                .document(user.uid)
                .collection("favActivityList")
                .orderBy("rate", Query.Direction.DESCENDING)
                .get().await()
            for (document in data) {
                favActivityList.add(document.toObject(Activity::class.java))
            }
        } catch (e: Exception){
            Log.d("Actividades favoritas no cargados: ", favActivityList.size.toString())
        }
        return favActivityList

    }

}