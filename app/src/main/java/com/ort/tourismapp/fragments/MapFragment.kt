package com.ort.tourismapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ort.tourismapp.R
import com.ort.tourismapp.adapters.ActivityAdapter
import com.ort.tourismapp.entities.Activity
import com.ort.tourismapp.entities.ActivityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: MapViewModel
    lateinit var v : View

    lateinit var mMap : GoogleMap
    lateinit var mMapView : MapView
    lateinit var activityRepository: ActivityRepository
    lateinit var activityList: MutableList<Activity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_map, container, false)
        mMapView = v.findViewById(R.id.mapView)
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync(this)
        activityRepository = ActivityRepository()
        activityList = mutableListOf()
        return v
    }

    override fun onStart() {
        super.onStart()
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            activityList = activityRepository.getAllActivities()
            for (activity in activityList){
                val latLng = LatLng(activity.lat, activity.long)
                mMap.addMarker(MarkerOptions()
                    .position(latLng)
                    .title(activity.title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                )
            }
       }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-34.61315,-58.37723))); //settea inicio en buenos aires
        //TODO deberia navegar a activity pero me tira un null
        mMap.setOnInfoWindowClickListener { marker ->
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                var activity = activityRepository.getActivityByPosition(marker.position)
                val action = MapFragmentDirections.actionMapFragmentToActivityDetailFragment(activity)
                findNavController().navigate(action)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        // TODO: Use the ViewModel
    }

}