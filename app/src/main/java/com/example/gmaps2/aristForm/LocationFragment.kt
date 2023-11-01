package com.example.gmaps2.aristForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gmaps2.R
import com.example.gmaps2.common.Locations
import com.example.gmaps2.databinding.FragmentLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class LocationFragment(private val auxLocation: AuxLocation) : DialogFragment() {
    private var _binding : FragmentLocationBinding? = null
    private val binding get()= _binding!!
    private var supportMapFragment : SupportMapFragment? = null
    private val callback = OnMapReadyCallback{googleMap ->
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Locations.hongKongChina, 15f))
    }
    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        _binding = FragmentLocationBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportMapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.mapFragmentForm) as SupportMapFragment?
        supportMapFragment?.getMapAsync(callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object{
        const val TAG = "LocationFragment"
    }
}