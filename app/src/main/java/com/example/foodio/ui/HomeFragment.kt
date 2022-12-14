package com.example.foodio.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.foodio.api.YelpService
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.dao.YelpSearchResult
import com.example.foodio.databinding.FragmentHomeBinding
import com.yuyakaido.android.cardstackview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable


class SharedViewModel : ViewModel(), Serializable{

    var selectedRestaurant = mutableListOf<YelpRestaurant>()

    fun getList(restaurantList: MutableList<YelpRestaurant>) {
        selectedRestaurant = restaurantList
    }

}

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private val BASE_URL = "https://api.yelp.com/v3/"
    private val API_KEY =
        "f_eAKp40QcRfos-k0Df3mci08dFFe2VDVFRT27buIkLcVpa77J7-ReupE_5By_qbtvlJj9Dv2BJFbGGZATfMhNzghjhTRpb8zMFeP6oGtER65ZP0-kU1FZlpU0AFY3Yx"
    private var LIMIT: Int = 10
    private lateinit var LOCATION: String
    private lateinit var PRICE: String
    private lateinit var CATEGORY: String

    @SuppressLint("StaticFieldLeak")
    private lateinit var adapter: CardStackAdadpter

    @SuppressLint("StaticFieldLeak")
    private lateinit var layoutManager: CardStackLayoutManager

    @SuppressLint("StaticFieldLeak")
    private lateinit var binding: FragmentHomeBinding
    private var topPosition: Int = 0
    private val apiRestaurantList = mutableListOf<YelpRestaurant>()
    private val likedRestaurantList = mutableListOf<YelpRestaurant>()


    private val model: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntents()
        callAPI()
    }

    private fun getIntents() {
        val mainActivity: MainActivity = activity as MainActivity
        CATEGORY = mainActivity.getCategoryInfo()
        PRICE = mainActivity.getPriceInfo()
        LOCATION = mainActivity.getLocationInfo()
        checkIntents()
    }

    private fun checkIntents() {
        LIMIT = when (PRICE) {
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
        yelpService.searchRestaurants("Bearer $API_KEY", LIMIT, CATEGORY, PRICE, LOCATION)
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
                    Log.i(TAG, "$apiRestaurantList")
                    apiRestaurantList.addAll(body.restaurants)
                    apiRestaurantList.shuffle()
                    adapter = CardStackAdadpter(requireContext(), apiRestaurantList)
                    binding.cardStackView.adapter = adapter
                    setUpButton()

                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }
            })
    }

    private fun initialiseLayout() {
        binding.apply {
            layoutManager = CardStackLayoutManager(requireContext())
            layoutManager.setStackFrom(StackFrom.None)
            layoutManager.setVisibleCount(3)
            layoutManager.setTranslationInterval(8.0f)
            layoutManager.setScaleInterval(0.95f)
            layoutManager.setSwipeThreshold(0.3f)
            layoutManager.setMaxDegree(0.0f)
            layoutManager.setDirections(Direction.HORIZONTAL)
            layoutManager.setCanScrollHorizontal(false)
            layoutManager.setCanScrollVertical(false)
            layoutManager.setSwipeableMethod(SwipeableMethod.Automatic)
            layoutManager.setOverlayInterpolator(LinearInterpolator())
            cardStackView.layoutManager=layoutManager
        }
    }

    private fun setUpButton() {
        binding.apply {
            initialiseLayout()
            btnDislike.setOnClickListener {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                layoutManager.setSwipeAnimationSetting(setting)
                cardStackView.layoutManager=layoutManager
                cardStackView.swipe()
                Toast.makeText(context, "Disliked", Toast.LENGTH_SHORT).show()
                incrementTopPos()
                checkEndOfList()
            }
            btnLike.setOnClickListener {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                layoutManager.setSwipeAnimationSetting(setting)
                cardStackView.layoutManager=layoutManager
                cardStackView.swipe()
                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show()
                addLikedRestaurant()
                checkEndOfList()
            }
        }
    }


    private fun checkEndOfList() {
        when(topPosition){
            apiRestaurantList.size - 1 -> model.getList(likedRestaurantList)
            apiRestaurantList.size -> disableButtons()
        }
    }

    private fun incrementTopPos() {
        if (topPosition < apiRestaurantList.size) {
            topPosition++
        }
    }

    private fun addLikedRestaurant() {
        val selectedRestaurant = adapter.returnRestaurant(topPosition)
        likedRestaurantList.add(selectedRestaurant)
        incrementTopPos()
    }

    private fun disableButtons(){
        binding.btnLike.isEnabled=false
        binding.btnDislike.isEnabled=false
    }

}