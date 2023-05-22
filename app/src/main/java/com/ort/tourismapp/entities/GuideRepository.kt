package com.ort.tourismapp.entities

import android.util.Log
import com.google.firebase.firestore.Query
import com.ort.tourismapp.database.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class GuideRepository () {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var guideList : MutableList<Guide> = mutableListOf()
    suspend fun getHomeGuideList(): MutableList<Guide>{
        try{
            val data = database.collection("guias")
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
            val data = database.collection("guias")
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
    fun addGuide(){
        var guide = Guide("", "Maria", "Freire", "mf@g.com", "CABA", "Buenos Aires",
            "Argentina", "URL Foto", 8, mutableListOf())

        database.collection("guias").document().set(guide)
    }
}