package com.example.gmaps2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.gmaps2.databinding.ActivityEventsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class EventsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMarkerDragListener {
    private lateinit var binding: ActivityEventsBinding
    private lateinit var map6 : GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment6 = supportFragmentManager.findFragmentById(R.id.map6) as SupportMapFragment
        mapFragment6.getMapAsync(this)
        setupToggle()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map6 = googleMap
        map6.moveCamera(CameraUpdateFactory.newLatLngZoom(Locations.valencia, 16f))
        val valencia = map6.addMarker(MarkerOptions()
            .position(Locations.valencia).title(getString(R.string.mark_valencia)))
        var valenciaCPMarker : Marker? = null
        map6.setOnMapClickListener {
            Toast.makeText(this,
                getString(R.string.toast_click_txt), Toast.LENGTH_SHORT).show()
            valenciaCPMarker = map6.addMarker(MarkerOptions()
                .position(Locations.valenciaParqueCentral)
                .title(getString(R.string.marker_valencia)))
            valencia?.zIndex = 1f
        }
        map6.setOnMapLongClickListener {
            Toast.makeText(this,
                getString(R.string.toast_long_click_txt), Toast.LENGTH_SHORT).show()
            valenciaCPMarker?.remove()
        }
        valencia?.tag = "Open to walk"
        map6.setOnMarkerClickListener(this)
        valencia?.isDraggable = true
        map6.setOnMarkerDragListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.i(getString(R.string.tag_abc), "onMarkerClick: ${marker.tag}")
        return false
    }

    override fun onMarkerDrag(marker: Marker) {
        Log.i(getString(R.string.tag_abc), "onMarkerDrag: ${marker.position}...")
    }

    override fun onMarkerDragEnd(p0: Marker) {
        binding.toggleGroup.visibility = View.VISIBLE
    }

    override fun onMarkerDragStart(p0: Marker) {
        binding.toggleGroup.visibility = View.INVISIBLE
    }
    private fun setupToggle(){
        binding.toggleGroup.addOnButtonCheckedListener{ group, checkedId, isChecked ->
            if(isChecked){
                map6.mapType = when(checkedId){
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