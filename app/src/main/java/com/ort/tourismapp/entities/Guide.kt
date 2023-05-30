package com.ort.tourismapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Guide(
    var uid: String = "",
    var name: String = "",
    var lastname: String = "",
    var email: String = "",
    var telefono: String = "",
    var city: String = "",
    var guidePhoto : String = "",
    var rate : Int = 0,
    var activitiesOwnedList: MutableList<String> = mutableListOf()
    ) : Parcelable {
        constructor() : this("","","","","","","",0, mutableListOf())
        init {
            this.uid = uid
            this.name = name
            this.lastname = lastname
            this.telefono = telefono
            this.email = email
            this.city = city
            this.guidePhoto = guidePhoto
            this.rate = rate
            this.activitiesOwnedList = activitiesOwnedList
        }
}