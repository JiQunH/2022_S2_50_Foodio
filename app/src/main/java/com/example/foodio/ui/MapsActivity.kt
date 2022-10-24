package com.example.foodio.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodio.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.foodio.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var latLng: LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val boundsBuilder = LatLngBounds.builder()
        val lat = intent.getSerializableExtra(EXTRA_RESTAURANT_LATITUDE) as Double
        val long = intent.getSerializableExtra(EXTRA_RESTAURANT_LONGITUDE) as Double
        val name = intent.getStringExtra("Name")
        val address = intent.getStringExtra("Address")
        val restaurantCoordinates = LatLng(lat,long)
        mMap.addMarker(MarkerOptions().position(restaurantCoordinates).title(name).snippet(address))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantCoordinates,16.0f))
    }
}