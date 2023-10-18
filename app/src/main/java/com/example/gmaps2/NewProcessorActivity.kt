package com.example.gmaps2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class NewProcessorActivity : AppCompatActivity(), OnMapReadyCallback, OnMapsSdkInitializedCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_processor)
        MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST, this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Locations.mursiaSity))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Locations.mursiaSity, 15f))
        googleMap.addMarker(MarkerOptions().position(Locations.mursiaSity).title("MursiaSity"))
        googleMap.isTrafficEnabled = true
    }
    override fun onMapsSdkInitialized(p0: MapsInitializer.Renderer) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
}