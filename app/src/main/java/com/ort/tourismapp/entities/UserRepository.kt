package com.ort.tourismapp.entities

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class UserRepository {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var usersCollection = database.collection("usuarios")
    private var favActivityList : MutableList<Activity> = mutableListOf()
    private var activityRepository: ActivityRepository = ActivityRepository()


    fun getUserId():String{
        return Firebase.auth.currentUser!!.uid
    }

    suspend fun getUserName(userId: String): String {
        return usersCollection.document(userId).get().await().get("name").toString()
    }

    suspend fun getUserAvatar(userId: String): String {
        return usersCollection.document(userId).get().await().get("profilePhoto").toString()
    }

    suspend fun getUserData(userId: String): User {
        return usersCollection.document(userId).get().await().toObject(User::class.java)!!
    }

    //TODO hacer que traiga todas las actividades favoritas de un usuario
    //falta la logica para que a partir de activity iud traiga la actividad en si
    suspend fun getFavouritesActivities(userid: String): MutableList<String>{
        var favActivityList : MutableList<String> = mutableListOf()
        try{

            favActivityList = usersCollection
                .document(userid).get().await().get("activitiesLikedList") as MutableList<String>

        } catch (e: Exception){
            Log.d("Actividades favoritas no cargadas: ", favActivityList.size.toString())
        }
        Log.d("Actividadfav ", favActivityList.size.toString())
        return favActivityList
    }

    fun crearCuenta( uid: String, nombre: String, apellido: String, telefono:String, email: String, password: String, ) {
        usersCollection.document(uid!!)
            .set(User(uid, nombre, apellido, telefono, email, password, "a1", mutableListOf())) //TODO falta agregar la foto de perfil aca
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

    fun updateAvatar(avatarId: String, user: User)
    {
        usersCollection.document(user.uid)
            .update(mapOf("profilePhoto" to avatarId))
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    //TODO chequear si realmente funciona y guarda el uid de la activity en el usuario activo
    suspend fun addFavouriteActivity(userId: String, activityId: String){
        val userRef = usersCollection.document(userId)
        userRef.get().await().let { document ->
            val activitiesLikedList = document.get("activitiesLikedList") as? MutableList<String> ?: mutableListOf()
            activitiesLikedList.add(activityId)
            userRef.update("activitiesLikedList", activitiesLikedList).await()
        }
    }

    suspend fun deleteFavouriteActivity(userId: String, activityId: String){
        val userRef = usersCollection.document(userId)
        userRef.get().await().let { document ->
            val activitiesLikedList = document.get("activitiesLikedList") as? MutableList<String> ?: mutableListOf()
            activitiesLikedList.remove(activityId)
            userRef.update("activitiesLikedList", activitiesLikedList).await()
        }
    }

    suspend fun getFullFavouritesActivities(userId: String): MutableList<Activity> {

        var favListFull: MutableList<Activity> = mutableListOf()
        var favListStrings: MutableList<String> = this.getFavouritesActivities(userId)



        for (fav in favListStrings){
            favListFull.add(activityRepository.getActivity(fav))
            Log.d("getFullFavouritesActivities", favListFull.get(0).title)
        }
        return favListFull


    }
}