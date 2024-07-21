package com.educacionit.airbit.reservation.view.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.educacionit.airbit.R
import com.educacionit.airbit.entities.Amenity

class AmenitiesAdapter : RecyclerView.Adapter<AmenitiesAdapter.AmenitiesViewHolder>() {
    private var amenities: List<Amenity>? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AmenitiesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.amenity_item, parent, false)
        return AmenitiesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AmenitiesViewHolder, position: Int) {
        val currentAmenity = amenities?.get(position)
        println("Current amenity = $currentAmenity")
        holder.amenityName.text = currentAmenity?.name
        holder.amenityIcon.apply {
            setImageDrawable(getAmenityIcon(this.context, currentAmenity))
        }
    }

    private fun getAmenityIcon(context: Context, currentAmenity: Amenity?): Drawable? {
        // Todo: Add logic to return the specific icon for every case
        return AppCompatResources.getDrawable(context, R.drawable.bed_icon)
    }

    override fun getItemCount() = amenities?.size ?: 0

    fun setAmenities(updatedAmenities: List<Amenity>) {
        amenities = updatedAmenities
        println("Amenities = $updatedAmenities")
        notifyDataSetChanged()
    }

    class AmenitiesViewHolder(itemView: View) : ViewHolder(itemView) {
        val amenityName: TextView
        val amenityIcon: ImageView

        init {
            amenityName = itemView.findViewById(R.id.amenity_name)
            amenityIcon = itemView.findViewById(R.id.amenity_icon)
        }

    }
}