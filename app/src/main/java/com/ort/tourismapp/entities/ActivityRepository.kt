package com.ort.tourismapp.entities

import android.service.autofill.FieldClassification
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ActivityRepository() {
    var database = Firebase.database.reference
    lateinit var activity: Activity
    var activities : MutableList<Activity> = mutableListOf()



}