package com.ort.tourismapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.User

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    lateinit var v : View

    lateinit var currentUserId : String

    lateinit var txtSearch : EditText
    lateinit var txtNombre : TextView


    val user = Firebase.auth.currentUser
    val userId = user?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        txtSearch = v.findViewById(R.id.txtSearch)
        txtNombre = v.findViewById(R.id.homeUsername)
        return v
    }

    override fun onStart() {
        super.onStart()
        getUserData()
        txtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchTerm = txtSearch.text.toString()
                val action = HomeFragmentDirections.actionHomeFragmentToActivitiesListFragment()
                findNavController().navigate(action)
                true
            } else {
                false
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    //TODO chequear esto si es como esta en el repo del profesor
    fun getUserData(){
        val database = FirebaseDatabase.getInstance().getReference("users")

        if (userId != null) {
            database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = snapshot.getValue(User::class.java)
                    txtNombre.text= user?.name.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
    //TODO mostrar dos actividades
    //TODO mostrar dos guias
    // para traer solo dos cards por actividad o guia
    //se puede usar esto citiesRefs.orderBy("rate").limit(2)

    //TODO conectar todo con Firestore (base de datos de firebase)
    //TODO HACER METODO DE SALIR DE USUARIO (Ver documentacion de google)
    //TODO usar Storage y Guide para guardar las fotos subidas de cada actividad que cree el guia en su app (PARA APP GUIA)
    //usar glide para subir/bajar imagenes
}




