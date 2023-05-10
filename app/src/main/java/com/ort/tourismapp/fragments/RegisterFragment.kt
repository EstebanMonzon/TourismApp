package com.ort.tourismapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
        userImgText= v.findViewById(R.id.userProfilePhoto)
        return v
    }

    override fun onStart() {
        super.onStart()
        buttonRegister.setOnClickListener{
            if(userPassText.text.toString().equals(userPassConfirmText.text.toString()))
            {
                var user = User(1, userNameText.text.toString(),userEmailText.text.toString(),userPassText.text.toString(),userImgText.text.toString())
                crearCuenta(user)
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

    private fun crearCuenta(user: User)
    {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener()
        {
                task ->
            if(task.isSuccessful)
            {
                val actualUserId = FirebaseAuth.getInstance().currentUser?.uid
                if (actualUserId != null) {
                    FirebaseDatabase.getInstance().getReference("Users").child(actualUserId).setValue(user)
                }
            }
            val action = RegisterFragmentDirections.actionRegisterFragmentToRegisteredOkFragment2()
            findNavController().navigate(action)
        }
    }
}
