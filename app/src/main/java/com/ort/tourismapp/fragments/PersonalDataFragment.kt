package com.ort.tourismapp.fragments

import android.content.ContentValues.TAG
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.User

class PersonalDataFragment : Fragment() {
    companion object {
        fun newInstance() = PersonalDataFragment()
    }

    private lateinit var viewModel: PersonalDataViewModel
    lateinit var v : View

    val db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_personal_data, container, false)
        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonalDataViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private fun updateUser (){
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = "Jane Q. User"
            photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }
    }
}