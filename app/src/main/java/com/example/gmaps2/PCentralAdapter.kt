package com.example.gmaps2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.gmaps2.databinding.InfoParcCentralBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class PCentralAdapter(context : Context) : GoogleMap.InfoWindowAdapter {
    private val binding : InfoParcCentralBinding
    init{
        val viewRoot = LayoutInflater.from(context).inflate(R.layout.info_parc_central, null)
        binding = InfoParcCentralBinding.bind(viewRoot)
    }
    override fun getInfoContents(p0: Marker): View? {
        binding?.let{
            it.imgPCentral.setImageResource(R.drawable.ic_park_48)
            it.rbPCentral.rating = 4.7f
            it.tvName.text = "Parque Central"
            it.tvDescription.text = "Jardines y parque Central de Valencia"
        }
        return binding?.root
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }
}