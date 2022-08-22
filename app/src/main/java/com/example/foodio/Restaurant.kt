package com.example.foodio

data class Restaurant(val name : String, val price : Double, val cuisine : String, ){
    private val restaurantName : String = name
    private val restaurantPrice : Double = price
    private val restaurantCuisine : String = cuisine

    fun printDetail(){
        println("Name: $restaurantName Price: $restaurantPrice Cuisine: $restaurantCuisine")
    }
}
