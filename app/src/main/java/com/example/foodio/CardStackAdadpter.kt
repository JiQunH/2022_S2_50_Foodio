package com.example.foodio

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.databinding.CardRestaurantBinding
import com.yuyakaido.android.cardstackview.CardStackView
private lateinit var cardBinding : CardRestaurantBinding

class CardStackAdadpter(val context: Context, val restaurants : List<YelpRestaurant>) : RecyclerView.Adapter<CardStackAdadpter.CardViewHolder>(){
    inner class CardViewHolder(val binding : CardRestaurantBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(restaurant: YelpRestaurant){
            binding.apply {
               //Gets variable from Dataclass end points ands and sets it to the UI
                tvRName.text = restaurant.name
                tvRAddress.text = restaurant.location.address
                tvRPhone.text = restaurant.phone
                RRating.rating = restaurant.rating!!.toFloat()
                tvRPrice.text = restaurant.price
                tvRPhone.text = restaurant.phone
                //Allows for image to be displayed of type context
                Glide.with(context).load(restaurant.imageUrl).apply(
                    RequestOptions().transform(
                        CenterCrop(), RoundedCorners(20)
                    )
                ).into(ivRImage)
            }
        }
    }

    //Holds layout of indivdual item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        cardBinding = CardRestaurantBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CardViewHolder(cardBinding)
    }

    //Display data at a certain position and update
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    //Gets Size of Restaurant into method
    override fun getItemCount() = restaurants.size

    //Retrn the restaurant at Position number parsed
    fun returnRestaurant(position: Int) : YelpRestaurant{
        return restaurants[position]
    }
}