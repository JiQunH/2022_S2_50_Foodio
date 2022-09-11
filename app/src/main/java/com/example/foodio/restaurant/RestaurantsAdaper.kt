package com.example.foodio.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.foodio.R


class RestaurantsAdapter(val context : Context, val restaurants: List<YelpRestaurant>) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(restaurant: YelpRestaurant) {
            val name = itemView.findViewById<TextView>(R.id.tvName)
            val address = itemView.findViewById<TextView>(R.id.tvAddress)
            val rating = itemView.findViewById<RatingBar>(R.id.ratingBar)
            val distance = itemView.findViewById<TextView>(R.id.tvDistance)
            val image = itemView.findViewById<ImageView>(R.id.ivImage)

            name.text = restaurant.name
            address.text = restaurant.location.address
            rating.rating=restaurant.rating.toFloat()
            distance.text = restaurant.displayDistance()

            Glide.with(context).load(restaurant.imageUrl).apply(RequestOptions().transform(
                CenterCrop(), RoundedCorners(20)
            )).into(image)
        }
    }
}
