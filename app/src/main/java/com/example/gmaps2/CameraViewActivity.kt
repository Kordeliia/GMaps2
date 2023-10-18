package com.example.gmaps2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.gmaps2.Utils.dp
import com.example.gmaps2.databinding.ActivityCameraViewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraViewActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding : ActivityCameraViewBinding
    private lateinit var map4 : GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.binding = binding
        val mapFragment4 = supportFragmentManager
            .findFragmentById(R.id.map4) as SupportMapFragment
        mapFragment4.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //Valladolid: 41.646723, -4.730361
        map4 = googleMap
        map4.addMarker(MarkerOptions().position(Locations.valladolid).title("Campo Grande"))
        map4.moveCamera(CameraUpdateFactory.newLatLng(Locations.valladolid))
        /*val tokioCamera = CameraPosition.builder()
            .target(Locations.tokio)
            .bearing(60f)
            .tilt(0f)
            .zoom(20f)
            .build()
        map4.moveCamera(CameraUpdateFactory.newCameraPosition(tokioCamera))*/
      /*  map4.moveCamera(CameraUpdateFactory.newLatLngZoom(Locations.valladolid, 16f))
        lifecycleScope.launch{
            delay(5_000)
            map4.addMarker(MarkerOptions().position(Locations.mursiaSity))
            map4.animateCamera(CameraUpdateFactory.newLatLngZoom(Locations.mursiaSity, 16f))
        }*/
        /*lifecycleScope.launch {
            delay(2_500)
            //map4.moveCamera(CameraUpdateFactory.zoomBy(2f))
            map4.moveCamera(CameraUpdateFactory.zoomIn())
            delay(2_500)
            map4.moveCamera(CameraUpdateFactory.zoomOut())
            delay(3_500)
            map4.animateCamera(CameraUpdateFactory.zoomTo(20f))
        }*/
       /* val tokioCamera = CameraPosition.builder()
            .target(Locations.tokio)
            .bearing(245f)
            .tilt(40f)
            .zoom(18f)
            .build()
        map4.animateCamera(CameraUpdateFactory.newCameraPosition(tokioCamera))
        lifecycleScope.launch {
            delay(2_500)
            for(i in 1..15){
                map4.animateCamera(CameraUpdateFactory.scrollBy(0f, -150f))
                delay(500)
        }
        }*/
        val cartagenaDownBounds = LatLngBounds(
            Locations.cartagena1,
            Locations.cartagena2
        )
        map4.moveCamera(CameraUpdateFactory.newLatLngZoom(Locations.cartagena, 16f))
        lifecycleScope.launch {
            delay(2_500)
            //map4.animateCamera(CameraUpdateFactory.newLatLngBounds(cartagenaDownBounds, dp(32)))
            map4.animateCamera(CameraUpdateFactory
                .newLatLngBounds(cartagenaDownBounds, dp(100), dp(100),dp(32)))
        }
    }
}