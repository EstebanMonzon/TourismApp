package com.ort.tourismapp.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.adapters.GuideAdapter
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.Guide
import com.ort.tourismapp.entities.User

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    lateinit var v: View

    lateinit var recyclerActivity: RecyclerView
    lateinit var adapterActivity: ActivityAdapter

    lateinit var recyclerGuide: RecyclerView
    lateinit var adapterGuide: GuideAdapter
    lateinit var searchView: SearchView
    lateinit var txtBienvenidaNombre: TextView
    var txtNombre: String = ""

    lateinit var btnVerEnMapa_home: Button
    lateinit var btnActividadesVerTodo: Button
    lateinit var btnGuidesVerTodo: Button

    val user = Firebase.auth.currentUser
    val database = Firebase.database.reference
    val userId = user?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        searchView = v.findViewById(R.id.searchView_home)
        txtBienvenidaNombre = v.findViewById(R.id.txt_Bienvenida)
        recyclerActivity = v.findViewById(R.id.recActivity)
        recyclerGuide = v.findViewById(R.id.recGuide)
        btnActividadesVerTodo = v.findViewById(R.id.btnActividadesVerTodo)
        btnGuidesVerTodo = v.findViewById(R.id.btnGuidesVerTodo)
        btnVerEnMapa_home = v.findViewById(R.id.btnVerEnMapa_home)
        return v
    }

    override fun onStart() {
        super.onStart()
        getUserData()
        txtBienvenidaNombre.text = "Bienvenido\n$txtNombre"

        //addActivitiesyGuides()
        recyclerActivity.layoutManager = LinearLayoutManager(context)
        recyclerGuide.layoutManager = LinearLayoutManager(context)
        getActivityList()
        getGuideList()
        //TODO generar funcion que busque por palabra clave en lista de actividades y guia


        //TODO
        /*btnVerEnMapa_home.setOnClickListener(){
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
        val database = FirebaseDatabase.getInstance().getReference("users")

        if (userId != null) {
            database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    txtNombre = user?.name.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
    private fun addActivitiesyGuides() {

        var guide = Guide("1", "Eloy", "Saavedra", "ea@g.com", "CABA", "Buenos Aires",
            "Argentina", "URL FOTO", 7, mutableListOf())
        var guide2 = Guide("2", "Maria", "Freire", "mf@g.com", "CABA", "Buenos Aires",
            "Argentina", "URL Foto", 8, mutableListOf())

        /*database.getReference("guias").child(guide.uid).setValue(guide)
        database.getReference("guias").child(guide2.uid).setValue(guide2)*/

        var activity = Activity("1", "Usina del Arte", "CABA",
            "Buenos Aires", "Argentina", guide, "Soy la usina del arte :)",
            "fsdfsdf",8, mutableListOf("buenos aires", "usina", "arte"))
        var activity2 = Activity("2", "Estadio de Boca Jrs", "CABA",
            "Buenos Aires", "Argentina", guide2, "Hola soy la actividad Estadio de Boca Jrs",
            "fsdfsdf",6, mutableListOf("buenos aires", "boca", "estadio"))
        var activity3 = Activity("3", "Museo de bellas Artes", "CABA", "Buenos Aires", "Argentina", guide,
            "Hola soy la actividad Museo de bellas Artes", "URL Foto", 7)
        var activity4 = Activity("4", "Caminito", "CABA", "Buenos Aires", "Argentina", guide2 ,
            "Hola soy la actividad Caminito", "URL Foto", 8)

        /*database.getReference("actividades").child(activity.uid).setValue(activity)
        database.getReference("actividades").child(activity2.uid).setValue(activity2)
        database.getReference("actividades").child(activity3.uid).setValue(activity3)
        database.getReference("actividades").child(activity4.uid).setValue(activity4)*/
    }

    private fun getActivityList(){
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
                Log.d(ContentValues.TAG, it.exception?.message.toString())
            }
        }
    }

    private fun getGuideList(){
        database.child("guias").get().addOnCompleteListener {
            if (it.isSuccessful) {
                var guidesList = it.result.value as MutableList<Guide>
                //puebla las cards
                adapterGuide = GuideAdapter(guidesList){ position ->
                    val action = GuideListFragmentDirections.actionGuideListFragmentToGuideDetailFragment(
                        guidesList[position])
                    findNavController().navigate(action)
                }
                recyclerGuide.adapter = adapterGuide
            } else {
                Log.d(ContentValues.TAG, it.exception?.message.toString())
            }
        }
    }



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




