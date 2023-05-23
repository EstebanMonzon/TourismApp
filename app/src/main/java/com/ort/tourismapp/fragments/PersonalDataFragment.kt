package com.ort.tourismapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.User
import com.ort.tourismapp.entities.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonalDataFragment : Fragment() {
    companion object {
        fun newInstance() = PersonalDataFragment()
    }

    private lateinit var viewModel: PersonalDataViewModel
    lateinit var v : View

    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var userRepository: UserRepository

    lateinit var userNombre: EditText
    lateinit var userApellido: EditText
    lateinit var userTelefono: EditText
    lateinit var userPass: EditText
    lateinit var userPassNew: EditText
    lateinit var userPassNewConfirm: EditText
    lateinit var buttonGuardarCambios: Button
    lateinit var buttonCambiarPass: Button
    lateinit var userId: String
    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_personal_data, container, false)
        userId = Firebase.auth.currentUser!!.uid
        userRepository = UserRepository()
        userNombre= v.findViewById(R.id.userPersonName_change)
        userApellido= v.findViewById(R.id.userPersonApellido_change)
        userTelefono= v.findViewById(R.id.userPersonTelefono_change)
        userPass = v.findViewById(R.id.userPassRegister_change)
        userPassNew = v.findViewById(R.id.userPassNewRegister_change)
        userPassNewConfirm = v.findViewById(R.id.userPassNewConfirmRegister_change)
        buttonGuardarCambios = v.findViewById(R.id.btnGuardarCambios)
        buttonCambiarPass = v.findViewById(R.id.btnCambiarPass)
        return v
    }

    override fun onStart() {
        super.onStart()

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            user = userRepository.getUserData(userId)
            userNombre.setText(user.name)
            userApellido.setText(user.lastname)
            userTelefono.setText(user.telefono)
        }

        buttonGuardarCambios.setOnClickListener{
            if(checkChanges(userNombre.text.toString(), userApellido.text.toString(), userTelefono.text.toString())){
                userRepository.updateUser(userNombre.text.toString(), userApellido.text.toString(), userTelefono.text.toString(), user)
                Snackbar.make(v, "Se guardaron los cambios", Snackbar.LENGTH_SHORT).show()
            }
        }

        buttonCambiarPass.setOnClickListener {
            if (checkChangesPass(userPass.text.toString(), userPassNew.text.toString(), userPassNewConfirm.text.toString())){
                userRepository.updatePassword(userPassNew.text.toString(), user)
                Snackbar.make(v, "Se cambió la contraseña", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonalDataViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun checkChanges(nombre: String, apellido: String, telefono: String): Boolean {
        if (nombre.isNullOrEmpty()) {
            Snackbar.make(v, "El nombre no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (apellido.isNullOrEmpty()) {
            Snackbar.make(v, "El apellido no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (telefono.isNullOrEmpty()) {
            Snackbar.make(v, "El telefono no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true //retorna true si se cumplen todos los requisitos
    }

    private fun checkChangesPass(userPass: String, userPassNew: String, userPassNewConfirm: String): Boolean {
        if (userPass.isNullOrEmpty()) {
            Snackbar.make(v, "El password no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(userPassNew.isNullOrEmpty()) {
            Snackbar.make(v, "El password nuevo no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(userPassNewConfirm.isNullOrEmpty()) {
            Snackbar.make(v, "El password nuevo no debe estar vacio", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(!userPassNew.equals(userPassNewConfirm)) {
            Snackbar.make(v, "Error: Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true //retorna true si se cumplen todos los requisitos
    }

}