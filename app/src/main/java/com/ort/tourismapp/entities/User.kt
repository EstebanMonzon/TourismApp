package com.ort.tourismapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var uid: String = "",
    var name: String = "",
    var lastname: String = "",
    var email: String = "",
    var password: String = "",
    var profilePhoto: String = "",
    var activitiesLikedList: MutableList<Activity> = mutableListOf()
) : Parcelable {
    constructor() : this("","","","","","", mutableListOf())
    //TODO hacer los constructores de cada objeto para que lo pueda leer la DB de firebase
    //TODO chequear como se pone vacio un mutablelist
    init {
        this.uid = uid!!
        this.name = name!!
        this.lastname = lastname!!
        this.email = email!!
        this.password = password!!
        this.profilePhoto = profilePhoto !!
        //activitiesLikedList va a empezar vacia cuando se registre el usuario
    }


}
