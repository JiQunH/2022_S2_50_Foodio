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
       //binds json api to card view
        fun bind(restaurant: YelpRestaurant){
            binding.apply {
                //Sets data from DataClass to components for the UI
                tvRName.text = restaurant.name
                tvRAddress.text = restaurant.location.address
                tvRPhone.text = restaurant.phone
                RRating.rating = restaurant.rating!!.toFloat()
                tvRPrice.text = restaurant.price
                //Display pictures of the restaurant
                Glide.with(context).load(restaurant.imageUrl).apply(
                    RequestOptions().transform(
                        CenterCrop(), RoundedCorners(20)
                    )
                ).into(ivRImage)
            }
        }
    }

    //Contains layout for indivdual items in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        cardBinding = CardRestaurantBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CardViewHolder(cardBinding)
    }


    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    //Gets size of restaurants being passed
    override fun getItemCount() = restaurants.size

    fun returnRestaurant(position: Int) : YelpRestaurant{
        return restaurants[position]
    }


}