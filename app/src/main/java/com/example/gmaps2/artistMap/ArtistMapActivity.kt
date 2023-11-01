package com.example.gmaps2.artistMap

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.gmaps2.R
import com.example.gmaps2.common.Locations
import com.example.gmaps2.common.Utils
import com.example.gmaps2.common.dataAccess.FakeDatabase
import com.example.gmaps2.common.entities.Artist
import com.example.gmaps2.databinding.ActivityArtistMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ArtistMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding : ActivityArtistMapBinding
    private lateinit var mapAr : GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapArtist) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(gMap: GoogleMap) {
        mapAr = gMap
        mapAr.moveCamera(CameraUpdateFactory.newLatLngZoom(Locations.hongKongChina, 14f))
        /*mapAr.uiSettings.isZoomControlsEnabled = true
        mapAr.uiSettings.isMapToolbarEnabled = true
        mapAr.uiSettings.isRotateGesturesEnabled = false*/
        mapAr.uiSettings.apply{
            isZoomControlsEnabled = true
            isMapToolbarEnabled = true
            isRotateGesturesEnabled = false
        }
        getArtist()
    }

    private fun getArtist() {
        lifecycleScope.launch{
            setInProgress(true)
            val fakeRemoteData = FakeDatabase.getArtists(this@ArtistMapActivity)
            delay(1_000)
            fakeRemoteData?.let{
                addArtistToMap(it)
            }
            setInProgress(false)
        }
    }

    private fun addArtistToMap(artists: List<Artist>) {
        for (artist in artists){
            val markerOptions = MarkerOptions()
                .position(artist.birthLocation.getLocation())
                .title(artist.getFullName())
                .snippet(artist.birthLocation.placeDetails)
            val glideOptions = RequestOptions().centerCrop()
                .circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(this).asBitmap().load(artist.photoURL)
                .apply(glideOptions).into(object : CustomTarget<Bitmap?>(){
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resource))
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
                            Utils.getResizedBitmap(this@ArtistMapActivity, resource,
                                resources.getDimensionPixelSize(R.dimen.img_map_size))
                        ))
                        mapAr.addMarker(markerOptions)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onLoadFailed(errorDrawable: Drawable?){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin))
                        mapAr.addMarker(markerOptions)
                    }
                })
        }
    }

    private fun setInProgress(enable: Boolean){
        binding.progressBar.visibility = if(enable) View.VISIBLE else View.GONE
    }
}