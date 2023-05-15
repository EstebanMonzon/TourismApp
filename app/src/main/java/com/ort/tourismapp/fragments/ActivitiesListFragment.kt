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
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.adapters.GuideAdapter
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.Guide
import com.ort.tourismapp.entities.GuideRepository

class ActivitiesListFragment : Fragment() {
    companion object {
        fun newInstance() = ActivitiesListFragment()
    }

    private lateinit var viewModel: ActivitiesListViewModel
    lateinit var v : View

    lateinit var recyclerActivity : RecyclerView
    lateinit var adapterActivity : ActivityAdapter

    val database = Firebase.database.reference

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

        //trae lista de datos
        database.child("actividades").get().addOnCompleteListener {
            if (it.isSuccessful) {
                var activitiesList = it.result.value as MutableList<Activity>
                //puebla las cards
                adapterActivity = ActivityAdapter(activitiesList){ position ->
                    val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(
                        activitiesList[position]
                    )
                    findNavController().navigate(action)
                }
                recyclerActivity.adapter = adapterActivity
            } else {
                Log.d(TAG, it.exception?.message.toString())
            }
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivitiesListViewModel::class.java)
        // TODO: Use the ViewModel
    }
}