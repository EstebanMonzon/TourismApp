package com.ort.tourismapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ort.tourismapp.R

class GuideContactInfoFragment : Fragment() {

    companion object {
        fun newInstance() = GuideContactInfoFragment()
    }

    private lateinit var viewModel: GuideContactInfoViewModel
    lateinit var v: View

    lateinit var textName: TextView
    lateinit var textRate: TextView
    lateinit var textUbicacionGuia: TextView
    lateinit var textEmail: TextView
    lateinit var textTelefono: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_guide_contact_info, container, false)
        textName = v.findViewById(R.id.txtGuideName_contacto)
        textRate = v.findViewById(R.id.txtRate_contacto)
        textUbicacionGuia = v.findViewById(R.id.txtUbicacionGuia_contacto)
        textEmail = v.findViewById(R.id.txtEmailGuia)
        textTelefono = v.findViewById(R.id.txtTelefonoGuia)
        return v
    }

    override fun onStart() {
        super.onStart()
        val guide = GuideContactInfoFragmentArgs.fromBundle(requireArguments()).guide

        textName.text = guide.name
        textRate.text = guide.rate.toString()
        textUbicacionGuia.text = guide.city
        textEmail.text = guide.email
        textTelefono.text = guide.telefono
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GuideContactInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}