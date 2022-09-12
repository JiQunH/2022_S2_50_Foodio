package com.example.foodio.dao


import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun locationToString(location: YelpLocation) = location.toString()

    @TypeConverter
    fun stringToLocation(value : String) : YelpLocation = YelpLocation(value)

}