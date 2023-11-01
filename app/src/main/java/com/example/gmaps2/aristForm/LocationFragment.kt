package com.example.gmaps2.aristForm

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gmaps2.R
import com.example.gmaps2.common.Constants.BIRTH_LOCATION
import com.example.gmaps2.common.Constants.STR_CONCAT
import com.example.gmaps2.common.Locations
import com.example.gmaps2.common.entities.BirthLocation
import com.example.gmaps2.databinding.FragmentLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import java.io.IOException
import java.util.Locale

class LocationFragment(private val auxLocation: AuxLocation) : DialogFragment() {
    private lateinit var centerLocation: LatLng
    private lateinit var map : GoogleMap
    private var _binding : FragmentLocationBinding? = null
    private val binding get()= _binding!!
    private var supportMapFragment : SupportMapFragment? = null
    private var birthLocation : BirthLocation? = null
    private val callback = OnMapReadyCallback{googleMap ->
        map = googleMap
        map.uiSettings.apply {
            isZoomControlsEnabled = true
        }
        birthLocation?.let{
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(it.getLocation(), 15f))
            setupCameraIdle()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        arguments?.let{
            birthLocation = Gson().fromJson(it.getString(BIRTH_LOCATION), BirthLocation::class.java)
        }
    }
    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        _binding = FragmentLocationBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportMapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.mapFragmentForm) as SupportMapFragment?
        supportMapFragment?.getMapAsync(callback)
        setupToolbar()
        binding.btnDone.setOnClickListener {
            sendSelectedLocation()
        }
    }

    private fun setupToolbar() {
        binding.mtbToolbar.setNavigationOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportMapFragment?.let{
            requireActivity().supportFragmentManager.beginTransaction().remove(it).commit()
        }
        _binding = null
    }
    private fun sendSelectedLocation(){
        auxLocation.setBirthLocation(centerLocation.latitude,
            centerLocation.longitude,
            binding.tvAdress.text.toString().trim())
        dismiss()
    }
    private fun setupCameraIdle(){
        map.setOnCameraIdleListener {
            centerLocation = map.cameraPosition.target
            getAdressFromLocation(centerLocation.latitude, centerLocation.longitude)
        }
    }

    private fun getAdressFromLocation(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(requireActivity(), Locale.ENGLISH)
        try{
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if(addresses?.isNotEmpty() == true && (addresses[0].countryCode != null)){
                val fetchedAddress = addresses[0]
                val countryCode = fetchedAddress.countryCode
                val adminArea = fetchedAddress.adminArea
                val locality = fetchedAddress.locality
                binding.tvAdress.text= ""
                binding.tvAdress.append(countryCode)
                adminArea?.let{
                    binding.tvAdress.append(STR_CONCAT)
                    binding.tvAdress.append(adminArea)
                }
                locality?.let{
                    binding.tvAdress.append(STR_CONCAT)
                    binding.tvAdress.append(locality)
                }
            } else{
                binding.tvAdress.setText("Ubicación no válida")
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
    }


    companion object{
        const val TAG = "LocationFragment"
    }
}