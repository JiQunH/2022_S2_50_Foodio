package com.example.foodio.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.foodio.dao.YelpRestaurant
import com.example.foodio.databinding.SavedRestaurantsBinding

private lateinit var binding: SavedRestaurantsBinding

class RestaurantsFragmentAdapter(val context: Context, val restaurants : List<YelpRestaurant>, val onClickListener: OnClickListener): RecyclerView.Adapter<RestaurantsFragmentAdapter.SavedRestaurantsViewHolder>() {


    interface OnClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRestaurantsViewHolder {
        binding =SavedRestaurantsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SavedRestaurantsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: SavedRestaurantsViewHolder, position: Int) {
        holder.bind(restaurants[position])
        holder.itemView.setOnClickListener{
            onClickListener.onItemClick(position)
        }
    }

    inner class SavedRestaurantsViewHolder(val binding : SavedRestaurantsBinding) : RecyclerView.ViewHolder(binding.root){
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
                ).into(ivImage)
            }
        }
    }


}