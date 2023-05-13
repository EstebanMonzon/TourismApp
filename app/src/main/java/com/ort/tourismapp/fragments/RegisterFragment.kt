package com.ort.tourismapp.fragments

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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.User

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private lateinit var viewModel: RegisterViewModel
    lateinit var v : View

    lateinit var userEmailText : EditText
    lateinit var userPassText : EditText
    lateinit var userPassConfirmText : EditText
    lateinit var buttonRegister : Button
    lateinit var userNameText : EditText
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
        userNameText= v.findViewById(R.id.userPersonName)
        //userImgText= v.findViewById(R.id.userProfilePhoto)
        return v
    }

    override fun onStart() {
        super.onStart()
        buttonRegister.setOnClickListener{
            if(userPassText.text.toString().equals(userPassConfirmText.text.toString()))
            {
                var user = User("1", userNameText.text.toString(),userEmailText.text.toString(),userPassText.text.toString(),userImgText.text.toString())
                createAccount(user)
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
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

}
