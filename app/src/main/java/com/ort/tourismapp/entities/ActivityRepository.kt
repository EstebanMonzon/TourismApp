package com.ort.tourismapp.entities

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ActivityRepository() {
    val database = Firebase.database.reference
    lateinit var activity: Activity
    var activities : MutableList<Activity> = mutableListOf()

    fun getActivityList(){
        val rootRef = FirebaseDatabase.getInstance().reference
        val studentsListRef = rootRef.child("actividades")
        val query: Query = studentsListRef.orderByChild("rate")
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    activity = ds.getValue(Activity::class.java)!!
                    activities.add(activity)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        query.addListenerForSingleValueEvent(eventListener)
    }


}