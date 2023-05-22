package com.ort.tourismapp.entities

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ort.tourismapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class UserRepository {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var favActivityList : MutableList<Activity> = mutableListOf()

    suspend fun getUserName(userId: String): String {
        return database.collection("usuarios").document(userId).get().await().get("name").toString()
    }

    //TODO hacer que traiga todas las actividades favoritas de un usuario
    //falta la logica para que a partir de activity iud traiga la actividad en si
    suspend fun getFavouritesActivities(user: User): MutableList<Activity>{
        try{
            val data = database.collection("usuarios")
                .document(user.uid)
                .collection("activitiesLikedList")
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

    fun crearCuenta(email: String, password: String, nombre: String, apellido: String, uid: String) {
        database.collection("usuarios").document(uid!!)
            .set(User(uid, nombre, apellido, email, password, "", mutableListOf())) //TODO falta agregar la foto de perfil aca
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${uid}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }
    //TODO chequear si realmente funciona y guarda el uid de la activity en el usuario activo
    suspend fun addFavouriteActivity(userId: String, activityId: String){
        val userRef = database.collection("users").document(userId)
        userRef.get().await().let { document ->
            val activitiesLikedList = document.get("activitiesLikedList") as? MutableList<String> ?: mutableListOf()
            activitiesLikedList.add(activityId)
            userRef.update("activitiesLikedList", activitiesLikedList).await()
        }
    }

    suspend fun deleteFavouriteActivity(userId: String, activityId: String){
        val userRef = database.collection("users").document(userId)
        userRef.get().await().let { document ->
            val activitiesLikedList = document.get("activitiesLikedList") as? MutableList<String> ?: mutableListOf()
            activitiesLikedList.remove(activityId)
            userRef.update("activitiesLikedList", activitiesLikedList).await()
        }
    }

}