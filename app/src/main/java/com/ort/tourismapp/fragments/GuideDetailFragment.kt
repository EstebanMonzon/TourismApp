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
import com.ort.tourismapp.entities.GuideRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuideDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GuideDetailFragment()
    }

    private lateinit var viewModel: GuideDetailViewModel
    lateinit var v: View

    lateinit var textName: TextView
    lateinit var textRate: TextView
    lateinit var textUbicacionGuia: TextView
    lateinit var recyclerActivityGuide: RecyclerView
    lateinit var adapterActivity: ActivityAdapter
    lateinit var guideRepository: GuideRepository
    var activityGuideList: MutableList<Activity>  = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_guide_detail, container, false)
        textName = v.findViewById(R.id.txtGuideName)
        textRate = v.findViewById(R.id.txtRate)
        textUbicacionGuia = v.findViewById(R.id.txtUbicacionGuia)
        recyclerActivityGuide = v.findViewById(R.id.recActivity_guide)
        guideRepository = GuideRepository()
        return v
    }

    override fun onStart() {
        super.onStart()
        val guide = GuideDetailFragmentArgs.fromBundle(requireArguments()).guide
        textName.text = "${guide.name} ${guide.lastname}"
        textRate.text = guide.rate.toString()
        textUbicacionGuia.text = "${guide.city}"

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            activityGuideList = guideRepository.getAllActivitiesGuide(guide.uid)
            recyclerActivityGuide.layoutManager = LinearLayoutManager(context)
            adapterActivity = ActivityAdapter(activityGuideList){ position ->
                val action = GuideDetailFragmentDirections.actionGuideDetailFragmentToActivityDetailFragment(activityGuideList[position])
                findNavController().navigate(action)
            }
            recyclerActivityGuide.adapter = adapterActivity
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GuideDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}