package com.example.foodio.filter

object FiltersValidator {

    fun inputValidator(limit : Int, searchTerm : String, price : String, location : String) : Boolean{
        return limit>0 && searchTerm.isNotEmpty() && price.isNotEmpty() && location.isNotEmpty()
    }
}