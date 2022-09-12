package com.example.foodio

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.foodio.api.YelpService
import com.example.foodio.dao.RestaurantDao
import com.example.foodio.dao.RestaurantDatabase
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.dao.YelpSearchResult
import com.example.foodio.databinding.ActivityRestaurantBinding
import com.example.foodio.viewmodel.RestaurantViewModel
import com.example.foodio.viewmodel.RestaurantViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"

//yelp
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY =
    "f_eAKp40QcRfos-k0Df3mci08dFFe2VDVFRT27buIkLcVpa77J7-ReupE_5By_qbtvlJj9Dv2BJFbGGZATfMhNzghjhTRpb8zMFeP6oGtER65ZP0-kU1FZlpU0AFY3Yx"
private const val LIMIT = 50
private const val SEARCH_TERM = "Chinese"
private const val LOCATION = "AUCKLAND"


private lateinit var restaurantBinding: ActivityRestaurantBinding
private lateinit var dao: RestaurantDao
private lateinit var restaurantList: LiveData<List<YelpRestaurant>>
private val list = mutableListOf<YelpRestaurant>()
private lateinit var factory: RestaurantViewModelFactory
private lateinit var viewModel: RestaurantViewModel
private lateinit var adapter: RestaurantsAdapter


private lateinit var selectedRestaurant: YelpRestaurant
private var isListItemClicked = false


class RestaurantMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restaurantBinding = ActivityRestaurantBinding.inflate(layoutInflater)

        setContentView(restaurantBinding.root)

        restaurantRepository(application)
        initRecyclerView()

        restaurantBinding.apply {
            btn1.setOnClickListener{
                removeRestaurant()
            }

        }

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY", LIMIT, "$SEARCH_TERM", "$LOCATION")
            .enqueue(object : Callback<YelpSearchResult> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<YelpSearchResult>,
                    response: Response<YelpSearchResult>
                ) {
                    Log.i(TAG, "onResponse $response")
                    val body = response.body()
                    if (body == null) {
                        Log.w(TAG, "Did not receive valid response body from Yelp API....exiting")
                        return
                    }
                    list.addAll(body.restaurants)
                    restaurantBinding.apply {
                        viewModel.insertAllRestaurants(list)
                    }
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }
            })


    }

    private fun restaurantRepository(application: Application) {
        dao = RestaurantDatabase.getInstance(application).restaurantDao()
        restaurantList = dao.getAllRestaurants()
        factory = RestaurantViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[RestaurantViewModel::class.java]
        Log.i(TAG, "New instance created...")
    }

    private fun initRecyclerView() {
        adapter = RestaurantsAdapter(this, list)
        restaurantBinding.viewPager.adapter = adapter
        displayRestaurants()
    }

    private fun displayRestaurants() {
        viewModel.restaurants.observe(this) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun removeRestaurant() {
        restaurantBinding.apply {
            viewModel.deleteRestaurant(selectedRestaurant)
        }
    }

    private fun listItemClicked(restaurant: YelpRestaurant) {
        restaurantBinding.apply {
            selectedRestaurant = restaurant
            isListItemClicked = true
        }
    }

}