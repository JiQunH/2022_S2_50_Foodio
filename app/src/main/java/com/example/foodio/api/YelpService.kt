package com.example.foodio.api

import com.example.foodio.dao.YelpSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpService {
    //search parameters to yelp api
    @GET("businesses/search")
    fun searchRestaurants(
        @Header("Authorization") authHeader : String,
        @Query("limit") limit : Int,
        @Query("term") searchTerm : String,
        @Query("price")  price : String,
        @Query("location") location: String) : Call<YelpSearchResult>
}