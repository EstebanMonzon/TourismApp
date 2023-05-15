package com.ort.tourismapp.fragments

import android.os.Bundle
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
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.adapters.GuideAdapter
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.User

class HomeFragment : Fragment() {
    //TODO la barra de busqueda no conecta con activities
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
    val userId = user?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        searchView = v.findViewById(R.id.searchView_home)
        txtBienvenidaNombre = v.findViewById(R.id.txt_Bienvenida)
        txtBienvenidaNombre.setText("Bienvenido\n$txtNombre")
        recyclerActivity = v.findViewById(R.id.recActivity)
        recyclerGuide = v.findViewById(R.id.recGuide)
        return v
    }

    override fun onStart() {
        super.onStart()
        getUserData()
        recyclerActivity.layoutManager = LinearLayoutManager(context)
        recyclerGuide.layoutManager = LinearLayoutManager(context)
        //TODO generar funcion que busque por palabra clave en lista de actividades y guia


        //TODO
        /*btnVerEnMapa_home.setOnClickListener(){
            val action = HomeFragmentDirections
            findNavController().navigate(action)
        }*/
        //TODO necesita ser inicializado porque tira error
        /*btnActividadesVerTodo.setOnClickListener(){
            val action = HomeFragmentDirections.actionHomeFragmentToActivitiesListFragment()
            findNavController().navigate(action)
        }

        btnGuidesVerTodo.setOnClickListener(){
            val action = HomeFragmentDirections.actionHomeFragmentToGuideListFragment()
            findNavController().navigate(action)
        }*/

        //TODO cards redirect to activity or guide
        /*adapterActivity = ActivityAdapter(activitiesList){ position ->
            val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(
                activitiesList[position]
            )
            findNavController().navigate(action)
        }
        recyclerActivity.adapter = adapterActivity

        adapterGuide = GuideAdapter(guidesList){ position ->
            val action = GuideListFragmentDirections.actionGuideListFragmentToGuideDetailFragment(
                guidesList[position])
            findNavController().navigate(action)
        }
        recyclerGuide.adapter = adapterGuide*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    //TODO chequear esto si es como esta en el repo del profesor
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




