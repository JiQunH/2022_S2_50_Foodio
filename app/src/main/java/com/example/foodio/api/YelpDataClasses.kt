package com.example.foodio.dao

import android.location.Location
import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import kotlin.math.sin


data class YelpSearchResult (
    @SerializedName("total") val total : Int,
    @SerializedName("businesses") val restaurants : List<YelpRestaurant>
)

@Entity(tableName = "restaurant_data_table", primaryKeys = ["name"])
data class YelpRestaurant(
//variables
    @NonNull
    val name : String,
    val rating : Double?,
    val price: String?,
    @SerializedName("review_count") val numReviews : Int?,
    @SerializedName("image_url") val imageUrl : String?,
    @SerializedName("phone") val phone: String,
    val location: YelpLocation,
    val city : String?,
    val coordinates : YelpCoordinates
){


}

data class YelpCoordinates (
    val latitude : Double?,
    val longitude : Double?
) : Serializable

data class YelpLocation(
    @SerializedName("address1") val address : String
)




//data class YelpCategory(
//    val title : String
//)

