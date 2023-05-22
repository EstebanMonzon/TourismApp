package com.ort.tourismapp.fragments

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.User
import com.ort.tourismapp.entities.UserRepository

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var firebaseAuth : FirebaseAuth
    lateinit var userRepository: UserRepository

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
        userRepository = UserRepository()
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
            if(checkAllFields()){
                crearCuenta(userEmailText.text.toString(), userPassText.text.toString(), userNombreText.text.toString(), userApellidoText.text.toString())
            //TODO agregar una imagen pre-seteada a userImgText= v.findViewById(R.id.userProfilePhoto), usar glide
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun crearCuenta(email: String, password: String, nombre: String, apellido: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
            if(task.isSuccessful) {
                val action = RegisterFragmentDirections.actionRegisterFragmentToRegisteredOkFragment2()
                val user = Firebase.auth.currentUser
                user?.let {
                    userRepository.crearCuenta(email, password, nombre, apellido, it.uid)
                }
                findNavController().navigate(action)
            }
        }
    }

    private fun checkAllFields(): Boolean {
        if (userNombreText!!.length() == 0) {
            Snackbar.make(v, "El nombre no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (userApellidoText!!.length() == 0) {
            Snackbar.make(v, "El apellido no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (userEmailText!!.length() == 0) {
            Snackbar.make(v, "El email no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (userPassText!!.length() == 0) {
            Snackbar.make(v, "El password no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(userPassConfirmText!!.length() == 0) {
            Snackbar.make(v, "El password no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(!userPassText.text.toString().equals(userPassConfirmText.text.toString())) {
            Snackbar.make(v, "Error: Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true //retorna true si se cumplen todos los requisitos
    }
}
