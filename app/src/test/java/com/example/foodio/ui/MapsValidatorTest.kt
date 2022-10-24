package com.example.foodio.ui

import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.*
import org.junit.Test

class MapsValidatorTest{

    @Test
    fun whenMapIsValid(){
        val name = "Eden Noodles Cafe"
        val address = "105 Dominion Road, Mt Eden, Auckland 1024"
        var latlng : LatLng = LatLng(45.0,123.0)
        val result = MapsValidator.validateMapInput(latlng,address, name)
        assertTrue(result)
    }

    @Test
    fun whenMapIsInvalid(){
        val name = ""
        val address = ""
        var latlng : LatLng = LatLng(45.0,123.0)
        val result = MapsValidator.validateMapInput(latlng,address, name)
        assertTrue(result)
    }
}