package com.ort.tourismapp.entities

import android.util.Log
import com.google.firebase.firestore.Query
import com.ort.tourismapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class ActivityRepository() {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var actividadesCollection = database.collection("actividades")
    private var activityList : MutableList<Activity> = mutableListOf()

    suspend fun getHomeActivityList(): MutableList<Activity> {
        try{
            val data = actividadesCollection
                .orderBy("rate", Query.Direction.DESCENDING)
                .limit(2)
                .get().await()
            for(document in data){
                activityList.add(document.toObject(Activity::class.java))
            }
        } catch (e: Exception){
            Log.d("Actividades no cargadas: ", activityList.size.toString())
        }
        return activityList
    }

    suspend fun getAllActivities(): MutableList<Activity> {
        try{
            val data = actividadesCollection
                .orderBy("rate", Query.Direction.DESCENDING)
                .get().await()
            for(document in data){
                activityList.add(document.toObject(Activity::class.java))
            }
        } catch (e: Exception){
            Log.d("Actividades no cargadas: ", activityList.size.toString())
        }
        return activityList
    }

    suspend fun getActivity(uid: String): Activity {
        var activity = Activity()
        try{
            val data = actividadesCollection.document(uid)
                .get().await().toObject(Activity::class.java)!!
            if (data != null) {
                activity = data
            }
        } catch (e: Exception){
            Log.d("Actividad no cargada", "actividad no cargada")
        }
        return activity
    }

    /*fun addActivity(guide:Guide) {
        var activity = Activity("Caminito", "CABA", "Buenos Aires", "Argentina", "guide" ,
            "Hola soy la actividad Caminito", "URL Foto", 8)

        database.collection("actividades").document().set(activity)
    }*/

}