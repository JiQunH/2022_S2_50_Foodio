package com.example.foodio

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.foodio.databinding.ActivityMainBinding
import com.example.foodio.filter.FilterActivity

class MainActivity : AppCompatActivity(){

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())
        binding.bottomNavigationView2.setOnItemReselectedListener {
            when (it.itemId){
                R.id.home -> {
                    loadFragment(HomeFragment())
                }
                R.id.restaurants ->{
                    loadFragment(RestaurantsFragment())
                }
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



    fun getLocationInfo() : String{
        val location = intent.getStringExtra("Location")
        return location.toString()
    }

    fun getPriceInfo() : String{
        val price = intent.getStringExtra("Price")
        return price.toString()
    }


    fun getCategoryInfo() : String{
        val cat = intent.getStringExtra("Category")
        return cat.toString()
    }

}