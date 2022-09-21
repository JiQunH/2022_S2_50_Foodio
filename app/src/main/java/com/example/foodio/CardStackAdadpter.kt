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
                tvRName.text = restaurant.name
                tvRAddress.text = restaurant.location.address
                tvRCity.text = restaurant.city
                RRating.rating = restaurant.rating!!.toFloat()
                tvRPrice.text = restaurant.price
                Glide.with(context).load(restaurant.imageUrl).apply(
                    RequestOptions().transform(
                        CenterCrop(), RoundedCorners(20)
                    )
                ).into(ivRImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        cardBinding = CardRestaurantBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CardViewHolder(cardBinding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount() = restaurants.size

    fun returnRestaurant(position: Int) : YelpRestaurant{
        return restaurants[position]
    }
}