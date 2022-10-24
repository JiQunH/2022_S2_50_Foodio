package com.example.foodio.ui


import android.annotation.SuppressLint
import android.util.Log
import com.example.foodio.api.YelpService
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.dao.YelpSearchResult
import com.google.firebase.auth.AuthResult
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private val BASE_URL = "https://api.yelp.com/v3/"
private val API_KEY =
    "f_eAKp40QcRfos-k0Df3mci08dFFe2VDVFRT27buIkLcVpa77J7-ReupE_5By_qbtvlJj9Dv2BJFbGGZATfMhNzghjhTRpb8zMFeP6oGtER65ZP0-kU1FZlpU0AFY3Yx"


class RetrofitClientTest {
    @Test
    fun testRetrofitInstance() {
        //Get an instance of Retrofit
        val instance: Retrofit = RetrofitClient().retrofit
        assert(instance.baseUrl().url().toString() == BASE_URL)
    }

    @Test
    fun testPlacesService() {
        //Get an instance of PlacesService by proiving the Retrofit instance
        val service = PlacesService(RetrofitClient().retrofit)
        //Create a new request for our API calling
        //Execute the API call
        val limit = 5
        val searchTerm = "Chinese"
        val price = "$"
        val location = "Auckland"
        val response = service.searchRestaurants(API_KEY,limit,searchTerm,price,location).enqueue(object :
            Callback<YelpSearchResult> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<YelpSearchResult>,
                response: Response<YelpSearchResult>
            ) {
                val errorBody = response.errorBody()
                assert(errorBody==null)
                assertNotNull(response.body())
                assert(response.code() == 200)
            }
            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
            }
        })

    }
}


