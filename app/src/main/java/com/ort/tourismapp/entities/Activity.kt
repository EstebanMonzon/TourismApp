package com.ort.tourismapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Activity (
    var title: String = "",
    var city: String = "",
    var province: String = "",
    var country: String = "",
    var guide: Guide? = null,
    var description: String = "",
    var activityPhoto: String = "",
    var rate: Int = 0 ,
    var tags: MutableList<String> = mutableListOf()
    ) : Parcelable {
        constructor() : this("","","","",null,"","",0, mutableListOf())
        init {
            this.title = title
            this.city = city
            this.province = country
            this.country = country
            this.guide = guide
            this.description = description
            this.activityPhoto = activityPhoto
            this.rate = rate
            this.tags = tags
        }
    }