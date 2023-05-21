package com.ort.tourismapp.entities

import com.google.firebase.firestore.Query
import com.ort.tourismapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class ActivityRepository() {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var activityList : MutableList<Activity> = mutableListOf()

    /*Intente usar corutinas solo en este metodo*/
    suspend fun getHomeActivityList(): MutableList<Activity> {
        activityList = database.collection("actividades")
            .orderBy("rate", Query.Direction.DESCENDING)
            .limit(2)
            .get().await().toObjects(Activity::class.java)

        return activityList
    }

    fun getAllActivities(): MutableList<Activity> {
        database.collection("actividades")
            .orderBy("rate", Query.Direction.DESCENDING)
            .limit(2)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    activityList.add(document.toObject(Activity::class.java))
                }
            }
        return activityList
    }

    fun addActivity(guide:Guide) {
        var activity = Activity("Caminito", "CABA", "Buenos Aires", "Argentina", guide ,
            "Hola soy la actividad Caminito", "URL Foto", 8)

        database.collection("actividades").document().set(activity)
    }

}