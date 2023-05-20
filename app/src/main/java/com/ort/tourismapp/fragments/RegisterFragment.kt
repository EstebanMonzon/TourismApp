package com.ort.tourismapp.fragments

import android.content.ContentValues.TAG
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R


class RegisterFragment : Fragment() {
    //TODO no anda registrar nuevo usuario
    companion object {
        fun newInstance() = RegisterFragment()
    }

    val database = Firebase.firestore

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

    //TODO chequear que guarde realmente en usuario y la contraseña (junto con los otros datos)
    private fun crearCuenta(mail: String, contraseña: String, nombre: String, apellido: String) {
        firebaseAuth.createUserWithEmailAndPassword(mail, contraseña).addOnCompleteListener() { task ->
            if(task.isSuccessful) {
                val action = RegisterFragmentDirections.actionRegisterFragmentToRegisteredOkFragment2()
                val user = Firebase.auth.currentUser
                user?.let {
                    val usuarioID = it.uid
                    crearCuentaEnBD(mail, contraseña, nombre, apellido, usuarioID)
                }
                findNavController().navigate(action)
            }
        }
    }

    private fun crearCuentaEnBD(mail: String, contraseña: String, nombre: String, apellido: String, uid: String) {
        val usuario = hashMapOf(
            "uid" to uid,
            "name" to nombre,
            "lastname" to apellido,
            "email" to mail,
            "password" to contraseña
        )
        database.collection("usuarios").document(uid!!).set(usuario)

            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${uid}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
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
