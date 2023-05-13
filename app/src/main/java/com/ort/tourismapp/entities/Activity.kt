package com.ort.tourismapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Activity (
    var uid: String = "",
    var title: String = "",
    var city: String = "",
    var province: String = "",
    var country: String = "",
    var guide: Guide? = null,
    var description: String = "",
    var activityPhoto: String = "",
    var rate: String = "",
    var tags: MutableList<String> = mutableListOf()
    ) : Parcelable {
        constructor() : this("","","","","",null,"","","", mutableListOf())
        init {
            this.uid = uid!!
            this.title = title!!
            this.city = city!!
            this.province = country!!
            this.country = country !!
            this.guide = guide!!
            this.description = description !!
            this.activityPhoto = activityPhoto !!
            this.rate = rate !!
            this.tags = tags !!
        }
    }