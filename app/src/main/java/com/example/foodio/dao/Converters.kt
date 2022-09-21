package com.example.foodio.dao


import androidx.room.TypeConverter

class Converters {
    //convert yelp data class YelpLocation to string to store in room and vice versa
    @TypeConverter
    fun locationToString(location: YelpLocation) = location.toString()

    @TypeConverter
    fun stringToLocation(value : String) : YelpLocation = YelpLocation(value)




}