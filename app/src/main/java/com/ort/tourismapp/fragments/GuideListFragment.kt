package com.ort.tourismapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.adapters.GuideAdapter
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.Guide
import com.ort.tourismapp.entities.GuideRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuideListFragment : Fragment() {

    companion object {
        fun newInstance() = GuideListFragment()
    }

    private lateinit var viewModel: GuideListViewModel
    lateinit var v: View
    var guideList : MutableList<Guide> = mutableListOf()

    lateinit var recyclerGuide: RecyclerView
    lateinit var searchView: SearchView
    lateinit var adapterGuide: GuideAdapter
    lateinit var guideRepository: GuideRepository
    lateinit var matchedGuides: MutableList<Guide>
    lateinit var errorBusqueda: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_guide_list, container, false)
        recyclerGuide = v.findViewById(R.id.recGuide)
        searchView = v.findViewById(R.id.searchView_guide)
        guideRepository = GuideRepository()
        matchedGuides = mutableListOf()
        errorBusqueda = v.findViewById(R.id.errorBusqueda_guide)
        return v
    }

    override fun onStart() {
        super.onStart()
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            guideList = guideRepository.getAllGuides()
            recyclerGuide.layoutManager = LinearLayoutManager(context)
            adapterGuide = GuideAdapter(guideList){ position ->
                val action = GuideListFragmentDirections.actionGuideListFragmentToGuideDetailFragment(
                    guideList[position])
                findNavController().navigate(action)
            }
            recyclerGuide.adapter = adapterGuide
        }

        performSearch()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GuideListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun performSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                matchedGuides.clear()
                search(newText)
                return true
            }
        })
    }

    private fun search(text: String) {
        for (guide in guideList){
            if (guide.name.contains(text, true) ||
                guide.lastname.contains(text,true)) {
                matchedGuides.add(guide)
            }
        }
        updateRecyclerView()
        if (matchedGuides.isEmpty()) {
            errorBusqueda.text = "No se encontraron resultados para tu búsqueda"
        }
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        adapterGuide = GuideAdapter(matchedGuides){ position ->
            val action = GuideListFragmentDirections.actionGuideListFragmentToGuideDetailFragment(
                matchedGuides[position]
            )
            findNavController().navigate(action)
        }
        recyclerGuide.adapter = adapterGuide
        adapterGuide.guideList = matchedGuides
        adapterGuide.notifyDataSetChanged()
    }
}