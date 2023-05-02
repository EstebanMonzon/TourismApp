package com.ort.tourismapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.GuideAdapter
import com.ort.tourismapp.entities.GuideRepository

class GuideListFragment : Fragment() {

    companion object {
        fun newInstance() = GuideListFragment()
    }

    private lateinit var viewModel: GuideListViewModel
    lateinit var v : View

    lateinit var recyclerGuide : RecyclerView
    var repositoriy: GuideRepository = GuideRepository()
    lateinit var adapter : GuideAdapter

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
        adapter = GuideAdapter(repositoriy.guides){ position ->
            val action = GuideListFragmentDirections.actionGuideListFragmentToGuideDetailFragment(repositoriy.guides[position])
            findNavController().navigate(action)
        }
        recyclerGuide.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GuideListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}