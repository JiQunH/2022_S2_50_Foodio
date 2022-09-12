package com.example.foodio.dao

import android.location.Location
import androidx.browser.trusted.sharing.ShareTarget
import androidx.room.*
import com.google.gson.annotations.SerializedName

data class YelpSearchResult (
    @SerializedName("total") val total : Int,
    @SerializedName("businesses") val restaurants : List<YelpRestaurant>
)

@Entity(tableName = "restaurant_data_table")
data class YelpRestaurant(

    @ColumnInfo(name = "restaurant_name")
    val name : String?,
    @ColumnInfo(name = "restaurant_rating")
    val rating : Double?,
    @ColumnInfo(name = "restaurant_price")
    val price: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "restaurant_reviews_count")
    @SerializedName("review_count") val numReviews : Int?,
    @ColumnInfo(name = "restaurant_distance")
    @SerializedName("distance") val distanceInMeters: Double?,
    @SerializedName("image_url") val imageUrl : String?,
    @ColumnInfo(name = "restaurant_address")
    val location: YelpLocation,
//    val categories : List<YelpCategory>
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