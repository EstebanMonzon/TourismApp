package com.ort.tourismapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var uid: String = "",
    var name: String = "",
    var lastname: String = "",
    var telefono: String = "",
    var email: String = "",
    var password: String = "",
    var profilePhoto: String = "",
    var activitiesLikedList: MutableList<String> = mutableListOf()
) : Parcelable {
    constructor() : this("","", "","","","","", mutableListOf())
    init {
        this.uid = uid
        this.name = name
        this.lastname = lastname
        this.telefono = telefono
        this.email = email
        this.password = password
        this.profilePhoto = profilePhoto
        //activitiesLikedList va a empezar vacia cuando se registre el usuario
    }
}
