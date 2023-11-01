package com.example.gmaps2.aristForm

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.gmaps2.R
import com.example.gmaps2.common.Utils
import com.example.gmaps2.common.dataAccess.FakeDatabase
import com.example.gmaps2.common.entities.Artist
import com.example.gmaps2.common.entities.BirthLocation
import com.example.gmaps2.databinding.ActivityFormBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class FormActivity : AppCompatActivity(), AuxLocation {
    private lateinit var binding : ActivityFormBinding
    private var artist:Artist? =null
    private lateinit var newLocation : BirthLocation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getArtist()?.let{
            artist = it
            setupArtist(it)
        }
        binding.imgBtnLocation.setOnClickListener {
            showMap()
        }
        binding.mBtnSave.setOnClickListener {
            uploadData()
        }
    }

    private fun setupArtist(artist: Artist) {
        val glideOptions = RequestOptions().centerCrop()
            .circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
        binding.etName.setText(artist.name)
        binding.etSurname.setText(artist.surname)
        Glide.with(this).asBitmap().load(artist.photoURL)
            .apply(glideOptions).into(binding.imgForm)
        binding.etBirthLocation.setText(artist.birthLocation.placeDetails)
        setBirthLocation(
            artist.birthLocation.latitude,
            artist.birthLocation.longitude,
            artist.birthLocation.placeDetails)
    }


    private fun getArtist():Artist? = FakeDatabase.getArtist(this)
    private fun showMap(){
        artist?.let{
            val dialogFragment = LocationFragment(this)
            val transaction = supportFragmentManager.beginTransaction()
            dialogFragment.show(transaction, LocationFragment.TAG)
        }
    }
    private fun uploadData(){
        artist?.let{
            it.name = binding.etName.text.toString()
            it.surname = binding.etSurname.text.toString()
            it.birthLocation = newLocation
        }
    }

    //Implements for AuxLocation Interface
    override fun setBirthLocation(latitude: Double, longitude: Double, placeDetails: String) {
        newLocation = BirthLocation()
        newLocation.latitude = latitude
        newLocation.longitude = longitude
        newLocation.placeDetails = placeDetails
    }
}