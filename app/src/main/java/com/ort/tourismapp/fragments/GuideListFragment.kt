package com.ort.tourismapp.fragments

import android.content.ContentValues
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

    val db = Firebase.firestore
    var guidesList: MutableList<Guide> = arrayListOf()

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

        var guide = Guide("1", "Tamara", "Aida", "t@Aida", "CABA", "Buenos Aires",
            "Argentina", "URL FOTO", "7,5", mutableListOf())
        var guide2 = Guide("2", "Mariana", "Baena", "m@Baena", "CABA", "Buenos Aires",
            "Argentina", "URL Foto", "8", mutableListOf())

        db.collection("guides").add(guide)
        db.collection("guides").add(guide2)

        //trae lista de datos
        db.collection("guides")
//             .whereEqualTo("tipo", "PERRO")
            .limit(20)
            .orderBy("rate")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (guide in snapshot) {
                        guidesList.add(guide.toObject())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        adapterGuide = GuideAdapter(guidesList){ position ->
            val action = GuideListFragmentDirections.actionGuideListFragmentToGuideDetailFragment(
                guidesList[position])
            findNavController().navigate(action)
        }
        recyclerGuide.adapter = adapterGuide
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GuideListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}