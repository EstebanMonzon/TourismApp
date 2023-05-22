package com.ort.tourismapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.integrity.internal.y
import com.ort.tourismapp.R

class ActivityDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ActivityDetailFragment()
    }

    private lateinit var viewModel: ActivityDetailViewModel
    lateinit var v: View

    lateinit var textTitle: TextView
    lateinit var textCity: TextView
    lateinit var textDesc: TextView
    lateinit var textRate: TextView
    lateinit var btnActivityContact : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_activity_detail, container, false)
        textTitle = v.findViewById(R.id.txtActivityTitle)
        textCity = v.findViewById(R.id.txtCity)
        textDesc = v.findViewById(R.id.txtActivityDesc)
        textRate = v.findViewById(R.id.textRate)
        btnActivityContact = v.findViewById(R.id.btnActivityContact)
        return v
    }
    override fun onStart() {
        super.onStart()
        val activity = ActivityDetailFragmentArgs.fromBundle(requireArguments()).activity
        val title = activity.title
        val city = activity.city
        val description = activity.description
        val rate = activity.rate
        textTitle.text = title
        textCity.text = city
        textDesc.text = description
        textRate.text = rate.toString()

        //TODO agregar boton de agregar actividad a favoritos y llamar a userRepository.addFavouriteActivity(uid,activityUid)
        // y userRepository.deleteFavouriteActivity(uid,activityUid)
        /*
        * lo mejor seria hacer esto cuando este la otra app del guia andando
        * deberia poder agregar y borrar de acuerdo a click y cambio de estado del boton
        * solo bordes naranjas es que no esta en favoritos, boton completo naranja esta en favoritos
        */

        btnActivityContact.setOnClickListener(){
            Snackbar.make(v, "Enviamos tu notificación al guia para que te contacte", Snackbar.LENGTH_SHORT).show()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivityDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
