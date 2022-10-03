package com.example.foodio

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.example.foodio.api.YelpService
import com.example.foodio.dao.RestaurantDao
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.dao.YelpSearchResult
import com.example.foodio.databinding.FragmentHomeBinding
import com.example.foodio.viewmodel.RestaurantViewModelFactory
import com.yuyakaido.android.cardstackview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "HomeFragment"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY =
    "f_eAKp40QcRfos-k0Df3mci08dFFe2VDVFRT27buIkLcVpa77J7-ReupE_5By_qbtvlJj9Dv2BJFbGGZATfMhNzghjhTRpb8zMFeP6oGtER65ZP0-kU1FZlpU0AFY3Yx"
private const val LIMIT = 10
private lateinit var LOCATION : String
private lateinit var PRICE : String
private lateinit var CATEGORY : String

private lateinit var dao: RestaurantDao
private lateinit var factory: RestaurantViewModelFactory
private val initialRestaurantList = mutableListOf<YelpRestaurant>()
private val finalRestaurantList = mutableListOf<YelpRestaurant>()
private lateinit var adapter: CardStackAdadpter
private lateinit var layoutManager: CardStackLayoutManager
private lateinit var cardStackView: CardStackView
private lateinit var binding: FragmentHomeBinding

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        callAPI()
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRecyclerView()
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Top)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()
        layoutManager.setSwipeAnimationSetting(setting)
        binding.apply {
            btnDislike.setOnClickListener {
                cardStackView.swipe()
            }
            btnLike.setOnClickListener {
                cardStackView.swipe()
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainActivity: MainActivity = activity as MainActivity
        CATEGORY = mainActivity.getCategoryInfo()
        PRICE = mainActivity.getPriceInfo()
        LOCATION = mainActivity.getLocationInfo()
//        callAPI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun callAPI() {

        //call api
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY", LIMIT, "$CATEGORY", "$PRICE", "$LOCATION")
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
                    initialRestaurantList.addAll(body.restaurants)
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }
            })
    }

    private fun initRecyclerView(){
        cardStackView = binding.cardStackView
        adapter = CardStackAdadpter(requireContext(), initialRestaurantList)
        layoutManager = CardStackLayoutManager(context)
        cardStackView.adapter = adapter
        layoutManager.setCanScrollVertical(false)
    }


}