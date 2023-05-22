package com.ort.tourismapp.entities

import android.util.Log
import com.google.firebase.firestore.Query
import com.ort.tourismapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class GuideRepository () {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var guideList : MutableList<Guide> = mutableListOf()
    private var guiasCollection = database.collection("guias")

    suspend fun getHomeGuideList(): MutableList<Guide>{
        try{
            val data = guiasCollection
                .orderBy("rate", Query.Direction.DESCENDING)
                .limit(2)
                .get().await()
            for (document in data) {
                guideList.add(document.toObject(Guide::class.java))
            }
        } catch (e: Exception){
            Log.d("Guias no cargados: ", guideList.size.toString())
        }
        return guideList
    }

    suspend fun getAllGuides(): MutableList<Guide>{
        try{
            val data = guiasCollection
                .orderBy("rate", Query.Direction.DESCENDING)
                .get().await()
            for (document in data) {
                guideList.add(document.toObject(Guide::class.java))
            }
        } catch (e: Exception){
            Log.d("Guias no cargados: ", guideList.size.toString())
        }
        return guideList

    }

    suspend fun getGuide(guideId: String): Guide {
        var guide = Guide()

        try{
            val data = guiasCollection
                .document(guideId).get().await().toObject(Guide::class.java)
            if (data != null) {
                guide = data
            }
        } catch (e: Exception){
            Log.d("Guia no fue cargados: ", "error de carga de guia")
        }

        return guide
    }

    /*fun addGuide(){
        var guide = Guide("", "Maria", "Freire", "mf@g.com", "CABA", "Buenos Aires",
            "Argentina", "URL Foto", 8, mutableListOf())

        database.collection("guias").document().set(guide)
    }*/
}