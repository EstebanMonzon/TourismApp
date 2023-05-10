package com.ort.tourismapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.ort.tourismapp.R

class ActivityDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ActivityDetailFragment()
    }

    private lateinit var viewModel: ActivityDetailViewModel
    lateinit var v : View

    lateinit var textTitle : TextView
    lateinit var textCity : TextView
    lateinit var textDesc : TextView
    lateinit var btnActivityContact : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_activity_detail, container, false)
        textTitle = v.findViewById(R.id.txtActivityTitle)
        textCity = v.findViewById(R.id.txtCity)
        textDesc = v.findViewById(R.id.txtActivityDesc)
        btnActivityContact = v.findViewById(R.id.btnActivityContact)

        return v
    }

    override fun onStart() {
        super.onStart()
        val activity = ActivityDetailFragmentArgs.fromBundle(requireArguments()).activity
        val title = activity.title
        val city = activity.city
        val description = activity.description
        textTitle.text = title
        textCity.text = city
        textDesc.text = description

        //TODO deberia saltar un pop-up con la frase "Enviamos tu notificación al guia para que te contacte"
        btnActivityContact.setOnClickListener(){
            val action = ActivityDetailFragmentDirections.actionActivityDetailFragmentToGuideListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivityDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}