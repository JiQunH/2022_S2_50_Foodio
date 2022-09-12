package com.example.foodio.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RestaurantDao {
    @Insert
    suspend fun insertAllRestaurants(restaurants : List<YelpRestaurant>)

    @Delete
    suspend fun deleteRestaurant(restaurant: YelpRestaurant)

    @Query("SELECT * FROM restaurant_data_table")
    fun getAllRestaurants():LiveData<List<YelpRestaurant>>
}