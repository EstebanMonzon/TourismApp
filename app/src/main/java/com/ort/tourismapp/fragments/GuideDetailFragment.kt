package com.ort.tourismapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ort.tourismapp.R

class GuideDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GuideDetailFragment()
    }

    private lateinit var viewModel: GuideDetailViewModel
    lateinit var v : View

    lateinit var textName : TextView
    lateinit var textBio : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_guide_detail, container, false)
        textName = v.findViewById(R.id.txtGuideName)
        textBio = v.findViewById(R.id.txtGuideBio)

        return v
    }

    override fun onStart() {
        super.onStart()
        val guide = GuideDetailFragmentArgs.fromBundle(requireArguments()).guide
        val name = guide.name
        val bio = guide.biography
        textName.text = name
        textBio.text = bio
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GuideDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}