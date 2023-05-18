package com.ort.tourismapp.fragments


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.adapters.GuideAdapter
import com.ort.tourismapp.database.FirebaseSingleton
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.ActivityRepository
import com.ort.tourismapp.entities.Guide
import com.ort.tourismapp.entities.GuideRepository
import com.ort.tourismapp.entities.User
import com.ort.tourismapp.entities.UserRepository

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    lateinit var v: View

    lateinit var recyclerActivity: RecyclerView
    lateinit var adapterActivity: ActivityAdapter
    lateinit var activityRepository: ActivityRepository
    lateinit var guideRepository: GuideRepository
    lateinit var userRepository: UserRepository

    lateinit var recyclerGuide: RecyclerView
    lateinit var adapterGuide: GuideAdapter
    lateinit var searchView: SearchView
    lateinit var txtBienvenidaNombre: TextView
    var txtNombre: String = ""

    lateinit var btnVerEnMapa_home: Button
    lateinit var btnActividadesVerTodo: Button
    lateinit var btnGuidesVerTodo: Button

    val user = Firebase.auth.currentUser
    val userId = user?.uid
    var activityList : MutableList<Activity> = mutableListOf()
    var guideList : MutableList<Guide> = mutableListOf()
    lateinit var userInfo: User
    val database = FirebaseSingleton.getInstance().getDatabase() //traigo  la instancia de la db aca porque todavia
                                                                 //no termine de armar lo del userRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        searchView = v.findViewById(R.id.searchView_home)
        txtBienvenidaNombre = v.findViewById(R.id.txt_Bienvenida)
        recyclerActivity = v.findViewById(R.id.recActivity_home)
        recyclerGuide = v.findViewById(R.id.recGuide_home)
        btnActividadesVerTodo = v.findViewById(R.id.btnActividadesVerTodo)
        btnGuidesVerTodo = v.findViewById(R.id.btnGuidesVerTodo)
        btnVerEnMapa_home = v.findViewById(R.id.btnVerEnMapa_home)
        return v
    }

    override fun onStart() {

        super.onStart()
        getUserData()
        //TODO FALTA TRABAJAR EN LA CLASE USERREPOSITORY
        //userInfo = userRepository.getUserData(userId)
        txtBienvenidaNombre.text = "Bienvenido\n$txtNombre" //aca deberia traer el nombre pero todavia no lo termine

        recyclerActivity.layoutManager = LinearLayoutManager(context)
        recyclerGuide.layoutManager = LinearLayoutManager(context)

        activityList = activityRepository.getHomeActivityList()
        guideList = guideRepository.getHomeGuideList()

        //TODO generar funcion que busque por palabra clave en lista de actividades y guia
        adapterActivity = ActivityAdapter(activityList){ position ->
            val action = HomeFragmentDirections.actionHomeFragmentToActivityDetailFragment(
                activityList[position]
            )
            findNavController().navigate(action)
        }
        recyclerActivity.adapter = adapterActivity

        adapterGuide = GuideAdapter(guideList){ position ->
            val action = HomeFragmentDirections.actionHomeFragmentToGuideDetailFragment(
                guideList[position])
            findNavController().navigate(action)
        }
        recyclerGuide.adapter = adapterGuide

        /*btnVerEnMapa_home.setOnClickListener(){ //TODO
            val action = HomeFragmentDirections
            findNavController().navigate(action)
        }*/

        btnActividadesVerTodo.setOnClickListener(){
            val action = HomeFragmentDirections.actionHomeFragmentToActivitiesListFragment()
            findNavController().navigate(action)
        }

        btnGuidesVerTodo.setOnClickListener(){
            val action = HomeFragmentDirections.actionHomeFragmentToGuideListFragment()
            findNavController().navigate(action)
        }

        //TODO cards redirect to activity or guide
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private fun getUserData(){
        database.collection("users").whereEqualTo("uid", userId)
    }

    //TODO chequear que datos guardar del guia en una actividad, solo uid tal vez?
    //TODO HACER METODO DE SALIR DE USUARIO (Ver documentacion de google)
    //TODO usar Storage y Guide para guardar las fotos subidas de cada actividad que cree el guia en su app (PARA APP GUIA)
    //TODO hacer que botones conecten con lista de actividades y lista de guias
    /*
    * //TODO traer actividades desde base de datos
        //TODO mostrar dos actividades, usar esto citiesRefs.orderBy("rate").limit(2)
        //TODO traer guias desde base de datos
        //TODO mostrar dos guias
     */

}





