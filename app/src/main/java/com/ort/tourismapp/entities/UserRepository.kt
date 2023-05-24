package com.ort.tourismapp.entities

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class UserRepository {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var usersCollection = database.collection("usuarios")
    private var favActivityList : MutableList<String> = mutableListOf()

    suspend fun getUserName(userId: String): String {
        return usersCollection.document(userId).get().await().get("name").toString()
    }

    suspend fun getUserData(userId: String): User {
        return usersCollection.document(userId).get().await().toObject(User::class.java)!!
    }

    //TODO hacer que traiga todas las actividades favoritas de un usuario
    //falta la logica para que a partir de activity iud traiga la actividad en si
    suspend fun getFavouritesActivities(userid: String): MutableList<String>{
        try{
            val data = database.collection("usuarios")
                .document(userid)
                .collection("activitiesLikedList")
                .orderBy("rate", Query.Direction.DESCENDING)
                .get().await().toObjects(String::class.java)

        } catch (e: Exception){
            Log.d("Actividades favoritas no cargadas: ", favActivityList.size.toString())
        }
        return favActivityList
    }

    fun crearCuenta( uid: String, nombre: String, apellido: String, telefono:String, email: String, password: String, ) {
        usersCollection.document(uid!!)
            .set(User(uid, nombre, apellido, telefono, email, password, "", mutableListOf())) //TODO falta agregar la foto de perfil aca
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${uid}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

     fun updateUser(nombre: String, apellido: String, telefono: String,user: User){
         usersCollection.document(user.uid)
             .update(mapOf("name" to nombre, "lastname" to apellido, "telefono" to telefono))
             .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
             .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }

    fun updatePassword(userPassNew: String, user: User) {
        Firebase.auth.currentUser!!.updatePassword(userPassNew)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    usersCollection.document(user.uid)
                        .update(mapOf("password" to userPassNew))
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                    Log.d(TAG, "User password updated.")
                }
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    //TODO chequear si realmente funciona y guarda el uid de la activity en el usuario activo
    suspend fun addDeleteFavouriteActivity(userId: String, activityId: String){
        Log.d("addDeleteFavouriteActivity","entra addDeleteFavouriteActivity")
        val userRef = database.collection("users").document(userId)

        var activitiesLikedList = getFavouritesActivities(userId)
        Log.d("addDeleteFavouriteActivity","Ahora hay " + activitiesLikedList.size.toString()+" en favoritas")
        // Trae tamaño de lista 0, osea trae bien la lista de la ddbb

        val containsLikedActivity : Boolean = activitiesLikedList.contains(activityId)
        Log.d("addDeleteFavouriteActivity",containsLikedActivity.toString() + " contiene actividad")

        if(!containsLikedActivity){

            Log.d("addDeleteFavouriteActivity","entra add")
            activitiesLikedList.add(activityId)


        }
        if(containsLikedActivity){
            Log.d("addDeleteFavouriteActivity","entra delete")
            activitiesLikedList.remove(activityId)
        }

        Log.d("addDeleteFavouriteActivity",userRef.id)
        //Devuelve el userId correcto


        userRef.update("activitiesLikedList", activitiesLikedList)


        Log.d("addDeleteFavouriteActivity","Ahora hay " + activitiesLikedList.size.toString()+" en favoritas")


        //AGREGA Y BORRA LAS FAVORITAS A LA BASE PERO NO SE VEN EN LA BASE, NO ENTIENDO"


    }

//    CODIGO VIEJO
//    suspend fun addFavouriteActivity(userId: String, activityId: String){
//        val userRef = database.collection("users").document(userId)
//        userRef.get().await().let { document ->
//            val activitiesLikedList = document.get("activitiesLikedList") as? MutableList<String> ?: mutableListOf()
//            activitiesLikedList.add(activityId)
//            userRef.update("activitiesLikedList", activitiesLikedList).await()
//        }
//    }
//
//    suspend fun deleteFavouriteActivity(userId: String, activityId: String){
//        val userRef = database.collection("users").document(userId)
//        userRef.get().await().let { document ->
//            val activitiesLikedList = document.get("activitiesLikedList") as? MutableList<String> ?: mutableListOf()
//            activitiesLikedList.remove(activityId)
//            userRef.update("activitiesLikedList", activitiesLikedList).await()
//        }
//    }

}