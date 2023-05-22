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
    var province: String = "",
    var country: String = "",
    var guidePhoto : String = "",
    var rate : Int = 0,
    var activitiesOwnedList: MutableList<Activity?> = mutableListOf()
    ) : Parcelable {
        constructor() : this("","","", "","","","","","",0, mutableListOf())
        init {
            this.uid = uid
            this.name = name
            this.lastname = lastname
            this.telefono = telefono
            this.email = email
            this.city = city
            this.province = province
            this.country = country
            this.guidePhoto = guidePhoto
            this.rate = rate
            //activitiesOwnedList inicia vacia
        }
}