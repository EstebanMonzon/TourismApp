package com.ort.tourismapp.entities

import com.google.firebase.firestore.Query
import com.ort.tourismapp.database.FirebaseSingleton

class GuideRepository () {
    val database = FirebaseSingleton.getInstance().getDatabase()
    private var guideList : MutableList<Guide> = mutableListOf()

    fun getHomeGuideList(): MutableList<Guide>{
        database.collection("guias")
            .orderBy("rate", Query.Direction.DESCENDING)
            .limit(2)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    guideList.add(document.toObject(Guide::class.java))
                }
            }
        return guideList
    }

    fun getAllGuides(): MutableList<Guide>{
        database.collection("guias")
            .orderBy("rate", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    guideList.add(document.toObject(Guide::class.java))
                }
            }
        return guideList
    }

    fun addGuide(){
        var guide = Guide("", "Maria", "Freire", "mf@g.com", "CABA", "Buenos Aires",
            "Argentina", "URL Foto", 8, mutableListOf())

        database.collection("guias").document().set(guide)
    }
}