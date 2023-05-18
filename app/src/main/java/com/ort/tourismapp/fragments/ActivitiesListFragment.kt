package com.ort.tourismapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.database.FirebaseSingleton
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.ActivityRepository

class ActivitiesListFragment : Fragment() {
    companion object {
        fun newInstance() = ActivitiesListFragment()
    }

    private lateinit var viewModel: ActivitiesListViewModel
    lateinit var v : View

    lateinit var recyclerActivity: RecyclerView
    lateinit var searchView: SearchView
    lateinit var adapterActivity: ActivityAdapter
    lateinit var activityRepository: ActivityRepository
    var activityList: MutableList<Activity>  = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_activities_list, container, false)
        recyclerActivity = v.findViewById(R.id.recActivity)
        activityRepository = ActivityRepository()
        return v
    }
    override fun onStart() {
        super.onStart()
        activityList = activityRepository.getAllActivities()
        searchView = v.findViewById(R.id.searchView_activity)
        recyclerActivity.layoutManager = LinearLayoutManager(context)
        adapterActivity = ActivityAdapter(activityList){ position ->
            val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(activityList[position])
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