package com.ort.tourismapp.entities

import android.util.Log
import com.google.firebase.firestore.Query
import com.ort.tourismapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class GuideRepository () {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var guiasCollection = database.collection("guias")
    private var guideList: MutableList<Guide> = mutableListOf()
    var activityRepository = ActivityRepository()

    suspend fun getHomeGuideList(): MutableList<Guide> {
        val guideList = mutableListOf<Guide>()
        try{
            val data = guiasCollection
                .orderBy("rate", Query.Direction.DESCENDING)
                .limit(2)
                .get().await()
            for(document in data){
                guideList.add(document.toObject(Guide::class.java))
            }
        } catch (e: Exception){
            Log.d("Actividades no cargadas: ", guideList.size.toString())
        }
        return guideList
    }

    suspend fun getAllGuides(): MutableList<Guide>{
        try{
            val data = database.collection("guias")
                .orderBy("rate", Query.Direction.DESCENDING)
                .get().await()
            if (data != null) {
                for (document in data) {
                    guideList.add(document.toObject(Guide::class.java))
                }
            }
        } catch (e: Exception){
            Log.d("Guias no cargados: ", guideList.size.toString())
        }
        return guideList
    }

    suspend fun getGuide(guideId: String): Guide {
        var guide = Guide()
        try{
            val data = database.collection("guias").document(guideId)
                .get().await().toObject(Guide::class.java)
            if (data != null) {
                guide = data
            }
        } catch (e: Exception){
            Log.d("Guia no fue cargados: ", "error de carga de guia")
        }
        return guide
    }

    suspend fun getAllActivitiesGuide(guideId: String): MutableList<Activity> {
        var activitiesGuideList: MutableList<Activity> = mutableListOf()

        try{
            var uidsList: MutableList<String>
            val data = database.collection("guias")
                .document(guideId).get().await().get("activitiesOwnedList")

            if (data != null) {
                uidsList = data as MutableList<String>
                for(uid in uidsList){
                    activitiesGuideList.add(activityRepository.getActivity(uid))
                }
            }
        } catch (e: Exception){
            Log.d("Actividades de guia no fueron cargadas: ", "error de carga de actividades")
        }

        return activitiesGuideList
    }

    /*fun addGuide(){
        var guide = Guide("", "Maria", "Freire", "mf@g.com", "CABA", "Buenos Aires",
            "Argentina", "URL Foto", 8, mutableListOf())

        database.collection("guias").document().set(guide)
    }*/
}
