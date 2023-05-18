package com.ort.tourismapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.GuideAdapter
import com.ort.tourismapp.entities.Guide
import com.ort.tourismapp.entities.GuideRepository

class GuideListFragment : Fragment() {

    companion object {
        fun newInstance() = GuideListFragment()
    }

    private lateinit var viewModel: GuideListViewModel
    lateinit var v: View
    var guideList : MutableList<Guide> = mutableListOf()

    lateinit var recyclerGuide: RecyclerView
    lateinit var adapterGuide: GuideAdapter

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
        adapterGuide = GuideAdapter(guideList){ position ->
            val action = GuideListFragmentDirections.actionGuideListFragmentToGuideDetailFragment(
                guideList[position])
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