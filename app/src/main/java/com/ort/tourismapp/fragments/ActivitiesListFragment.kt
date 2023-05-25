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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.database.FirebaseSingleton
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
    lateinit var activityRepository: ActivityRepository
    var activityList: MutableList<Activity>  = mutableListOf()
    lateinit var userRepository: UserRepository
    lateinit var userLikedActivities : MutableList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_activities_list, container, false)
        recyclerActivity = v.findViewById(R.id.recActivity)
        activityRepository = ActivityRepository()
        userRepository = UserRepository()
        return v
    }
    override fun onStart() {
        super.onStart()
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            activityList = activityRepository.getAllActivities()
            recyclerActivity.layoutManager = LinearLayoutManager(context)
            adapterActivity = ActivityAdapter(activityList){ position ->
                val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(activityList[position])
                findNavController().navigate(action)
            }
            recyclerActivity.adapter = adapterActivity
        }
        searchView = v.findViewById(R.id.searchView_activity)

        //TODO agregar boton de agregar actividad a favoritos y llamar a userRepository.addFavouriteActivity(uid,activityUid)
        // y userRepository.deleteFavouriteActivity(uid,activityUid)
        /*
        * lo mejor seria hacer esto cuando este la otra app del guia andando
        * deberia poder agregar y borrar de acuerdo a click y cambio de estado del boton
        * solo bordes naranjas es que no esta en favoritos, boton completo naranja esta en favoritos
        */
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivitiesListViewModel::class.java)
        // TODO: Use the ViewModel
    }
}