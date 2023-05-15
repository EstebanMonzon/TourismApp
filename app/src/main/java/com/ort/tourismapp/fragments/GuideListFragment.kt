package com.ort.tourismapp.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.GuideAdapter
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.Guide
import com.ort.tourismapp.entities.GuideRepository

class GuideListFragment : Fragment() {

    companion object {
        fun newInstance() = GuideListFragment()
    }

    private lateinit var viewModel: GuideListViewModel
    lateinit var v: View

    lateinit var recyclerGuide: RecyclerView
    lateinit var adapterGuide: GuideAdapter

    val database = Firebase.database.reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_guide_list, container, false)
        recyclerGuide = v.findViewById(R.id.recGuide)
        return v
    }

    override fun onStart() {
        super.onStart()
        recyclerGuide.layoutManager = LinearLayoutManager(context)

        //trae lista de datos
        database.child("guias").get().addOnCompleteListener {
            if (it.isSuccessful) {
                var guidesList = it.result.value as MutableList<Guide>
                //puebla las cards
                adapterGuide = GuideAdapter(guidesList){ position ->
                    val action = GuideListFragmentDirections.actionGuideListFragmentToGuideDetailFragment(
                        guidesList[position])
                    findNavController().navigate(action)
                }
                recyclerGuide.adapter = adapterGuide
            } else {
                Log.d(TAG, it.exception?.message.toString())
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GuideListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}