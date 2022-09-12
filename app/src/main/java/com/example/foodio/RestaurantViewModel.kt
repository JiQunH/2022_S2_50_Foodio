package com.example.foodio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodio.dao.RestaurantDao
import com.example.foodio.dao.YelpRestaurant
import kotlinx.coroutines.launch

class RestaurantViewModel(private val dao : RestaurantDao) : ViewModel() {
    val restaurants = dao.getAllRestaurants()

    fun insertAllRestaurants(restaurant: List<YelpRestaurant>) = viewModelScope.launch {
        dao.insertAllRestaurants(restaurant)
    }

    fun deleteRestaurant(restaurant: YelpRestaurant) = viewModelScope.launch {
        dao.deleteRestaurant(restaurant)
    }

    fun deleteAllRestaurants(restaurant: List<YelpRestaurant>) = viewModelScope.launch {
        dao.deleteAllRestaurants(restaurant)
    }
}