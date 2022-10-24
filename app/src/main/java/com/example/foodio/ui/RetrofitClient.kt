package com.example.foodio.ui

import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.dao.YelpSearchResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

private val API_KEY =
    "f_eAKp40QcRfos-k0Df3mci08dFFe2VDVFRT27buIkLcVpa77J7-ReupE_5By_qbtvlJj9Dv2BJFbGGZATfMhNzghjhTRpb8zMFeP6oGtER65ZP0-kU1FZlpU0AFY3Yx"

class RetrofitClient {
    private val BASE_URL = "https://api.yelp.com/v3/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

interface PlacesEndpoint {

    @GET("businesses/search")
    fun searchRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("limit") limit: Int,
        @Query("term") searchTerm: String,
        @Query("price") price: String,
        @Query("location") location: String
    ): Call<YelpSearchResult>
}

class PlacesService constructor(private val retrofit: Retrofit) :
    PlacesEndpoint {
    private val endpoint by lazy { retrofit.create(PlacesEndpoint::class.java) }

    override fun searchRestaurants(
        authHeader: String,
        limit: Int,
        searchTerm: String,
        price: String,
        location: String
    ): Call<YelpSearchResult> =
        endpoint.searchRestaurants(authHeader, limit,searchTerm,price,location)

}