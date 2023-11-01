package com.example.gmaps2.common.entities

data class Artist(val id : String = "",
                  var name : String = "",
                  var surname : String = "",
                  val photoURL : String = "",
                  var birthLocation : BirthLocation = BirthLocation()
){
    fun getFullName(): String = "$name $surname"
}
