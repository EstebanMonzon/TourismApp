package com.ort.tourismapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.entities.ActivityRepository

class ActivitiesListFragment : Fragment() {

    companion object {
        fun newInstance() = ActivitiesListFragment()
    }

    private lateinit var viewModel: ActivitiesListViewModel
    lateinit var v : View

    lateinit var recyclerActivity : RecyclerView
    var repositoriy: ActivityRepository = ActivityRepository()
    lateinit var adapter : ActivityAdapter

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
        adapter = ActivityAdapter(repositoriy.activities){ position ->
            val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(repositoriy.activities[position])
            findNavController().navigate(action)
        }
        recyclerActivity.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivitiesListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}