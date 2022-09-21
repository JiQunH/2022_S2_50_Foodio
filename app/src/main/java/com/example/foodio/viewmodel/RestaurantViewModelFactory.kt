package com.example.foodio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodio.dao.RestaurantDao
import java.lang.IllegalArgumentException

class RestaurantViewModelFactory (private val dao : RestaurantDao): ViewModelProvider.Factory {

    //returns viewmodel of database
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RestaurantViewModel::class.java)){
            return RestaurantViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}