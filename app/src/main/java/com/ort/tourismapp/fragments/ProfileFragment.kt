package com.ort.tourismapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ort.tourismapp.R

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    lateinit var v : View

    var textLabel : String = "Mi perfil"
    var textPersonalData : String = "Datos personales"

    lateinit var labelProfile : TextView
    lateinit var buttonPersonalData : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profile, container, false)

        labelProfile = v.findViewById(R.id.txtProfile)
        labelProfile.text = textLabel

        buttonPersonalData = v.findViewById(R.id.btnEditarDatos)
        buttonPersonalData.text = textPersonalData
        return v
    }

    override fun onStart() {
        super.onStart()
        buttonPersonalData.setOnClickListener(){
            val action = ProfileFragmentDirections.actionProfileFragmentToPersonalDataFragment()
            findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}