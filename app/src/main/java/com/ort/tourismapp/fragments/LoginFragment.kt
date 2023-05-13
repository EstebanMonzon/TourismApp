package com.ort.tourismapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

    var textLabel : String = "Conozcamos la ciudad!"
    var textEmail : String = "Email"
    var textPass : String = "Contraseña"
    var textLogin: String = "Ingresar"
    var textAccount : String = "No tenes cuenta?"
    var textRegister : String = "Crear cuenta"


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

        labelLogin.text = textLabel
        labelEmail.text = textEmail
        labelPass.text = textPass
        buttonLogin.text = textLogin
        labelAccount.text = textAccount
        buttonRegister.text = textRegister

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

    //TODO chequear que realmente loggee, ver documentacion de google!!
    private fun login(mail: String, contra: String) {
        firebaseAuth.signInWithEmailAndPassword(mail, contra).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                val action = LoginFragmentDirections.actionLoginFragmentToMenuActivity()
                findNavController().navigate(action)
            } else {
                Snackbar.make(v, "Error: el mail o la contraseña son incorrectos", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}