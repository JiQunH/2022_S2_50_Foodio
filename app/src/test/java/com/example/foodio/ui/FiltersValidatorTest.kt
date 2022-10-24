package com.example.foodio.ui

import com.example.foodio.filter.FiltersValidator
import org.junit.Assert.*
import org.junit.Test

class FiltersValidatorTest{
    @Test
    fun whenInputIsValid(){
         val result = FiltersValidator.inputValidator(10, "Chinese", "$", "Auckland")
        assertTrue(result)
    }

    @Test
    fun whenInputIsInvalid(){
        val result = FiltersValidator.inputValidator(0, "", "", "")
        assertTrue(result)
    }
}