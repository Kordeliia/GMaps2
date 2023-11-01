package com.example.gmaps2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gmaps2.common.Locations
import com.example.gmaps2.R
import com.example.gmaps2.activities.adapter.PCentralAdapter
import com.example.gmaps2.common.Utils
import com.example.gmaps2.databinding.ActivityMarkersBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MarkersActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMarkerDragListener {
    private lateinit var mapM : GoogleMap
    private lateinit var binding : ActivityMarkersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarkersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.binding = binding
        val mapFragmentM = supportFragmentManager.findFragmentById(R.id.mapM) as SupportMapFragment
        mapFragmentM.getMapAsync(this)
        setupToggle()
    }
    private fun setupToggle() {
        binding.toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                mapM.mapType = when (checkedId) {
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
        mapM = googleMap
        mapM.moveCamera(CameraUpdateFactory.newLatLngZoom(Locations.valenciaParqueCentral, 16f))
        val valenciaPCMarker = mapM.addMarker(MarkerOptions()
            .position(Locations.valenciaParqueCentral)
            .title(getString(R.string.marker_valencia_pc)))
        valenciaPCMarker?.run{
            //setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
            setIcon(BitmapDescriptorFactory.defaultMarker(100f))
            Utils.getBitmapFromVector(this@MarkersActivity, R.drawable.ic_pin)?.let{
                setIcon(BitmapDescriptorFactory.fromBitmap(it))
            }
            rotation = -45f
            setAnchor(0.5f, 0.5f)
            isFlat = true
            snippet = "Parc Central"
        }
        mapM.setOnMarkerClickListener(this)
        valenciaPCMarker?.isDraggable = true
        mapM.setOnMarkerClickListener(this)
        //custom infowWindow
        mapM.setInfoWindowAdapter(PCentralAdapter(this))
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        /*marker.showInfoWindow()
        return tru */
        return false
    }

    override fun onMarkerDrag(marker: Marker) {
        marker.alpha = 0.4f


    }

    override fun onMarkerDragEnd(marker: Marker) {
        binding.toggleGroup.visibility = View.VISIBLE
        marker.title = "New Location"
        marker.snippet = "Welcome"
        marker.alpha = 1.0f
    }

    override fun onMarkerDragStart(marker: Marker) {
        binding.toggleGroup.visibility = View.INVISIBLE
    }
}