package com.ort.tourismapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.ActivityRepository
import com.ort.tourismapp.entities.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivitiesListFragment : Fragment() {
    companion object {
        fun newInstance() = ActivitiesListFragment()
    }

    private lateinit var viewModel: ActivitiesListViewModel
    lateinit var v : View

    lateinit var recyclerActivity: RecyclerView
    lateinit var searchView: SearchView
    lateinit var adapterActivity: ActivityAdapter
    lateinit var userRepository: UserRepository
    lateinit var activityRepository: ActivityRepository
    lateinit var activityList: MutableList<Activity>
    lateinit var matchedActivities: MutableList<Activity>
    lateinit var errorBusqueda: TextView
    private lateinit var btnVerEnMapa: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_activities_list, container, false)
        recyclerActivity = v.findViewById(R.id.recActivity)
        searchView = v.findViewById(R.id.searchView_activity)
        btnVerEnMapa = v.findViewById(R.id.btnVerEnMapa)
        activityRepository = ActivityRepository()
        userRepository=UserRepository()
        activityList = mutableListOf()
        matchedActivities = mutableListOf()
        errorBusqueda = v.findViewById(R.id.errorBusqueda_activity)
        return v
    }
    override fun onStart() {
        super.onStart()
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            activityList = activityRepository.getAllActivities()
            recyclerActivity.layoutManager = LinearLayoutManager(context)
            adapterActivity = ActivityAdapter(activityList,userRepository){ position ->

                val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(activityList[position])
                findNavController().navigate(action)
            }
            recyclerActivity.adapter = adapterActivity
        }

        performSearch()

        btnVerEnMapa.setOnClickListener(){
            val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToMapFragment()
            findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivitiesListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun performSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                matchedActivities.clear()
                search(newText)
                return true
            }
        })
    }

    private fun search(text: String) {
        for (activity in activityList){
            if (activity.title.contains(text, true) ||
                activity.tags.toString().contains(text,true)) {
                matchedActivities.add(activity)
            }
        }
        updateRecyclerView()
        if (matchedActivities.isEmpty()) {
            errorBusqueda.text = "No se encontraron resultados para tu búsqueda"
        }
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        adapterActivity = ActivityAdapter(matchedActivities){ position ->
            val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(
                matchedActivities[position]
            )
            findNavController().navigate(action)
        }
        recyclerActivity.adapter = adapterActivity
        adapterActivity.activityList = matchedActivities
        adapterActivity.notifyDataSetChanged()
    }
}