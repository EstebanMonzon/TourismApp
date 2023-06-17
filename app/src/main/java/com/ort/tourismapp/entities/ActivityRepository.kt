package com.ort.tourismapp.entities

import android.util.Log
import com.google.android.gms.maps.model.LatLng
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

    suspend fun getActivityByUid(uid: String): Activity {
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

    suspend fun getActivityByPosition(position: LatLng?): Activity {
        var lat = position?.latitude
        var long = position?.longitude
        var activity = Activity()
        try{
            val data = actividadesCollection.whereEqualTo("lat", lat).whereEqualTo("long", long)
                .limit(1)
                .get()
                .await()
            if (!data.isEmpty) {
                // Process the activity object or handle null case
                activity = data.documents[0].toObject(Activity::class.java)!!
            }
        } catch (e: Exception){
            Log.d("Actividad no cargada", "actividad no cargada")
        }
        return activity
    }
}