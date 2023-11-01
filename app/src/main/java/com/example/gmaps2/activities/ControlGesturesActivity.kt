package com.example.gmaps2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gmaps2.common.Locations
import com.example.gmaps2.R
import com.example.gmaps2.common.Utils
import com.example.gmaps2.common.Utils.dp
import com.example.gmaps2.databinding.ActivityControlGesturesBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception


class ControlGesturesActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map5 : GoogleMap
    private lateinit var binding : ActivityControlGesturesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlGesturesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.binding = binding
        val mapFragment5 = supportFragmentManager.findFragmentById(R.id.map5) as SupportMapFragment
        mapFragment5.getMapAsync(this)
        setupToggle()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map5 =googleMap
        map5.addMarker(MarkerOptions().position(Locations.ponferradina)
            .title(getString(R.string.title_marker_castillo_ponferrada)))
        map5.moveCamera(CameraUpdateFactory.newLatLngZoom(Locations.ponferradina, 16f))
        map5.uiSettings.apply {
            // isMyLocationButtonEnabled = true // necesita a isMyLocationEnabled
            isZoomControlsEnabled = true
            isCompassEnabled = false
            isMapToolbarEnabled = true
            isRotateGesturesEnabled = false
            isZoomGesturesEnabled = false
            isTiltGesturesEnabled = false
        }
        map5.setPadding(0, 0, 0, dp(64))
        if(isMapStyleReady()){
            Toast.makeText(this, getString(R.string.toast_msg_nav_ready),
                Toast.LENGTH_SHORT).show()
        }
    }
    private fun isMapStyleReady() : Boolean {
        try {
            return map5.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.new_style))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
    private fun setupToggle(){
        binding.toggleGroup.addOnButtonCheckedListener{ group, checkedId, isChecked ->
            if(isChecked){
                map5.mapType = when(checkedId){
                    R.id.btnNormal -> GoogleMap.MAP_TYPE_NORMAL
                    R.id.btnHybrid -> GoogleMap.MAP_TYPE_HYBRID
                    R.id.btnSatellite -> GoogleMap.MAP_TYPE_SATELLITE
                    R.id.btnTerrain -> GoogleMap.MAP_TYPE_TERRAIN
                    else -> GoogleMap.MAP_TYPE_NONE
                }
            }
        }
    }
}


