package com.ort.tourismapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Guide(
    var id: Int,
    var name: String,
    var lastName: String,
    var email: String,
    var cellphone: String,
    var city: String,
    var province: String,
    var country: String,
    var biography: String,
    var guidePhoto : String,
) : Parcelable {
}