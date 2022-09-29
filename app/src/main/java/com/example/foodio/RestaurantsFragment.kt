package com.example.foodio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodio.databinding.SavedRestaurantsBinding

private lateinit var binding: SavedRestaurantsBinding

class RestaurantsFragment : Fragment() {


//    override fun onCreate(savedInstanceState: Bundle?) {
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding=SavedRestaurantsBinding.inflate(inflater,container,false)

        return binding.root
    }

}