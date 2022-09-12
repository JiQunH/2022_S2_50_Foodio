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
import com.example.foodio.databinding.ItemRestaurantBinding

private lateinit var itemBinding: ItemRestaurantBinding

class RestaurantsAdapter(val context: Context, private val restaurants: List<YelpRestaurant>) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemBinding =
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: YelpRestaurant) {
            binding.apply {
                tvName.text = restaurant.name
                tvAddress.text = restaurant.location.address
                tvDistance.text = restaurant.displayDistance()
                ratingBar.rating = restaurant.rating!!.toFloat()
                tvPrice.text = restaurant.price
                Glide.with(context).load(restaurant.imageUrl).apply(
                    RequestOptions().transform(
                        CenterCrop(), RoundedCorners(20)
                    )
                ).into(ivImage)
            }

        }
    }


}
