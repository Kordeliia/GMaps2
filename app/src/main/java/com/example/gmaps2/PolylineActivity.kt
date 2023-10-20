package com.example.gmaps2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.gmaps2.databinding.ActivityPolylineBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        mapPolyline.setPadding(0, 0, 0, Utils.dp(64))
        runPolyline()
      //  runPolygon()
     //   runCircle()
    }
    private fun runPolyline() {

        val route = mutableListOf(Locations.murciaTerraNatura, Locations.murciaAlcantarilla,
            Locations.murciaPatinio, Locations.murciaTorreaguera, Locations.murciaSantomera)
        mapPolyline.addMarker(MarkerOptions().position(Locations.murciaTerraNatura).title(
            getString(
                R.string.marker_murcia_terra_natura
            )))
        val polyline = mapPolyline.addPolyline(PolylineOptions()
            .width(8f)
            .color(Color.MAGENTA)
            .geodesic(true)
            .clickable(true))
       // polyline.points = route
        lifecycleScope.launch {
            route.add(Locations.murciaTerraNatura)
            route.add(Locations.murciaAlcantarilla)
            route.add(Locations.murciaPatinio)
            route.add(Locations.murciaTorreaguera)
            route.add(Locations.murciaSantomera)
            val runtimeRoute = mutableListOf<LatLng>()
            for(point in route){
                runtimeRoute.add(point)
                polyline.points = runtimeRoute
                delay(1_500)
            }
        }
        polyline.tag = getString(R.string.tag_polyline)
        mapPolyline.setOnPolylineClickListener {
            Toast.makeText(this,"${it.tag}" , Toast.LENGTH_SHORT).show()
        }
        polyline.pattern = listOf(Dot(), Gap(16f), Dash(32f), Gap(16f))
        polyline.jointType = JointType.ROUND
        polyline.width = 16f
    }
    private fun runCircle() {
        TODO("Not yet implemented")
    }

    private fun runPolygon() {
        TODO("Not yet implemented")
    }


}