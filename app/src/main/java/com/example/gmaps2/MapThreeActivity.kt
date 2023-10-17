package com.example.gmaps2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MapThreeActivity : AppCompatActivity(), OnStreetViewPanoramaReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_three)
        val panoramaFragment2 =  supportFragmentManager
            .findFragmentById(R.id.map3) as SupportStreetViewPanoramaFragment
        panoramaFragment2.getStreetViewPanoramaAsync(this)
    }
    override fun onStreetViewPanoramaReady(streetViewPanorama: StreetViewPanorama) {
        val ponferradina = LatLng(42.543234, -6.593890)
        streetViewPanorama.apply {
           setPosition(ponferradina)
       }
    }
}