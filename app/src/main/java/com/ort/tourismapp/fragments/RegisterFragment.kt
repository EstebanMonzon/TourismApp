package com.ort.tourismapp.fragments

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.User


class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    val database = Firebase.database

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private lateinit var viewModel: RegisterViewModel
    lateinit var v : View

    lateinit var userEmailText : EditText
    lateinit var userPassText : EditText
    lateinit var userPassConfirmText : EditText
    lateinit var buttonRegister : Button
    lateinit var userNombreText : EditText
    lateinit var userApellidoText : EditText
    lateinit var userImgText : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_register, container, false)
        firebaseAuth = Firebase.auth
        userEmailText = v.findViewById(R.id.userEmailRegister)
        userPassText = v.findViewById(R.id.userPassRegister)
        userPassConfirmText = v.findViewById(R.id.userPassConfirmRegister)
        buttonRegister = v.findViewById(R.id.btnRegisterEnter)
        userNombreText= v.findViewById(R.id.userPersonName)
        userApellidoText= v.findViewById(R.id.userPersonApellido)
        //userImgText= v.findViewById(R.id.userProfilePhoto)
        return v
    }

    override fun onStart() {
        super.onStart()

        buttonRegister.setOnClickListener{
            if(userPassText.text.toString().equals(userPassConfirmText.text.toString()))
            {
                crearCuenta(userEmailText.text.toString(), userPassText.text.toString(), userNombreText.text.toString(), userApellidoText.text.toString())
            }
            else
            {
                Snackbar.make(v, "Error: Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()
                userPassText.requestFocus()
            }
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    //TODO chequear que guarde realmente en usuario y la contraseña (junto con los otros datos)
    //ver documentacion https://firebase.google.com/docs/auth/android/start?hl=es&authuser=0#kotlin+ktx_3
    //TODO investigar que es TAG y como se usa


    private fun crearCuenta(mail: String, contra: String, nombre: String, apellido: String)
    {
        firebaseAuth.createUserWithEmailAndPassword(mail, contra).addOnCompleteListener()
        {
                task ->
            if(task.isSuccessful)
            {
                val action = RegisterFragmentDirections.actionRegisterFragmentToRegisteredOkFragment2()
                val user = Firebase.auth.currentUser

                user?.let {
                    val usuarioID = it.uid

                    crearCuentaEnBD(mail, contra, nombre, apellido, usuarioID)
                }

                findNavController().navigate(action)
            }
        }
    }

    private fun crearCuentaEnBD(mail: String, contra: String, nombre: String, apellido: String, uid: String)
    {
        val usuario = hashMapOf(
            "uid" to uid,
            "name" to nombre,
            "lastname" to apellido,
            "email" to mail,
            "password" to contra
        )

        Log.d(TAG, "Intentando crear un usuario en la BD")

        database.getReference("usuarios").child(uid).setValue(usuario)
    }
}
