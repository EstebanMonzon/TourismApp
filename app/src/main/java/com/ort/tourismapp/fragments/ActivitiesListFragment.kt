package com.ort.tourismapp.fragments

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.GuideRepository

class ActivitiesListFragment : Fragment() {
    companion object {
        fun newInstance() = ActivitiesListFragment()
    }

    private lateinit var viewModel: ActivitiesListViewModel
    lateinit var v : View

    lateinit var recyclerActivity : RecyclerView
    lateinit var adapterActivity : ActivityAdapter

    val db = Firebase.firestore
    var activitiesList : MutableList<Activity> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_activities_list, container, false)
        recyclerActivity = v.findViewById(R.id.recActivity)
        return v
    }
    override fun onStart() {
        super.onStart()
        recyclerActivity.layoutManager = LinearLayoutManager(context)

        var activity = Activity("1", "Usina del Arte", "CABA",
            "Buenos Aires", "Argentina", null, "Soy la usina del arte :)",
            "fsdfsdf","7", mutableListOf("buenos aires", "usina", "arte"))
        var activity2 = Activity("1", "Estadio de Boca Jrs", "CABA",
            "Buenos Aires", "Argentina", null, "Hola soy la actividad Estadio de Boca Jrs",
            "fsdfsdf","6,5", mutableListOf("buenos aires", "boca", "estadio"))

        db.collection("activities").add(activity)
        db.collection("activities").add(activity2)

        //trae lista de datos
        db.collection("activities")
//             .whereEqualTo("tipo", "PERRO")
            .limit(20)
            .orderBy("rate")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (activity in snapshot) {
                        activitiesList.add(activity.toObject())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        adapterActivity = ActivityAdapter(activitiesList){ position ->
            val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(
                activitiesList[position]
            )
            findNavController().navigate(action)
        }
        recyclerActivity.adapter = adapterActivity
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivitiesListViewModel::class.java)
        // TODO: Use the ViewModel
    }
}