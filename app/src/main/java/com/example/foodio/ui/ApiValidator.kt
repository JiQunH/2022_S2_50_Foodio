package com.example.foodio.ui

import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.dao.YelpSearchResult
import retrofit2.Response

object ApiValidator {

    fun validateApiCall(restaurantList: MutableList<YelpRestaurant>) : Boolean{
        return restaurantList.isNotEmpty()
    }
}