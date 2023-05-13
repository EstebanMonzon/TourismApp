package com.ort.tourismapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Guide(
    var uid: String = "",
    var name: String = "",
    var lastname: String = "",
    var email: String = "",
    var city: String = "",
    var province: String = "",
    var country: String = "",
    var guidePhoto : String = "",
    var rate : String = "",
    var activitiesOwnedList: MutableList<Activity?> = mutableListOf()
    ) : Parcelable {
        constructor() : this("","","","","","","","","", mutableListOf())
        init {
            this.uid = uid!!
            this.name = name!!
            this.lastname = lastname!!
            this.email = email!!
            this.city = city!!
            this.province = country!!
            this.country = country !!
            this.guidePhoto = guidePhoto!!
            this.rate = rate !!
            //this.activitiesOwnedList = activitiesOwnedList !!
        }
}