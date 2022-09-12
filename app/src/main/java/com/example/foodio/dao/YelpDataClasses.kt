package com.example.foodio.dao

import com.google.gson.annotations.SerializedName

data class YelpSearchResult (
    @SerializedName("total") val total : Int,
    @SerializedName("businesses") val restaurants : List<YelpRestaurant>
)

data class YelpRestaurant(
    val name : String,
    val rating : Double,
    val price: String,
    @SerializedName("review_count") val numReviews : Int,
    @SerializedName("distance") val distanceInMeters: Double,
    @SerializedName("image_url") val imageUrl : String,
    val location: YelpLocation,
    val categories : List<YelpCategory>
){
    fun displayDistance():String{
        val distance = String.format("%.2f",distanceInMeters)
        return "$distance km"
    }
}



data class YelpCategory(
    val title : String
)

data class YelpLocation(
    @SerializedName("address1") val address : String
)