package com.example.foodio.ui


import android.annotation.SuppressLint
import com.example.foodio.api.YelpService
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.dao.YelpSearchResult
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val BASE_URL = "https://api.yelp.com/v3/"
private val API_KEY =
    "f_eAKp40QcRfos-k0Df3mci08dFFe2VDVFRT27buIkLcVpa77J7-ReupE_5By_qbtvlJj9Dv2BJFbGGZATfMhNzghjhTRpb8zMFeP6oGtER65ZP0-kU1FZlpU0AFY3Yx"

class ApiValidatorTest {
     @Test
     fun whenInputIsValid(){
         val apiRestaurantList = callApi()
         val result = ApiValidator.validateApiCall(apiRestaurantList)
         assertNotNull(result)
     }

    private fun callApi() : MutableList<YelpRestaurant>{
        val limit = 10
        val searchTerm = "Chinese"
        val price = "$"
        val location = "Auckland"

        val apiRestaurantList = mutableListOf<YelpRestaurant>()
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY", limit, searchTerm, price, location)
            .enqueue(object : Callback<YelpSearchResult> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<YelpSearchResult>,
                    response: Response<YelpSearchResult>
                ) {
                    val body = response.body()
                    if (body != null) {
                        apiRestaurantList.addAll(body.restaurants)
                    }
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        return apiRestaurantList
    }
}