package com.example.gmaps2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gmaps2.databinding.ActivityPolylineBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class PolylineActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapPolyline: GoogleMap
    private lateinit var binding: ActivityPolylineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPolylineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.binding = binding
        val mapFragmentPoly =
            supportFragmentManager.findFragmentById(R.id.mapPolyline) as SupportMapFragment
        mapFragmentPoly.getMapAsync(this)
        setupToggle()
    }

    private fun setupToggle() {
        binding.toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                mapPolyline.mapType = when (checkedId) {
                    R.id.btnNormal -> GoogleMap.MAP_TYPE_NORMAL
                    R.id.btnHybrid -> GoogleMap.MAP_TYPE_HYBRID
                    R.id.btnSatellite -> GoogleMap.MAP_TYPE_SATELLITE
                    R.id.btnTerrain -> GoogleMap.MAP_TYPE_TERRAIN
                    else -> GoogleMap.MAP_TYPE_NONE
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapPolyline = googleMap
        mapPolyline.uiSettings.isZoomControlsEnabled = true
        runPolyline()
        runPolygon()
        runCircle()
    }

    private fun runCircle() {
        TODO("Not yet implemented")
    }

    private fun runPolygon() {
        TODO("Not yet implemented")
    }

    private fun runPolyline() {
        TODO("Not yet implemented")
    }
}