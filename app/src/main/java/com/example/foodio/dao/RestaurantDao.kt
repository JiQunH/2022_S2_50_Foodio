package com.example.foodio.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurants(restaurants : List<YelpRestaurant>)

    @Delete
    suspend fun deleteRestaurant(restaurant: YelpRestaurant)

    @Delete
    suspend fun deleteAllRestaurants(restaurant: List<YelpRestaurant>)

    @Query("SELECT * FROM restaurant_data_table")
    fun getAllRestaurants():LiveData<List<YelpRestaurant>>
}