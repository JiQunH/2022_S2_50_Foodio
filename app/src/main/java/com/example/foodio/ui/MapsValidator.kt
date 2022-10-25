package com.example.foodio.ui

import com.google.android.gms.maps.model.LatLng

object MapsValidator {
    
    fun validateMapInput(latlng : LatLng, location : String, name : String) : Boolean{
        return location.isNotEmpty() && name.isNotEmpty() && latlng.latitude.isFinite() && latlng.longitude.isFinite()
    }
}