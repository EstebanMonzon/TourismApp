package com.ort.tourismapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private lateinit var viewModel: LoginViewModel
    lateinit var v : View

    lateinit var labelLogin : TextView
    lateinit var labelEmail : TextView
    lateinit var userEmailText : EditText
    lateinit var labelPass : TextView
    lateinit var userPassText : EditText
    lateinit var buttonLogin : Button
    lateinit var labelAccount : TextView
    lateinit var buttonRegister : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)

        firebaseAuth = Firebase.auth

        labelLogin = v.findViewById(R.id.txtLogin)
        labelEmail = v.findViewById(R.id.txtUser)
        userEmailText = v.findViewById(R.id.userEmail)
        labelPass = v.findViewById(R.id.txtPass)
        userPassText = v.findViewById(R.id.userPass)
        buttonLogin = v.findViewById(R.id.btnLogin)
        labelAccount = v.findViewById(R.id.txtAccount)
        buttonRegister = v.findViewById(R.id.btnRegister)
        return v
    }
    override fun onStart() {
        super.onStart()
        buttonLogin.setOnClickListener {
            login(userEmailText.text.toString(), userPassText.text.toString())
        }

        buttonRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }
    //SOLO RECIBE DATOS SI SE LOS TIPEA!, no recibe datos si se elige de datos pre-guardados del teclado
    //TODO chequea error: com.google.firebase.FirebaseException: An internal error has occurred. [ unexpected end of stream on
    private fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                val action = LoginFragmentDirections.actionLoginFragmentToMenuActivity()
                findNavController().navigate(action)
            } else {
                Snackbar.make(v, "Error: el email o la contraseña son incorrectos", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}