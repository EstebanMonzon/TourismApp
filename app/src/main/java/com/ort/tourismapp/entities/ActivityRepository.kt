package com.ort.tourismapp.entities

import android.util.Log
import androidx.lifecycle.liveData
import com.google.firebase.firestore.Query
import com.ort.tourismapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class ActivityRepository() {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var activityList : MutableList<Activity> = mutableListOf()
    suspend fun getHomeActivityList(): MutableList<Activity> {
        try{
            val data = database.collection("actividades")
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
            val data = database.collection("actividades")
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

    /*fun addActivity(guide:Guide) {
        var activity = Activity("Caminito", "CABA", "Buenos Aires", "Argentina", "guide" ,
            "Hola soy la actividad Caminito", "URL Foto", 8)

        database.collection("actividades").document().set(activity)
    }*/

}