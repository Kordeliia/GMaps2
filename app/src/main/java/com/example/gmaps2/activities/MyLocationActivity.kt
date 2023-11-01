package com.example.gmaps2.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.content.ContextCompat
import com.example.gmaps2.common.Locations
import com.example.gmaps2.common.PermissionUtils
import com.example.gmaps2.R
import com.example.gmaps2.common.Utils
import com.example.gmaps2.databinding.ActivityMyLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class MyLocationActivity : AppCompatActivity(), OnMapReadyCallback,
    OnRequestPermissionsResultCallback,
    OnMyLocationButtonClickListener,
    OnMyLocationClickListener {
    private lateinit var mapML : GoogleMap
    private lateinit var binding : ActivityMyLocationBinding
    private var permissionDenied = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.binding = binding
        val mapFragmentML = supportFragmentManager.findFragmentById(R.id.mapML) as SupportMapFragment
        mapFragmentML.getMapAsync(this)
        setupToggle()
    }private fun setupToggle(){
        binding.toggleGroup.addOnButtonCheckedListener{ group, checkedId, isChecked ->
            if(isChecked){
                mapML.mapType = when(checkedId){
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
        mapML = googleMap
        mapML.moveCamera(CameraUpdateFactory.newLatLng(Locations.ponferradina))
        mapML.addMarker(MarkerOptions().position(Locations.ponferradina)
            .title(getString(R.string.title_marker_castillo_ponferrada)))
        mapML.setOnMyLocationButtonClickListener(this)
        mapML.setOnMyLocationButtonClickListener(this)
        enableMyLocation()
    }
    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            mapML.isMyLocationEnabled = true
            return
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
            PermissionUtils.RationaleDialog.newInstance(LOCATION_PERMISSION_REQUEST_CODE, true)
                .show(supportFragmentManager, "dialog")
            return
        }

        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            || PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ){
            enableMyLocation()
        } else {
            permissionDenied = true
        }
    }
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (permissionDenied){
            showMissingPermissionError()
            permissionDenied = false
        }
    }
    private fun showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true)
            .show(supportFragmentManager, "dialog")
    }
    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Mi ubicación:\n$location", Toast.LENGTH_SHORT).show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Mi ubicación", Toast.LENGTH_SHORT).show()
        return false
    }
    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 21
    }
}