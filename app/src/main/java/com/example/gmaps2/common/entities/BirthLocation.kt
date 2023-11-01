package com.example.gmaps2.common.entities

import com.google.android.gms.maps.model.LatLng

data class BirthLocation(var latitude : Double = 0.0,
                         var longitude : Double = 0.0,
                         var placeDetails : String = "") {
    fun getLocation(): LatLng = LatLng(latitude, longitude)
}
