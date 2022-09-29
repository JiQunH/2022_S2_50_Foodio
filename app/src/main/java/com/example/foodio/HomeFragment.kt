package com.example.foodio

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodio.api.YelpService
import com.example.foodio.dao.RestaurantDao
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.dao.YelpSearchResult
import com.example.foodio.databinding.FragmentHomeBinding
import com.example.foodio.viewmodel.RestaurantViewModel
import com.example.foodio.viewmodel.RestaurantViewModelFactory
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val TAG = "HomeFragment"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY =
    "f_eAKp40QcRfos-k0Df3mci08dFFe2VDVFRT27buIkLcVpa77J7-ReupE_5By_qbtvlJj9Dv2BJFbGGZATfMhNzghjhTRpb8zMFeP6oGtER65ZP0-kU1FZlpU0AFY3Yx"
private const val LIMIT = 50
private lateinit var dao: RestaurantDao
private lateinit var factory: RestaurantViewModelFactory
private val intialRestaurantList = mutableListOf<YelpRestaurant>()
private val finalRestaurantList = mutableListOf<YelpRestaurant>()
private lateinit var viewModel: RestaurantViewModel
private lateinit var adapter: CardStackAdadpter
private lateinit var layoutManager: CardStackLayoutManager
private lateinit var cardStackView: CardStackView
private lateinit var binding: FragmentHomeBinding
private lateinit var restaurant: YelpRestaurant

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        callAPI()
        initRecyclerView()

        binding.apply {
            btnDislike.setOnClickListener {

            }
            btnLike.setOnClickListener {
                cardStackView.swipe()
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun callAPI() {

//        //gets user preferences for location, price & category
//        val intent = Intent(activity, FilterActivity::class.java)
//        val location = intent.getStringExtra("Location")
//        val price = intent.getStringExtra("Price")
//        val cat = intent.getStringExtra("Category")

        //call api
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY", LIMIT, "Chinese", "2", "Auckland")
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

                    intialRestaurantList.addAll(body.restaurants)
                    println(intialRestaurantList+"\n")
                    randomiseRestaurants()
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }
            })
    }

    private fun initRecyclerView(){
        cardStackView = binding.cardStackView
        adapter = CardStackAdadpter(requireContext(), finalRestaurantList)
        layoutManager = CardStackLayoutManager(context)
        cardStackView.adapter = adapter
        layoutManager.setCanScrollVertical(false)
    }



    fun randomiseRestaurants() {
        val maxResults = 10
        val list: MutableList<Int> = ArrayList()
        while (list.size < maxResults){
            val number = generateRandomNumber(LIMIT)
            if(!list.contains(number)){
                list.add(number)
                finalRestaurantList.add(intialRestaurantList[number])
            }
        }
    }

    private fun generateRandomNumber(max_number: Int): Int {
        return (0..max_number).random()
    }
}