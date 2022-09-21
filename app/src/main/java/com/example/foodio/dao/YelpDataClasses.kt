package com.example.foodio.dao

import android.location.Location
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlin.math.sin


data class YelpSearchResult (
    @SerializedName("total") val total : Int,
    @SerializedName("businesses") val restaurants : List<YelpRestaurant>
)

@Entity(tableName = "restaurant_data_table")
data class YelpRestaurant(
//variables
    @ColumnInfo(name = "restaurant_name")
    val name : String?,
    @ColumnInfo(name = "restaurant_rating")
    val rating : Double?,
    @ColumnInfo(name = "restaurant_price")
    val price: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "restaurant_reviews_count")
    @SerializedName("review_count") val numReviews : Int?,
    @SerializedName("image_url") val imageUrl : String?,
    @SerializedName("phone") val phone: String,
    @ColumnInfo(name = "restaurant_address")
    val location: YelpLocation,
    @ColumnInfo(name = "restaurant_city")
    val city : String?
){


}

data class YelpLocation(
    @SerializedName("address1") val address : String
)




//data class YelpCategory(
//    val title : String
//)

