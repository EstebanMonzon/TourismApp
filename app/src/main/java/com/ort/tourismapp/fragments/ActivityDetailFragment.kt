package com.ort.tourismapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.integrity.internal.y
import com.ort.tourismapp.R
import com.ort.tourismapp.entities.GuideRepository
import com.ort.tourismapp.entities.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ActivityDetailFragment()
    }

    private lateinit var viewModel: ActivityDetailViewModel
    lateinit var v: View

    lateinit var guideRepository: GuideRepository

    lateinit var textTitle: TextView
    lateinit var textCity: TextView
    lateinit var textDesc: TextView
    lateinit var imageActivity: ImageView
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
        imageActivity = v.findViewById(R.id.imageView_flag)
        btnActivityContact = v.findViewById(R.id.btnActivityContact)
        guideRepository = GuideRepository()
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

        Glide.with(this)
            .load(activity.activityPhoto)
            .centerCrop()
            .into(imageActivity)

        //TODO agregar boton de agregar actividad a favoritos y llamar a userRepository.addFavouriteActivity(uid,activityUid)
        // y userRepository.deleteFavouriteActivity(uid,activityUid)
        /*
        * lo mejor seria hacer esto cuando este la otra app del guia andando
        * deberia poder agregar y borrar de acuerdo a click y cambio de estado del boton
        * solo bordes naranjas es que no esta en favoritos, boton completo naranja esta en favoritos
        */

        btnActivityContact.setOnClickListener(){
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                val guide = guideRepository.getGuide(activity.guideUid)
                val action = ActivityDetailFragmentDirections.actionActivityDetailFragmentToGuideContactInfoFragment(
                    guide)
                findNavController().navigate(action)
            }
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivityDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
