package com.example.gmaps2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FirstMapActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_map)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val mursiaSity = LatLng(37.982968, -1.127733)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mursiaSity))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mursiaSity, 15f))
        googleMap.addMarker(MarkerOptions().position(mursiaSity).title("MursiaSity"))
        googleMap.isTrafficEnabled = true
    }
}