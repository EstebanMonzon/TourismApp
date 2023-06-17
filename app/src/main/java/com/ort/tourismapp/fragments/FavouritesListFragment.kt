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
import com.ort.tourismapp.entities.UserRepository
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
    var activityRepository: ActivityRepository = ActivityRepository()
    var favouritesList: MutableList<Activity>  = mutableListOf()
    var userRepository= UserRepository()
    var allActList : MutableList<Activity> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.fragment_favourites_list, container, false)
        recyclerActivity = v.findViewById(R.id.recActivityFavoritas)

        return v
    }
    override fun onStart() {
        super.onStart()
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {

            favouritesList = userRepository.getFullFavouritesActivities(userRepository.getUserId())

            recyclerActivity.layoutManager = LinearLayoutManager(context)

            adapterActivity = ActivityAdapter(favouritesList,userRepository){ position ->
                val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(allActList[position])
                findNavController().navigate(action)
            }
            recyclerActivity.adapter = adapterActivity
        }

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
        viewModel = ViewModelProvider(this).get(FavouritesListViewModel::class.java)
        // TODO: Use the ViewModel
    }
 //No se si esta bien como lo hice, tampoco si este metodo deberia estar acá, lo cambio en todo caso pero es un avance

}