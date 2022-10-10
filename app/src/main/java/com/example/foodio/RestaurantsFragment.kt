package com.example.foodio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.databinding.FragmentHomeBinding
import com.example.foodio.databinding.FragmentRestaurantsBinding
import com.example.foodio.databinding.SavedRestaurantsBinding

private lateinit var binding: FragmentRestaurantsBinding

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
       binding=FragmentRestaurantsBinding.inflate(inflater,container,false)
        binding.rvRestaurants.layoutManager=LinearLayoutManager(context)
        adapter = RestaurantsFragmentAdapter(requireContext(),model.selectedRestaurant)
        binding.rvRestaurants.adapter=adapter
        return binding.root
    }



}