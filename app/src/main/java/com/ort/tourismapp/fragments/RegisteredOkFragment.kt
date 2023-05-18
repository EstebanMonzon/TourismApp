package com.ort.tourismapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ort.tourismapp.R

class RegisteredOkFragment : Fragment() {

    companion object {
        fun newInstance() = RegisteredOkFragment()
    }

    private lateinit var viewModel: RegisteredOkViewModel
    lateinit var v : View

    lateinit var buttonVolver : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_registered_ok, container, false)

        buttonVolver = v.findViewById(R.id.btnRegisteredVolver)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisteredOkViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        buttonVolver.setOnClickListener{
            val action = RegisteredOkFragmentDirections.actionRegisteredOkFragmentToLoginFragment2()
            findNavController().navigate(action)
        }
    }
}