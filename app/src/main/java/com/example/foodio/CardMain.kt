package com.example.foodio

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.foodio.api.YelpService
import com.example.foodio.dao.RestaurantDao
import com.example.foodio.dao.RestaurantDatabase
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.dao.YelpSearchResult
import com.example.foodio.databinding.TinderCardBinding
import com.example.foodio.viewmodel.RestaurantViewModel
import com.example.foodio.viewmodel.RestaurantViewModelFactory
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackView
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
private const val LIMIT = 10
private const val PRICE = "1"
private const val SEARCH_TERM = "Korean"
private const val LOCATION = "AUCKLAND"


private lateinit var dao: RestaurantDao
private lateinit var restaurantList: LiveData<List<YelpRestaurant>>
private val list = mutableListOf<YelpRestaurant>()
private lateinit var factory: RestaurantViewModelFactory
private lateinit var viewModel: RestaurantViewModel
private lateinit var binding: TinderCardBinding
private lateinit var adapter: CardStackAdadpter
private lateinit var layoutManager : CardStackLayoutManager
private lateinit var cardStackView : CardStackView

private lateinit var selectedRestaurant: YelpRestaurant
private var isListItemClicked = false


class CardMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TinderCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantRepository(application)
        initRecyclerView()

        callAPI()

        binding.apply {
            btnDislike.setOnClickListener {
                val index = layoutManager.topPosition
               selectedRestaurant= adapter.returnRestaurant(index)
                Log.i(TAG, "$index")
            }
            btnLike.setOnClickListener{
                cardStackView.swipe()

            }
        }

    }

    private fun callAPI() {

        //gets user preferences for location, price & category
        val locationtest = intent.getStringExtra("Location")
        val pricetest = intent.getStringExtra("Price")
        val cattest = intent.getStringExtra("Category")

        //call api
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY", LIMIT, "$cattest", "$pricetest", "$locationtest")
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
                    binding.apply {
                        viewModel.insertAllRestaurants(body.restaurants)
                    }
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }
            })
    }

    //initialises database, viewmodel and factory
    private fun restaurantRepository(application: Application) {
        dao = RestaurantDatabase.getInstance(application).restaurantDao()
        restaurantList = dao.getAllRestaurants()
        factory = RestaurantViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[RestaurantViewModel::class.java]
        Log.i(TAG, "New instance created...")
    }

    //initialises adapter, layout and view
    private fun initRecyclerView() {
        adapter = CardStackAdadpter(this, list)
        cardStackView = binding.cardStackView
        layoutManager = CardStackLayoutManager(this)
        cardStackView.adapter = adapter

        layoutManager.setCanScrollVertical(false)
        displayRestaurants()
    }

    //displays restaurant profiles
    private fun displayRestaurants() {
        viewModel.restaurants.observe(this) {
            adapter.notifyDataSetChanged()
        }
    }

//    private fun removeRestaurant(returnRestaurant: YelpRestaurant) {
//        binding.apply {
//            viewModel.deleteRestaurant(selectedRestaurant)
//        }
//    }

}
