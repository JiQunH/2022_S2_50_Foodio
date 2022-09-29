package com.example.foodio

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.core.view.get
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
private var LIMIT : Int = 10
private lateinit var LOCATION : String
private lateinit var PRICE : String
private lateinit var CATEGORY : String

private lateinit var dao: RestaurantDao
private lateinit var factory: RestaurantViewModelFactory
private val restaurantList = mutableListOf<YelpRestaurant>()
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            btnDislike.setOnClickListener {
                cardStackView.swipe()
            }
            btnLike.setOnClickListener {
                cardStackView.swipe()
            }
        }
        callAPI()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainActivity: MainActivity = activity as MainActivity
        CATEGORY = mainActivity.getCategoryInfo()
        PRICE = mainActivity.getPriceInfo()
        LOCATION = mainActivity.getLocationInfo()
        checkIntents()
    }

    private fun checkIntents(){
        LIMIT = when(PRICE){
            "1" -> 5
            else -> {
                10
            }
        }
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
                    restaurantList.addAll(body.restaurants)
                    restaurantList.shuffle()

                    initRecyclerView()
                    binding.cardStackView.adapter = CardStackAdadpter(requireContext(), restaurantList)

                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }
            })
    }

    private fun initRecyclerView(){
        layoutManager = CardStackLayoutManager(requireContext(),object : CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {
                if(layoutManager!!.topPosition == restaurantList.size){
                    Toast.makeText(requireContext(),"This is the last card", Toast.LENGTH_SHORT).show()
                }
                if(direction == Direction.Right)
                {
                    //ToDO
                }
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
            }


        })
        layoutManager.setVisibleCount(3)
        layoutManager.setTranslationInterval(0.6f)
        layoutManager.setScaleInterval(0.8f)
        layoutManager.setMaxDegree(20.0f)
        layoutManager.setDirections(Direction.HORIZONTAL)
        layoutManager.setCanScrollVertical(false)
    }


}