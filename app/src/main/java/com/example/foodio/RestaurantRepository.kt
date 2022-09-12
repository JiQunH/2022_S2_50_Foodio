package com.example.foodio

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.example.foodio.api.YelpService
import com.example.foodio.dao.RestaurantDao
import com.example.foodio.dao.RestaurantDatabase
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.dao.YelpSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"
private const val BASE_URL="https://api.yelp.com/v3/"
private const val API_KEY = "f_eAKp40QcRfos-k0Df3mci08dFFe2VDVFRT27buIkLcVpa77J7-ReupE_5By_qbtvlJj9Dv2BJFbGGZATfMhNzghjhTRpb8zMFeP6oGtER65ZP0-kU1FZlpU0AFY3Yx"
private lateinit var dao : RestaurantDao
private lateinit var restaurantList: LiveData<List<YelpRestaurant>>

class RestaurantRepository : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       restaurantRepository(application)

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).
            build()

        val yelpService=retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY",50,"Avocado Toast","New York").enqueue(object :
            Callback<YelpSearchResult> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG,"onResponse $response")
                val body = response.body()
                if(body==null)
                {
                    Log.w(TAG, "Did not receive valid response body from Yelp API....exiting")
                    return
                }
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG,"onFailure $t")

            }

        })

    }private fun restaurantRepository(application: Application){
        dao = RestaurantDatabase.getInstance(application).restaurantDao()
        restaurantList = dao.getAllRestaurants()
        Log.i(TAG, "New instance created...")
    }


}

