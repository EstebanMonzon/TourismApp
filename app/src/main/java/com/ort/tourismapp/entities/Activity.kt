package com.ort.tourismapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Activity (
    var id: Int,
    var title: String,
    var city: String,
    var province: String,
    var country: String,
    var description: String,
    var activityPhoto : String,
    var rate : String,
    ) : Parcelable {
}