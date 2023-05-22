package com.ort.tourismapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.ActivityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesListFragment : Fragment() {

    companion object {
        fun newInstance() = FavouritesListFragment()
    }

    private lateinit var viewModel: FavouritesListViewModel
    lateinit var v : View

    lateinit var recyclerActivity: RecyclerView
    lateinit var adapterActivity: ActivityAdapter
    lateinit var activityRepository: ActivityRepository
    var favouritesList: MutableList<Activity>  = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites_list, container, false)
    }
    override fun onStart() {
        super.onStart()
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            favouritesList = activityRepository.getAllActivities()
            recyclerActivity.layoutManager = LinearLayoutManager(context)
            adapterActivity = ActivityAdapter(favouritesList){ position ->
                val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(favouritesList[position])
                findNavController().navigate(action)
            }
            recyclerActivity.adapter = adapterActivity
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavouritesListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun addActivity(activity : Activity){


    }
}