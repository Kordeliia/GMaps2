package com.example.gmaps2.modes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gmaps2.R
import com.example.gmaps2.common.Locations
import com.example.gmaps2.common.Utils
import com.example.gmaps2.databinding.ActivityControlGesturesBinding
import com.example.gmaps2.databinding.ActivityLiteModeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.MarkerOptions

class LiteModeActivity : AppCompatActivity(), OnMapReadyCallback,
    OnStreetViewPanoramaReadyCallback {
    private lateinit var mapLite : GoogleMap
    private lateinit var mapLite2 : StreetViewPanorama
    private lateinit var binding : ActivityLiteModeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiteModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.binding = binding
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val panoramaFragment = supportFragmentManager
            .findFragmentById(R.id.map2) as SupportStreetViewPanoramaFragment
        panoramaFragment.getStreetViewPanoramaAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapLite = googleMap

        mapLite.moveCamera(CameraUpdateFactory.newLatLng(Locations.murciaTerraNatura))
        mapLite.addMarker(MarkerOptions().position(Locations.murciaTerraNatura).title(getString(R.string.marker_murcia_terra_natura)))

    }

    override fun onStreetViewPanoramaReady(panorama: StreetViewPanorama) {
        mapLite2 = panorama
        mapLite2.setPosition(Locations.ponferradina)
    }
}