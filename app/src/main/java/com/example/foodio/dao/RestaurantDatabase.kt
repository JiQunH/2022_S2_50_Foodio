package com.example.foodio.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [YelpRestaurant::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RestaurantDatabase : RoomDatabase(){

    abstract fun restaurantDao() : RestaurantDao

    companion object{
        @Volatile
        private var INSTANCE : RestaurantDatabase? = null
        fun getInstance(context : Context):RestaurantDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RestaurantDatabase::class.java,
                        "student_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}