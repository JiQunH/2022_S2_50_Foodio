package com.example.foodio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodio.RestaurantsFragmentAdapter.OnClickListener
import com.example.foodio.databinding.FragmentRestaurantsBinding

private lateinit var binding: FragmentRestaurantsBinding
private const val TAG = "RestaurantsActivity"
const val EXTRA_RESTAURANT_LATITUDE = "EXTRA_RESTAURANT_LATITUDE"
const val EXTRA_RESTAURANT_LONGITUDE = "EXTRA_RESTAURANT_LONGITUDE"

class RestaurantsFragment : Fragment() {

    private val model: SharedViewModel by activityViewModels()
    private lateinit var adapter: RestaurantsFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        binding.rvRestaurants.layoutManager = LinearLayoutManager(context)
        adapter = RestaurantsFragmentAdapter(
            requireContext(),
            model.selectedRestaurant,
            object : OnClickListener {
                override fun onItemClick(position: Int) {
                    Log.i(TAG, "onItemClick $position")
                    val intent = Intent(activity, MapsActivity::class.java)
                    intent.putExtra(EXTRA_RESTAURANT_LATITUDE, model.selectedRestaurant[position].coordinates.latitude)
                    intent.putExtra(EXTRA_RESTAURANT_LONGITUDE, model.selectedRestaurant[position].coordinates.longitude)
                    intent.putExtra("Name", model.selectedRestaurant[position].name)
                    intent.putExtra("Address",model.selectedRestaurant[position].location.address)
                    startActivity(intent)
                }


            })
        binding.rvRestaurants.adapter = adapter
        return binding.root
    }


}